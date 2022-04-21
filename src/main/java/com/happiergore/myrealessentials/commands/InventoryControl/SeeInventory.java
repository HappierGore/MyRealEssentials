package com.happiergore.myrealessentials.commands.InventoryControl;

import com.happiergore.myrealessentials.Behaviours.PauseMoveInventory;
import com.happiergore.myrealessentials.commands.Arguments.ArgEnum;
import com.happiergore.myrealessentials.commands.Arguments.ArgumentType;
import com.happiergore.myrealessentials.commands.CommandType;
import com.happiergore.myrealessentials.commands.Commands;
import static com.happiergore.myrealessentials.helper.TextUtils.parseColor;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class SeeInventory extends Commands {

    public SeeInventory() {
        super("seeinventory", new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.target).setPosition(1));
            }
        }));
        this.onlyPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);

        Player autor = (Player) sender;

        if (target == autor) {
            autor.sendMessage(parseColor("&cYou should see your own inventory using \"E\""));
            return;
        }
        PauseMoveInventory.RegisterUser(target, autor);
        target.sendMessage(parseColor("&6An admin is inspecting your inventory. You won't be able to move your inventory items until it finishes."));
        autor.openInventory(target.getInventory());
        autor.sendMessage(parseColor("&6" + target.getDisplayName() + "'s inventorys opened."));
    }

}
