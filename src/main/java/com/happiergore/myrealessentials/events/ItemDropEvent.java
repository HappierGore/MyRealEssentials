package com.happiergore.myrealessentials.events;

import com.happiergore.myrealessentials.Behaviours.PauseMoveInventory;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 *
 * @author HappierGore
 */
public class ItemDropEvent {

    public void OnItemDropEvent(PlayerDropItemEvent e) {
        new PauseMoveInventory().OnDropItem(e);
    }
}
