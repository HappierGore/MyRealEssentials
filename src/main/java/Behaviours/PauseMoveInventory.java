package Behaviours;

import com.happiergore.myrealessentials.main;
import db.YamlJBDC;
import static helper.TextUtils.parseColor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author HappierGore
 */
public class PauseMoveInventory {

    private static final YamlJBDC config = new YamlJBDC(main.getPlugin(main.class).getDataFolder(), "SeeInvCache.yml");

    private static final Map<Player, Player> inventoriesPaused = new HashMap<>();

    private static final Set<Player> playersQuitOnOperation = new HashSet<>();

    private static final Set<Player> returned = new HashSet<>();

    public static void RegisterUser(Player target, Player admin) {
        inventoriesPaused.put(target, admin);
    }

    public static boolean IsRegistered(Player target) {
        return inventoriesPaused.containsKey(target);
    }

    public static void UnregisterUser(Player target) {
        inventoriesPaused.remove(target);
    }

    public static Set<Player> GetAdminsWorking() {
        Set<Player> admins = new HashSet<>();
        inventoriesPaused.forEach((target, admin) -> {
            admins.add(admin);
        });
        return admins;
    }

    public static Player GetTargetByAdmin(Player adminToLook) {
        for (Player target : inventoriesPaused.keySet()) {
            if (inventoriesPaused.get(target) == adminToLook) {
                return target;
            }
        }
        return null;
    }

    public static Player GetAdminByTarget(Player target) {
        if (inventoriesPaused.containsKey(target)) {
            return inventoriesPaused.get(target);
        }
        return null;
    }

    private static final String FINISHED_MSG = parseColor("&aThe admin has finished inspecting your inventory. You are able to move and reduce your inventory items again.");

    private static final String CANCELED_MSG = parseColor("&6An admin still inspecting your inventory. You won't be able to move your inventory items until it finishes.");

    public static void OnClickInventory(InventoryClickEvent e) {
        if (IsRegistered((Player) e.getWhoClicked()) && e.getClickedInventory() != null) {
            ((Player) e.getWhoClicked()).closeInventory();
            e.getWhoClicked().sendMessage(CANCELED_MSG);
            e.setCancelled(true);
        }
    }

    public static void OnDropItem(PlayerDropItemEvent e) {
        if (IsRegistered(e.getPlayer()) && e.getItemDrop() != null) {
            e.getPlayer().sendMessage(CANCELED_MSG);
            e.setCancelled(true);
        }
    }

    public static void OnPlaceBlock(BlockPlaceEvent e) {
        if (IsRegistered(e.getPlayer())) {
            e.getPlayer().sendMessage(CANCELED_MSG);
            e.setCancelled(true);
        }
    }

    public static void OnPlayerJoins(PlayerJoinEvent e) {

        for (Player key : inventoriesPaused.keySet()) {
            if (e.getPlayer().getUniqueId().toString().equals(key.getUniqueId().toString())) {
                playersQuitOnOperation.remove(e.getPlayer());
                returned.add(e.getPlayer());
                e.getPlayer().sendMessage(parseColor("&6An admin still inspecting your inventory."));
                RegisterUser(e.getPlayer(), inventoriesPaused.get(key));
                UnregisterUser(key);
                GetAdminByTarget(e.getPlayer()).sendMessage(parseColor("&aThe player " + e.getPlayer().getDisplayName() + " has returned."));
                GetAdminByTarget(e.getPlayer()).openInventory(e.getPlayer().getInventory());
                return;
            }
        }
        String UUID = e.getPlayer().getUniqueId().toString();
        String itemsPath = UUID + ".items.";
        if (config.getConfig().contains(UUID)) {
            e.getPlayer().getInventory().clear();

            config.GetItemList(itemsPath).forEach((slot, item) -> {
                e.getPlayer().getInventory().setItem(Integer.parseInt(slot), item);
            });

            e.getPlayer().sendMessage(parseColor("&6Your inventory has been modified by an admin while you was offline."));
            config.RemoveKey(UUID);
        }
    }

    public static void OnCloseInventory(InventoryCloseEvent e) {
        Player admin = (Player) e.getPlayer();
        if (GetAdminsWorking().contains(admin)) {
            Player target = GetTargetByAdmin((Player) e.getPlayer());
            if (playersQuitOnOperation.contains(target)) {

                Inventory invOpened = admin.getOpenInventory().getTopInventory();

                config.getConfig().set(target.getUniqueId().toString() + ".displayname", target.getDisplayName());

                for (int i = 0; i < invOpened.getContents().length; i++) {
                    //if items[i] is null, no entry will be made / an existing entry will be removed
                    config.getConfig().set(target.getUniqueId().toString() + ".items." + i, invOpened.getItem(i));
                }

                if (config.SaveFile()) {
                    admin.sendMessage(parseColor("&aYour changes will be aplied when " + target.getDisplayName() + " joins again."));
                    playersQuitOnOperation.remove(target);
                }

            } else if (!returned.contains(target)) {
                admin.sendMessage(parseColor("&aChanges aplied"));
                target.sendMessage(FINISHED_MSG);
            } else {
                returned.remove(target);
                return;
            }
            UnregisterUser(target);
        }
    }

    public static void OnPlayerLeaves(PlayerQuitEvent e) {
        if (IsRegistered(e.getPlayer())) {
            Player admin = GetAdminByTarget(e.getPlayer());
            admin.sendMessage(parseColor("&cThe player " + GetTargetByAdmin(admin).getDisplayName() + " has disconected. \nAnyways, the changes you make will reflected as soon as it joins again."));
            playersQuitOnOperation.add(e.getPlayer());
        }
    }
}
