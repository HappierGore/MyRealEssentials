package events;

import Behaviours.PauseMoveInventory;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlayerQuit {

    public static void OnPlayerQuit(PlayerQuitEvent e) {
        PauseMoveInventory.OnPlayerLeaves(e);
    }
}
