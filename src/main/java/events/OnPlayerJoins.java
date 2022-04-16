
package events;

import Behaviours.PauseMoveInventory;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlayerJoins {
    public static void OnPlayerJoins(PlayerJoinEvent e){
        PauseMoveInventory.OnPlayerJoins(e);
    }
}
