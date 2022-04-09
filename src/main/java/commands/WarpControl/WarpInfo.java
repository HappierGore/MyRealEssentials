/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands.WarpControl;

import commands.Arguments.ArgEnum;
import commands.Arguments.ArgumentType;
import commands.CommandType;
import commands.Commands;
import db.WarpsDAO;
import db.WarpsJBDC;
import static helper.TextUtils.parseColor;
import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.command.CommandSender;

/**
 *
 * @author HappierGore
 */
public class WarpInfo extends Commands {

    public WarpInfo() {
        super("warpinfo", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.list).setPosition(1).setHint("&cYou need to select a valid warp to check info. Please try again.").setList(new ArrayList<String>() {
                    {
                        addAll(WarpsJBDC.WARPS_REGISTERED.keySet());
                    }
                }).noPermission());
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

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        WarpsDAO warp = WarpsJBDC.WARPS_REGISTERED.get(args[0]);
        StringBuilder str = new StringBuilder();
        str.append("&6--------- Warp information ---------");
        str.append("\n&aWarp name:  ").append(warp.getWarpName());
        str.append("\n&aDisplay name: ").append(warp.getExtras().get("displayName"));
        str.append("\n&aCreated by: &e").append(warp.getExtras().get("createdBy"));
        str.append("\n&aThis warp is: &e").append(warp.getExtras().get("type"));
        str.append("\n&aWorld: &e").append(warp.getLocation().getWorld().getName());
        str.append("\n&aCoordinates (XYZ): &e");
        str.append(warp.getLocation().getX()).append("&a, &e").append(warp.getLocation().getY()).append("&a, &e").append(warp.getLocation().getZ());
        str.append("\n&6---------------------------------");
        sender.sendMessage(parseColor(str.toString()));
    }

}
