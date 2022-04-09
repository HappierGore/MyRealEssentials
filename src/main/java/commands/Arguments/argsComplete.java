package commands.Arguments;

import commands.CommandManager;
import commands.Commands;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 *
 * @author HappierGore
 */
public class argsComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        for (Commands command : CommandManager.getRegisteredCommands()) {

            //If the command executed exist into commands registered
            if (command.getCmdName().equalsIgnoreCase(cmd.getName())) {

                //Loop over each command registered
                for (int i = 1; i <= command.getCmdType().getArgsSize(); i++) {

                    //Loop over each argument of the command
                    for (ArgumentType argType : command.getCmdType().getArgsInPosition(i)) {

                        //Check the right argument per position if the user has permissions base (cmd)
                        if (argType.getPosition() == args.length && sender.hasPermission(command.getCmdPermission())) {

                            //If the argument type is equal to target and has permissions to use it, then return null (Player)
                            if (argType.getArgType() == ArgEnum.target) {
                                if (argType.usesPermission() && sender.hasPermission(command.getArgsPerms().get("other"))) {
                                    return null;
                                } else {
                                    return completions;
                                }
                            }

                            //If argument type is list, we'll refresh the list
                            if (argType.getArgType() == ArgEnum.list) {
                                command.refreshArgList();

                                //If the argument don't use permissions, then return all the list
                                if (!argType.usesPermission()) {
                                    return argType.getList();
                                }

                                //If the argument uses permissions, we'll loop over each of them.
                                command.getArgsPerms().forEach((name, perm) -> {

                                    //If has "arg:", remove it
                                    String fxName = name.contains("arg:") ? name.replaceAll("arg:", "") : name;

                                    //If has permission, then add it into list                                    
                                    if (sender.hasPermission(perm)) {
                                        completions.add(fxName);
                                    }
                                });

                                return completions;
                            }
                            //If the type is another, then just add the name
                            if (sender.hasPermission(command.getArgsPerms().get(argType.getName()))) {
                                completions.add(argType.getName());
                            }

                        }
                    }
                }
            }
        }
        return completions;
    }
}
