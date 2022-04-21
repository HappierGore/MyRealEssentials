package com.happiergore.myrealessentials.commands.Mobs;

import com.happiergore.myrealessentials.commands.Arguments.ArgEnum;
import com.happiergore.myrealessentials.commands.Arguments.ArgumentType;
import com.happiergore.myrealessentials.commands.CommandType;
import com.happiergore.myrealessentials.commands.Commands;
import static com.happiergore.myrealessentials.helper.TextUtils.parseColor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class SpawnMob extends Commands {

    public SpawnMob() {
        super("spawnmob", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.list).setHint("&cYou have to insert a mob name.").setPosition(1).noPermission().ListToUpperCase());
                add(new ArgumentType(ArgEnum.target).optional().setPosition(2));
                add(new ArgumentType(ArgEnum.integer).optional().setPosition(2).setHint("&cInsert the amount of mobs that will spawn or a username").noPermission());
                add(new ArgumentType(ArgEnum.target).optional().setPosition(3).setHint("&cUsage: /spawnmob <Mob> <Amount> <Target>"));
            }
        }));
        this.getCmdType().getArgsInPosition(1).forEach(arg -> {
            if (arg.getArgType() == ArgEnum.list) {
                List<String> mobs = new ArrayList<>();
                for (EntityType entity : EntityType.values()) {
                    mobs.add(entity.name());
                }
                arg.setList(mobs);
            }
        });
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        Player target = null;

        EntityType mob = EntityType.valueOf(args[0].toUpperCase());

        int amount = 1;
        //target in arg[1] ó arg[2]
        //amoun in arg[1]

        //1- spawnmob zombie HappierGore
        //2- spawnmob zombie 2 HappierGore
        //3- spawnmob zombie 2
        //4- spawnmob zombie
        //Try get target by second argument (arg[1)
        if (args.length >= 2) {
            //When we insert option 1
            target = Bukkit.getPlayer(args[1]);
            //When we insert option 2
            if (target == null) {
                amount = Integer.parseInt(args[1]);
                if (args.length >= 3) {
                    target = Bukkit.getPlayer(args[2]);
                }
                if (target == null && sender instanceof Player player) {
                    target = player;
                }
            }
        } else {
            if (sender instanceof Player player) {
                target = player;
            }
        }

        System.out.println("Target: " + target);
        System.out.println("Amount: " + amount);
        if (target == null) {
            sender.sendMessage(parseColor("&cYou have to set a valid target."));
            return;
        }

        for (int i = 0; i < amount; i++) {
            target.getWorld().spawnEntity(target.getLocation(), mob);
        }

        String msg = "&6" + amount + " " + args[0] + " has spawned in ";
        if (!(sender instanceof Player) || target != (Player) sender) {
            msg += target.getDisplayName() + " location.";
        } else {
            msg += "your location.";
        }
        sender.sendMessage(parseColor(msg));

    }

}
