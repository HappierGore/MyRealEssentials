package commands;

import commands.ItemControl.*;
import commands.UserControl.*;
import static helper.TextUtils.parseColor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class CommandManager implements CommandExecutor {

    public static void init() {
        System.out.println("Creating...");
        registerCommand(new Gamemode());
        registerCommand(new God());
        registerCommand(new Heal());
        registerCommand(new Rename());
        registerCommand(new SetLore());
        registerCommand(new RemoveLore());
    }

    private final static Map<String, Commands> COMMANDS = new HashMap<>();

    public static void registerCommand(Commands cmd) {
        COMMANDS.put(cmd.getCmdName(), cmd);
    }

    public static void unregisterCommand(Commands cmd) {
        COMMANDS.remove(cmd.getCmdName());
    }

    public static Set<Commands> getRegisteredCommands() {
        return new HashSet<Commands>() {
            {
                addAll(COMMANDS.values());
            }
        };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (COMMANDS.containsKey(cmd.getName().toLowerCase())) {
            Commands command = COMMANDS.get(cmd.getName().toLowerCase());

            if (command.onlyToPlayer() && !(sender instanceof Player)) {
                sender.sendMessage(parseColor("&cThis command is only available to players."));
                return false;
            }
            if (!sender.hasPermission(command.getCmdPermission())) {
                sender.sendMessage(parseColor("&cYou don't have permissions to execute &n" + command.getCmdName() + "&c command"));
                return false;
            }
            if (args.length == 0 && command.getCmdType().getArgsAmount() > 0 && !command.getCmdType().allowsTarget()) {
                for (int i = 0; i < args.length; i++) {
                    if (command.getCmdType().getArgsPerPosition().get(i) == null) {
                        continue;
                    }
                    sender.sendMessage(parseColor("&cYou need to specify an argument. \n&7" + command.getCmdType().getArgsPerPosition().get(1).toString().replace("[", "").replace("]", "")));
                    return false;
                }
            }

            for (int i = 0; i < args.length; i++) {
                Set<String> argsPerPosition = command.getCmdType().getArgsPerPosition().get(i + 1);
                if (argsPerPosition != null) {
                    if ((!command.getCmdType().allowsTarget() && argsPerPosition.contains(args[i]))
                            || !argsPerPosition.contains(args[i])) {
                        sender.sendMessage(parseColor("&cThat argument doesn't exist. Available arguments:\n&7" + argsPerPosition.toString().replace("[", "").replace("]", "")));
                        return false;
                    }
                }
            }
            command.executeCommand(sender, args);
        }

        return false;
    }

}
