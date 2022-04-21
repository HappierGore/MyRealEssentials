package com.happiergore.myrealessentials.commands.UserControl;

import com.happiergore.myrealessentials.User.UserState;
import com.happiergore.myrealessentials.commands.Arguments.ArgEnum;
import com.happiergore.myrealessentials.commands.CommandType;
import com.happiergore.myrealessentials.commands.Commands;
import com.happiergore.myrealessentials.commands.Arguments.ArgumentType;
import static com.happiergore.myrealessentials.helper.TextUtils.parseColor;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class God extends Commands {

    public God() {
        super("god", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.target).setPosition(1).optional());
            }
        }));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        Player target = null;

        if (args.length >= 1) {
            if (sender.hasPermission(this.getArgsPerms().get("other"))) {
                System.out.println("Otro");
                target = Bukkit.getPlayer(args[0]);
                System.out.println(target.toString());
            } else {
                sender.sendMessage(parseColor("&cYou aren't allowed to use this command to other players."));
                return;
            }
        }

        if (target == null) {
            if (sender instanceof Player player) {
                target = player;
            } else {
                sender.sendMessage(parseColor("&cThis command only can be used into other players while it's executing in console."));
                return;
            }
        }
        if (!UserState.players.containsKey(target)) {
            UserState.players.put(target, new UserState());
        }

        UserState userState = UserState.players.get(target);
        if (userState.isGod()) {
            sender.sendMessage(parseColor("&c" + target.getName() + " is not invinsible anymore."));
            if (!(sender instanceof Player) || (Player) sender != target) {
                target.sendMessage(parseColor("&cYour'e not invisible anymore."));
            }
            userState.setGod(false);
        } else {
            sender.sendMessage(parseColor("&a" + target.getName() + " is invinsible now."));
            userState.setGod(true);
            if (!(sender instanceof Player) || (Player) sender != target) {
                target.sendMessage(parseColor("&aYour'e invisible now."));
            }
        }
    }

}
