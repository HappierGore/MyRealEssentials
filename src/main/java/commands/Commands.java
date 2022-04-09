package commands;

import commands.Arguments.ArgEnum;
import commands.Arguments.ArgumentType;
import java.util.HashMap;
import java.util.Map;
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
    private final CommandType cmdType;

    public Commands(String cmdName, CommandType cmdType) {
        this.cmdName = cmdName;
        this.cmdPermission = "myessentials." + cmdName;

        if (cmdType.allowsTarget()) {
            this.argPermissions.put("other", "myessentials." + cmdName + ".other");
        }

        for (ArgumentType arg : cmdType.getArgs()) {
            if (!arg.usesPermission()) {
                break;
            }
            if (!arg.getUniquePermission().isBlank()) {
                this.argPermissions.put("generic", "myessentials." + cmdName + "." + arg.getUniquePermission());
                break;
            }

            if (arg.getArgType() == ArgEnum.list) {
                arg.getList().forEach(entry -> {
                    this.argPermissions.put("arg:" + entry, "myessentials." + cmdName + "." + entry);
                });
                break;
            }

            if (!arg.getName().isBlank()) {
                this.argPermissions.put(arg.getName(), "myessentials." + cmdName + "." + arg.getName());
            }
        }
        //System.out.println("Permissions of " + cmdName + "\n" + argPermissions.toString());

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

    public CommandType getCmdType() {
        return cmdType;
    }

    public void refreshArgList() {

    }

}
