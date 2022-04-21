package com.happiergore.myrealessentials.commands;

import com.happiergore.myrealessentials.commands.Arguments.ArgEnum;
import com.happiergore.myrealessentials.commands.Arguments.ArgumentType;
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
        //System.out.println("Permissions of " + cmdName + "\n" + argPermissions.toString());
        this.cmdType = init(cmdPermission, cmdType);
    }

    public Commands(CommandType cmdType) {
        this.cmdName = "myessentials";
        this.cmdPermission = cmdName;
        //System.out.println("Permissions of " + cmdName + "\n" + argPermissions.toString());
        this.cmdType = init(cmdPermission, cmdType);
    }

    private final CommandType init(String cmdPermission, CommandType cmdType) {
        if (cmdType.allowsTarget()) {
            this.argPermissions.put("other", cmdPermission + ".other");
        }

        for (ArgumentType arg : cmdType.getArgs()) {
            if (!arg.usesPermission()) {
                break;
            }
            if (!arg.getUniquePermission().isBlank()) {
                this.argPermissions.put("generic", cmdPermission + "." + arg.getUniquePermission());
                break;
            }

            if (arg.getArgType() == ArgEnum.list) {
                arg.getList().forEach(entry -> {
                    this.argPermissions.put("arg:" + entry, cmdPermission + "." + entry);
                });
                break;
            }

            if (!arg.getName().isBlank()) {
                this.argPermissions.put(arg.getName(), cmdPermission + "." + arg.getName());
            }
        }
        return cmdType;
    }

    public abstract void executeCommand(CommandSender sender, String[] args);

    public final void onlyPlayer(boolean value) {
        this.onlyPlayer = value;
    }

    public final boolean onlyToPlayer() {
        return this.onlyPlayer;
    }

    public final String getCmdName() {
        return cmdName;
    }

    public final String getCmdPermission() {
        return cmdPermission;
    }

    public final Map<String, String> getArgsPerms() {
        return this.argPermissions;
    }

    public final CommandType getCmdType() {
        return cmdType;
    }

    public void refreshArgList() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Commands{");
        sb.append("cmdName=").append(cmdName);
        sb.append(", cmdPermission=").append(cmdPermission);
        sb.append(", onlyPlayer=").append(onlyPlayer);
        sb.append(", argPermissions=").append(argPermissions);
        sb.append(", cmdType=").append(cmdType);
        sb.append('}');
        return sb.toString();
    }

}
