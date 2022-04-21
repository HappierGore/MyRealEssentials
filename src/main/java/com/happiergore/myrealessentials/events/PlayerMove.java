package com.happiergore.myrealessentials.events;

import com.happiergore.myrealessentials.Behaviours.CancelTeleport;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author HappierGore
 */
public class PlayerMove {

    public void PlayerMoveEvent(PlayerMoveEvent e) {
        CancelTeleport.OnPlayerMoving(e);
    }
}
