package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        if (args.length > 1) {
            return null;
        }
        for (Commands command : CommandManager.getRegisteredCommands()) {
            if (command.getCmdName().equalsIgnoreCase(cmd.getName())) {
                //for arguments
                if (command.getArguments() != null) {
                    Map<String, String> arguments = command.getArguments();
                    arguments.forEach((argument, permission) -> {
                        if (sender.hasPermission(permission)) {
                            completions.add(argument);
                        }
                    });
                    break;
                } else {
                    return null;
                }
            }
        }

        return completions;
    }

}
