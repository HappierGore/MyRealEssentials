package commands.UserControl;

import commands.CommandType;
import commands.Commands;
import static helper.TextUtils.parseColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class Heal extends Commands {
    
    public Heal() {
        super("heal", new CommandType(1, null).allowTargeting(1));
    }
    
    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        Player target = this.generateTarget(sender, args, 0);
        
        if (target == null) {
            return;
        }
        
        sender.sendMessage(parseColor("&a" + target.getName() + " has been healed and fed."));
        if (!(sender instanceof Player) || (Player) sender != target) {
            target.sendMessage(parseColor("&aYou have been healed and fed."));
        }
        
        target.setHealth(target.getMaxHealth());
        target.setFoodLevel(20);
    }
    
}
