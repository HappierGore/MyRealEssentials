package commands.WarpControl;

import commands.Arguments.ArgEnum;
import commands.Arguments.ArgumentType;
import commands.CommandType;
import commands.Commands;
import db.WarpsDAO;
import db.WarpsJBDC;
import static helper.TextUtils.parseColor;
import java.util.HashSet;
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
                add(new ArgumentType(ArgEnum.bool).setName("true").setPosition(2).optional().registerInTab());
                add(new ArgumentType(ArgEnum.bool).setName("false").setPosition(2).optional().registerInTab());
            }
        }));
        this.onlyPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {

        String warpName = args[0];
        Player player = (Player) sender;

        Location location = player.getLocation();

        WarpsDAO warp = new WarpsDAO(warpName, location);

        warp.getExtras().put("Created by", player.getDisplayName());
        warp.getExtras().put("Type", args.length > 1 ? (args[1].equalsIgnoreCase("true") ? "public" : "private") : "public");

        if (WarpsJBDC.WARPS_REGISTERED.containsKey(warp.getWarpName())) {
            if (WarpsJBDC.update(warp)) {
                player.sendMessage(parseColor("&aThe warp " + warpName) + " has been updated successfully.");
            } else {
                player.sendMessage(parseColor("&cThere was an problem when trying to update the warp. Please check the console and try again later."));
            }
            return;
        }

        if (WarpsJBDC.insert(warp)) {
            player.sendMessage(parseColor("&aThe warp " + warpName + " has been saved succesfully."));
        } else {
            player.sendMessage(parseColor("&cThere was an problem when trying to save the warp. Please check the console and try again later."));
        }

    }

}
