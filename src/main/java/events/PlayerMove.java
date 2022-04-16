package events;

import Behaviours.CancelTeleport;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author HappierGore
 */
public class PlayerMove {

    public static void PlayerMoveEvent(PlayerMoveEvent e) {
        CancelTeleport.OnPlayerMoving(e);
    }
}
