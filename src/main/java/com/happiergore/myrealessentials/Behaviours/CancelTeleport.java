package com.happiergore.myrealessentials.Behaviours;

import com.happiergore.myrealessentials.commands.WarpControl.Warp;
import static com.happiergore.myrealessentials.helper.TextUtils.parseColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CancelTeleport {

    private static void cancelTeleport(Player target, String reason) {
        Warp.teleport.get(target).cancel();
        Warp.teleport.remove(target);
        target.sendMessage(parseColor(reason + ". Teleport has been cancelled"));
    }

    public static void OnPlayerMoving(PlayerMoveEvent e) {
        if (Warp.teleport.containsKey(e.getPlayer())) {
            if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                cancelTeleport(e.getPlayer(), "&cYou have moved");
            }
        }
    }

    public static void OnPlayerDamaged(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (Warp.teleport.containsKey(player)) {
                cancelTeleport(player, "&cYou got damage");
            }
        }
    }
}
