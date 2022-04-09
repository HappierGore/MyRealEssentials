package commands.Arguments;

import commands.CommandManager;
import commands.Commands;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

            if (command.getCmdName().equalsIgnoreCase(cmd.getName())) {
                for (int i = 1; i <= command.getCmdType().getArgsSize(); i++) {
                    for (ArgumentType argType : command.getCmdType().getArgsInPosition(i)) {
                        if (argType.getPosition() == args.length) {
                            if (argType.getArgType() == ArgEnum.target) {
                                return null;
                            }
                            if (argType.getArgType() == ArgEnum.list) {
                                command.refreshArgList();
                                return argType.getList();
                            }
                        }
                    }
                }

                if (!command.getArgsTab().isEmpty()) {
                    Set<ArgumentType> argTab = command.getArgsTab();
                    Map<String, String> argPerm = command.getArgsPerms();
                    argTab.forEach((arg) -> {
                        if (args.length == arg.getPosition()) {
                            if (sender.hasPermission(argPerm.get(arg.getName()))) {
                                completions.add(arg.getName());
                            }
                        }
                    });
                }
            }
        }
        return completions;
    }
}
