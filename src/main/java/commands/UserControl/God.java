package commands.UserControl;

import User.UserState;
import commands.CommandType;
import commands.Commands;
import static helper.TextUtils.parseColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class God extends Commands {

    public God() {
        super("god", new CommandType(1, null).allowTargeting(1));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        Player target = this.generateTarget(sender, args, 0);

        if (target == null) {
            return;
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
