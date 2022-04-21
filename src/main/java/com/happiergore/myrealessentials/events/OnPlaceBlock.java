package com.happiergore.myrealessentials.events;

import com.happiergore.myrealessentials.Behaviours.PauseMoveInventory;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlaceBlock {

    public void OnPlaceBlock(BlockPlaceEvent e) {
        new PauseMoveInventory().OnPlaceBlock(e);
    }
}
