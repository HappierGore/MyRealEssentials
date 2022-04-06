package commands;

import static helper.TextUtils.parseColor;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public abstract class Commands {

    private final String cmdName;
    private final String cmdPermission;
    private boolean onlyPlayer = false;
    private final Map<String, String> arguments = new HashMap<>();
    private final CommandType cmdType;

    public Commands(String cmdName, CommandType cmdType) {
        this.cmdName = cmdName;
        this.cmdPermission = "myessentials." + cmdName;

        if (cmdType.allowsTarget()) {
            this.arguments.put("other", "myessentials." + cmdName + ".other");
        }

        cmdType.getArgsPerPosition().values().forEach(argArr -> {
            for (String arg : argArr) {
                this.arguments.put(arg, "myessentials." + cmdName + "." + arg);
            }
        });

        this.cmdType = cmdType;
    }

    public abstract void executeCommand(CommandSender sender, String[] args);

    public Player generateTarget(CommandSender sender, String[] args, int targetPosition) {
        Player target;
        if (args.length > targetPosition) {
            target = Bukkit.getPlayer(args[targetPosition]);

            if (target == null) {
                sender.sendMessage(parseColor("&cThat player doesn't exist or is not online. Please, try again"));
                return null;
            }
        } else if (sender instanceof Player player) {
            target = player;
        } else {
            sender.sendMessage(parseColor("&cYou can only use this command to other players."));
            return null;
        }
        return target;
    }

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

    public Map<String, String> getArguments() {
        return arguments;
    }

    public CommandType getCmdType() {
        return cmdType;
    }

}
