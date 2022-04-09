package commands;

import commands.Arguments.ArgumentType;
import commands.Arguments.ArgEnum;
import commands.WarpControl.SetWarp;
import commands.ItemControl.*;
import commands.UserControl.*;
import commands.WarpControl.DeleteWarp;
import static helper.TextUtils.parseColor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
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
        registerCommand(new SetWarp());
        registerCommand(new DeleteWarp());
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
            CommandType cmdType = command.getCmdType();

            if (command.onlyToPlayer() && !(sender instanceof Player)) {
                sender.sendMessage(parseColor("&cThis command is only available to players."));
                return false;
            }
            if (!sender.hasPermission(command.getCmdPermission())) {
                sender.sendMessage(parseColor("&cYou don't have permissions to execute &n" + command.getCmdName() + "&c command"));
                return false;
            }

            //Loop over the size of the args registered by the command
            for (int j = 1; j <= cmdType.getArgsSize(); j++) {

                //If the args size is greather or equal to the size of args registered by the command, then validate it
                if (args.length >= j) {
                    //Map to register any kind of errors
                    Set<ArgEnum> err = new HashSet<>();

                    //Flag to know if the arg put it exists into args registered in the command
                    boolean found = false;

                    //Loop over all arguments in specific position
                    for (ArgumentType argType : cmdType.getArgsInPosition(j)) {

                        //If the argument put it is equal to the actual argument by cmd or is blank, then we found it.
                        if (!found && (argType.getName().equalsIgnoreCase(args[j - 1]) || argType.getName().isBlank())) {
                            found = true;
                        }

                        //Switch to validate the argument, if one of the options is valid, then the map will be empty.
                        //This helps to allows different kind of argument per position, We can use arguments of type
                        //String or numeric in the same position without problems
                        switch (argType.getArgType()) {
                            case target -> {
                                if (Bukkit.getPlayer(args[j - 1]) == null) {
                                    err.add(argType.getArgType());
                                } else {
                                    err.remove(argType.getArgType());
                                }
                            }
                            case bool -> {
                                if (args[j - 1].equalsIgnoreCase("false") || args[j - 1].equalsIgnoreCase("true")) {
                                    err.remove(argType.getArgType());
                                } else {
                                    err.add(argType.getArgType());
                                }
                            }
                            case integer -> {
                                try {
                                    Integer.parseInt(args[j - 1]);
                                    err.remove(argType.getArgType());
                                } catch (NumberFormatException e) {
                                    err.add(argType.getArgType());
                                }
                            }
                            case string -> {
                                if (args[j - 1].isBlank()) {
                                    err.add(argType.getArgType());
                                } else {
                                    err.remove(argType.getArgType());
                                }
                            }
                            case list -> {
                                if (!argType.getList().contains(args[j - 1])) {
                                    err.add(argType.getArgType());
                                } else {
                                    err.remove(argType.getArgType());
                                }
                            }
                        }
                    }

                    //If the argument wasn't found, then send the arguments available in that position.
                    if (!found) {
                        sender.sendMessage(parseColor("&cThat argument doesn't exist. Available arguments: \n" + String.join(", ", cmdType.getArgsNamesInPosition(j))));
                        return false;
                    }

                    //Now, we'll send the error msg if it exists.
                    for (ArgEnum er : err) {
                        //If there are an hint message, give it priority
                        for (ArgumentType t : cmdType.getArgsInPosition(j)) {
                            if (!t.isOptional()) {
                                if (!t.getHint().isBlank()) {
                                    sender.sendMessage(parseColor(t.getHint()));
                                    return false;
                                }
                            }
                        }
                        switch (er) {
                            case target -> {
                                sender.sendMessage(parseColor("&cThe player on argument " + j + " doesn't exist or it's not online. Please try again."));
                                return false;
                            }
                            case bool -> {
                                sender.sendMessage(parseColor("&cYou have to use true / false in param: " + j));
                                return false;
                            }
                            case integer -> {
                                sender.sendMessage(parseColor("&cYou have to use a numeric value in param: " + j));
                                return false;
                            }
                            case string -> {
                                sender.sendMessage(parseColor("&cYou have to write some value in param: " + j));
                                return false;
                            }
                            case list -> {
                                sender.sendMessage(parseColor("&cYou have to insert a valid value of the list provided in param " + j));
                                return false;
                            }
                        }
                    }

                } else {
                    //Loop over the arguments in position j, if it's optional and it has not name, then we'll send the custom hint.
                    //If not, default message with arguments necessary will be sent.
                    for (ArgumentType t : cmdType.getArgsInPosition(j)) {
                        if (!t.isOptional()) {
                            if (t.getName().isBlank()) {
                                sender.sendMessage(parseColor(t.getHint()));
                            } else {
                                sender.sendMessage(parseColor("&cThis command needs arguments to works:\n " + String.join(", ", cmdType.getArgsNamesInPosition(j))));
                            }
                            return false;
                        }
                    }
                }
            }

            command.executeCommand(sender, args);
        }

        return false;
    }
}
