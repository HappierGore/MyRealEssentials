package commands.ItemControl;

import commands.Arguments.ArgEnum;
import commands.CommandType;
import commands.Commands;
import commands.Arguments.ArgumentType;
import static helper.TextUtils.parseColor;
import java.util.HashSet;
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
public class Rename extends Commands {
    
    public Rename() {
        super("rename", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.string).setPosition(1).optional());
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
        itemMeta.setDisplayName(parseColor(String.join(" ", args)));
        item.setItemMeta(itemMeta);
        player.getInventory().setItemInHand(item);
        player.sendMessage(parseColor("&aItem renamed!"));
    }
    
}
