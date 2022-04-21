package com.happiergore.myrealessentials.commands.ItemControl;

import com.happiergore.myrealessentials.commands.Arguments.ArgEnum;
import com.happiergore.myrealessentials.commands.CommandType;
import com.happiergore.myrealessentials.commands.Commands;
import com.happiergore.myrealessentials.commands.Arguments.ArgumentType;
import static com.happiergore.myrealessentials.helper.TextUtils.parseColor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author HappierGore
 */
public class SetLore extends Commands {

    public SetLore() {
        super("setlore", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.integer).setPosition(1).setHint("&cYou need to specify the line where you will add the lore."));
                add(new ArgumentType(ArgEnum.string).setPosition(2).optional());
            }
        }));
        this.onlyPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(parseColor("&cYou need an item in your hand to use this command."));
            return;
        }
        ItemMeta itemMeta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();

        int line;

        try {
            line = Integer.parseInt(args[0]);
            if (line == 0) {
                line = Integer.parseInt("a");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            player.sendMessage(parseColor("&cYou need to specify a valid line."));
            return;
        }

        if (lore.size() < line) {
            for (int i = lore.size(); i < line; i++) {
                lore.add("");
            }
        }

        String newText = parseColor(String.join(" ", args)).replaceFirst(String.valueOf(line), "");

        lore.set(line - 1, newText);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);
        player.getInventory().setItemInHand(item);
        player.sendMessage(parseColor("&aLore changed in line " + line + "!"));
    }

}
