package commands.WarpControl;

import com.happiergore.myrealessentials.main;
import commands.Arguments.ArgEnum;
import commands.Arguments.ArgumentType;
import commands.CommandType;
import commands.Commands;
import db.SQLite.WarpsDAO;
import db.SQLite.WarpsJBDC;
import static helper.TextUtils.errorMsg;
import static helper.TextUtils.parseColor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author HappierGore
 */
public class Warp extends Commands {

    public static Map<Player, BukkitTask> teleport = new HashMap<>();

    public Warp() {
        super("warp", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.list).setPosition(1).setHint("&cYou need to select a valid warp to go.").setList(new ArrayList<String>() {
                    {
                        addAll(WarpsJBDC.WARPS_REGISTERED.keySet());
                    }
                }));
            }
        }));
    }

    @Override
    public void refreshArgList() {
        this.getCmdType().getArgsInPosition(1).forEach(arg -> {
            arg.setList(new ArrayList<String>() {
                {
                    addAll(WarpsJBDC.WARPS_REGISTERED.keySet());
                }
            });
        });
        this.onlyPlayer(true);
    }

    private void teleportPlayer(Player player, int time, Location location) {
        teleport.put(player, Bukkit.getScheduler().runTaskLater(main.getPlugin(main.class), () -> {
            player.teleport(location);
            teleport.remove(player);
        }, time * 20));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        for (WarpsDAO value : WarpsJBDC.WARPS_REGISTERED.values()) {
            String permission = value.getPermission();
            if ((permission.isBlank() || sender.hasPermission(permission)) && value.getWarpName().equalsIgnoreCase(args[0])) {
                int time = main.configYML.getInt("Warps.TimeToTeleport");

                //Try to get the time specified for each group and takes the lower
                for (Object i : main.configYML.getMapList("Warps.TimePerGroup").toArray()) {
                    String entryFxd = i.toString().replaceAll("\\{", "").replaceAll("\\}", "");
                    String group = entryFxd.split("=")[0];
                    try {
                        int groupTime = Integer.parseInt(entryFxd.split("=")[1]);
                        if (sender.hasPermission("group." + group) && time > groupTime) {
                            time = groupTime;
                        }
                    } catch (NumberFormatException e) {
                        String errMsg = "There is an error in your config.yml\nError in group: " + group + ", invalid time. Please, just use numeric values.";
                        System.out.println(errorMsg(errMsg, true));
                    }
                }
                sender.sendMessage(parseColor("&aYou will be teleported to " + value.getExtras().get("displayName") + "&a in &b" + time + " &aseconds, don't move."));

                if (!teleport.containsKey((Player) sender)) {
                    teleportPlayer((Player) sender, time, value.getLocation());
                } else {
                    teleport.get((Player) sender).cancel();
                    teleportPlayer((Player) sender, time, value.getLocation());
                }

                break;
            } else {
                if (!sender.hasPermission(permission)) {
                    sender.sendMessage(parseColor("&cYou don't have permissions to use this warp."));
                    break;
                }
            }
        }
    }
}
