package events;

import Behaviours.PauseMoveInventory;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 *
 * @author HappierGore
 */
public class ItemDropEvent {

    public static void OnItemDropEvent(PlayerDropItemEvent e) {
        PauseMoveInventory.OnDropItem(e);
    }
}
