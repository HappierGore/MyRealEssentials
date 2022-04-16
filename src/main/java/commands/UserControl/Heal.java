package commands.UserControl;

import commands.Arguments.ArgEnum;
import commands.CommandType;
import commands.Commands;
import commands.Arguments.ArgumentType;
import static helper.TextUtils.parseColor;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class Heal extends Commands {

    public Heal() {
        super("heal", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.target).setPosition(1).optional());
            }
        }));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        Player target = null;

        if (args.length > 1) {
            if (sender.hasPermission(this.getArgsPerms().get("other"))) {
                System.out.println("Otro");
                target = Bukkit.getPlayer(args[1]);
                System.out.println(target.toString());
            } else {
                sender.sendMessage(parseColor("&cYou aren't allowed to use this command to other players."));
                return;
            }
        }

        if (target == null) {
            if (sender instanceof Player player) {
                target = player;

            } else {
                sender.sendMessage(parseColor("&cThis command only can be used into other players while it's executing in console."));
                return;
            }
        }

        sender.sendMessage(parseColor("&a" + target.getName() + " has been healed and fed."));
        if (!(sender instanceof Player) || (Player) sender != target) {
            target.sendMessage(parseColor("&aYou have been healed and fed."));
        }

        target.setHealth(target.getMaxHealth());
        target.setFoodLevel(20);
    }

}
