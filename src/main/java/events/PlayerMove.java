package events;

import commands.WarpControl.Warp;
import static helper.TextUtils.parseColor;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author HappierGore
 */
public class PlayerMove {

    public static void OnPlayerMove(PlayerMoveEvent e) {
        if (Warp.teleport.containsKey(e.getPlayer())) {
            if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                Warp.teleport.get(e.getPlayer()).cancel();
                Warp.teleport.remove(e.getPlayer());
                e.getPlayer().sendMessage(parseColor("&cYou have moved. Teleport has been cancelled"));
            }
        }
    }
}
