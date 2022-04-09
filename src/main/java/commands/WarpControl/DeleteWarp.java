package commands.WarpControl;

import commands.Arguments.ArgEnum;
import commands.Arguments.ArgumentType;
import commands.CommandType;
import commands.Commands;
import db.WarpsJBDC;
import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.command.CommandSender;

/**
 *
 * @author HappierGore
 */
public class DeleteWarp extends Commands {

    public DeleteWarp() {
        super("delwarp", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.list).setPosition(1).setHint("&cYou need to select a valid warp to delete it. Please try again.").setList(new ArrayList<String>() {
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
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        System.out.println("You want to delete: " + args[0]);
    }

}
