package commands.UserControl;

import commands.CommandType;
import commands.Commands;
import static helper.TextUtils.parseColor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public final class Gamemode extends Commands {

    public Gamemode() {
        super("gamemode", new CommandType(2, new HashMap<Integer, Set<String>>() {
            {
                put(1, new HashSet<String>() {
                    {
                        add("0");
                        add("1");
                        add("2");
                        add("3");
                    }
                });
            }
        }).allowTargeting(2));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {

        Player target = this.generateTarget(sender, args, 1);

        if (target == null) {
            return;
        }

        if (args[0].equals("0")) {
            target.setGameMode(GameMode.SURVIVAL);
            sender.sendMessage(parseColor("&6" + target.getName() + "'s &6gamemode changed to survival"));
            if (!(sender instanceof Player) || (Player) sender != target) {
                target.sendMessage(parseColor("&aYour gamemode has been changed to survival"));
            }
            return;
        }
        if (args[0].equals("1")) {
            target.setGameMode(GameMode.CREATIVE);
            sender.sendMessage(parseColor("&6" + target.getName() + "'s &6gamemode changed to creative"));
            if (!(sender instanceof Player) || (Player) sender != target) {
                target.sendMessage(parseColor("&aYour gamemode has been changed to creative"));
            }
            return;
        }
        if (args[0].equals("2")) {
            target.setGameMode(GameMode.ADVENTURE);
            sender.sendMessage(parseColor("&6" + target.getName() + "'s &6gamemode changed to adventure"));
            if (!(sender instanceof Player) || (Player) sender != target) {
                target.sendMessage(parseColor("&aYour gamemode has been changed to adventure"));
            }
        }
        if (args[0].equals("3")) {
            target.setGameMode(GameMode.SPECTATOR);
            sender.sendMessage(parseColor("&6" + target.getName() + "'s &6gamemode changed to spectator"));
            if (!(sender instanceof Player) || (Player) sender != target) {
                target.sendMessage(parseColor("&aYour gamemode has been changed to spectator"));
            }
        }
    }
}
