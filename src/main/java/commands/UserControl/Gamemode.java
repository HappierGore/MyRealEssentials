package commands.UserControl;

import commands.Arguments.ArgEnum;
import commands.CommandType;
import commands.Commands;
import commands.Arguments.ArgumentType;
import static helper.TextUtils.parseColor;
import java.util.HashSet;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public final class Gamemode extends Commands {

    public Gamemode() {
        super("gamemode", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.integer).setName("0").setPosition(1).registerInTab());
                add(new ArgumentType(ArgEnum.integer).setName("1").setPosition(1).registerInTab());
                add(new ArgumentType(ArgEnum.integer).setName("2").setPosition(1).registerInTab());
                add(new ArgumentType(ArgEnum.integer).setName("3").setPosition(1).registerInTab());
                add(new ArgumentType(ArgEnum.target).setPosition(2).optional());
            }
        }));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
//MUST CHANGE
        Player target = (Player) sender;

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
