package commands.ItemControl;

import commands.Arguments.ArgEnum;
import commands.CommandType;
import commands.Commands;
import commands.Arguments.ArgumentType;
import static helper.TextUtils.parseColor;
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
public class RemoveLore extends Commands {
    
    public RemoveLore() {
        super("removelore", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.integer).setPosition(1).setHint("&cYou need to specify the line to remove the lore."));
                add(new ArgumentType(ArgEnum.string).setName("all").optional().setPosition(1));
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
        
        if (!itemMeta.hasLore()) {
            player.sendMessage(parseColor("&6This item doesn't have lore lines."));
            return;
        }
        
        List<String> lore = itemMeta.getLore();
        
        int line;
        
        if (args[0].equalsIgnoreCase("all")) {
            lore.clear();
            player.sendMessage(parseColor("&aAll lore has been removed!"));
        } else {
            try {
                line = Integer.parseInt(args[0]);
                if (line == 0) {
                    line = Integer.parseInt("a");
                }
            } catch (NumberFormatException e) {
                player.sendMessage(parseColor("&cYou need to specify a valid line."));
                return;
            }
            if (lore.size() < line) {
                player.sendMessage(parseColor("&cThe line " + line + " is greather than the amount of lore lines of this item. Please, try again. \n &oMax number available: &n" + lore.size()));
                return;
            }
            
            lore.remove(line - 1);
            player.sendMessage(parseColor("&aLore removed in line " + line + "!"));
        }
        
        itemMeta.setLore(lore);
        
        item.setItemMeta(itemMeta);
        player.getInventory().setItemInHand(item);
        
    }
    
}
