package com.happiergore.myrealessentials.commands.WarpControl;

import com.happiergore.myrealessentials.commands.Arguments.ArgumentType;
import com.happiergore.myrealessentials.commands.CommandType;
import com.happiergore.myrealessentials.commands.Commands;
import com.happiergore.myrealessentials.db.SQLite.WarpsJBDC;
import static com.happiergore.myrealessentials.helper.TextUtils.parseColor;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.command.CommandSender;

/**
 *
 * @author HappierGore
 */
public class Warps extends Commands {

    public Warps() {
        super("warps", new CommandType(new HashSet<ArgumentType>()));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        Set<String> allowedWarps = new HashSet<>();
        WarpsJBDC.WARPS_REGISTERED.forEach((key, value) -> {
            String permission = value.getPermission();
            if (permission.isBlank() || sender.hasPermission(permission)) {
                allowedWarps.add(value.getExtras().get("displayName"));
            }
        });
        if (!allowedWarps.isEmpty()) {
            sender.sendMessage(parseColor("&6------------ Warps list ------------\n&bWarps:\n" + String.join(", ", allowedWarps) + "\n&6---------------------------------"));
        } else {
            sender.sendMessage(parseColor("&cYou don't have permissions for any warp."));
        }
    }

}
