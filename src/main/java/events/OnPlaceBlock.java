package events;

import Behaviours.PauseMoveInventory;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlaceBlock {

    public static void OnPlaceBlock(BlockPlaceEvent e) {
        PauseMoveInventory.OnPlaceBlock(e);
    }
}
