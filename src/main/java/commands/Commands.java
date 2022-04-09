package commands;

import commands.Arguments.ArgumentType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.command.CommandSender;

/**
 *
 * @author HappierGore
 */
public abstract class Commands {

    private final String cmdName;
    private final String cmdPermission;
    private boolean onlyPlayer = false;
    private final Map<String, String> argPermissions = new HashMap<>();
    private final Set<ArgumentType> argsToTab = new HashSet<>();
    private final CommandType cmdType;

    public Commands(String cmdName, CommandType cmdType) {
        this.cmdName = cmdName;
        this.cmdPermission = "myessentials." + cmdName;

        if (cmdType.allowsTarget()) {
            this.argPermissions.put("other", "myessentials." + cmdName + ".other");
        }

        cmdType.getArgs().forEach(arg -> {
            this.argPermissions.put(arg.getName(), "myessentials." + cmdName + "." + arg.getName());
            if (arg.toTab()) {
                argsToTab.add(arg);
            }
        });

        this.cmdType = cmdType;
    }

    public abstract void executeCommand(CommandSender sender, String[] args);

    public void onlyPlayer(boolean value) {
        this.onlyPlayer = value;
    }

    public boolean onlyToPlayer() {
        return this.onlyPlayer;
    }

    public String getCmdName() {
        return cmdName;
    }

    public String getCmdPermission() {
        return cmdPermission;
    }

    public Map<String, String> getArgsPerms() {
        return this.argPermissions;
    }

    public Set<ArgumentType> getArgsTab() {
        return this.argsToTab;
    }

    public CommandType getCmdType() {
        return cmdType;
    }

    public void refreshArgList() {

    }

}
