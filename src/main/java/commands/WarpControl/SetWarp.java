package commands.WarpControl;

import commands.Arguments.ArgEnum;
import commands.Arguments.ArgumentType;
import commands.CommandType;
import commands.Commands;
import db.SQLite.WarpsDAO;
import db.SQLite.WarpsJBDC;
import static helper.TextUtils.parseColor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class SetWarp extends Commands {

    public SetWarp() {
        super("setwarp", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.string).setPosition(1).setHint("&cYou need to specify a warp name."));
                add(new ArgumentType(ArgEnum.bool).setName("true").setPosition(2).optional());
                add(new ArgumentType(ArgEnum.bool).setName("false").setPosition(2).optional());
            }
        }));
        this.onlyPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {

        String warpName = "";
        String displayName = args[0];
        Player player = (Player) sender;

        Location location = player.getLocation();

        String[] splited = args[0].split("");
        for (int i = 0; i < splited.length; i++) {
            if (splited[i].equals("&")) {
                splited[i] = "";
                if (splited.length >= i) {
                    splited[i + 1] = "";
                }
            }
        }
        warpName = String.join("", splited);

        System.out.println("Fixed name: " + warpName);

        boolean isPPrivate = args.length > 1 && args[1].equalsIgnoreCase("false");

        Map<String, String> extra = new HashMap<>();

        extra.put("createdBy", player.getDisplayName());
        extra.put("type", isPPrivate ? "private" : "public");
        extra.put("displayName", displayName);

        WarpsDAO warp = new WarpsDAO(warpName, location, extra);

        if (WarpsJBDC.WARPS_REGISTERED.containsKey(warp.getWarpName())) {
            if (WarpsJBDC.update(warp)) {
                player.sendMessage(parseColor("&aThe warp " + warp.getExtras().get("displayName") + " &ahas been updated successfully."));
            } else {
                player.sendMessage(parseColor("&cThere was an problem when trying to update the warp. Please check the console and try again later."));
            }
            return;
        }

        if (WarpsJBDC.insert(warp)) {
            player.sendMessage(parseColor("&aThe warp " + warp.getExtras().get("displayName") + " &ahas been saved succesfully."));
        } else {
            player.sendMessage(parseColor("&cThere was an problem when trying to save the warp. Please check the console and try again later."));
        }

    }

}
