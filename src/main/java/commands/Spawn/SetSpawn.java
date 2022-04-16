package commands.Spawn;

import com.happiergore.myrealessentials.main;
import static com.happiergore.myrealessentials.main.luckPerms;
import commands.Arguments.ArgEnum;
import commands.Arguments.ArgumentType;
import commands.CommandType;
import commands.Commands;
import static helper.TextUtils.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class SetSpawn extends Commands {

    public SetSpawn() {
        super("setspawn", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.list).setPosition(1).noPermission().optional().setHint("&cPlease, insert the group name where this spawn will be.").setUniquePermission("setspawn"));
            }
        }));
        this.onlyPlayer(true);
    }

    @Override
    public void refreshArgList() {
        List<String> groups = new ArrayList<>();

        luckPerms.getGroupManager().getLoadedGroups().forEach(group -> {
            groups.add(group.getName());
        });

        this.getCmdType().getArgsInPosition(1).forEach(arg -> {
            arg.setList(groups);
        });
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        FileConfiguration config = main.configYML;
        String path = "Spawnpoint.";
        String defaultPath = path + "default";
        String groupPath = "";
        String location = formatLocation(((Player) sender).getLocation()).toString();
        if (args.length > 0) {
            groupPath = path + "SpawnPerGroup." + args[0];
        }
        if (groupPath.isBlank()) {
            sender.sendMessage(parseColor("&aThe spawnpoint for &beveryone&a has been placed in your current position."));
            config.set(defaultPath, location);
        } else {
            sender.sendMessage(parseColor("&aThe spawnpoint for group &b" + args[0] + " has been placed in your current position"));
            config.set(groupPath, location);
        }

        main.getPlugin(main.class).saveConfig();
    }

}
