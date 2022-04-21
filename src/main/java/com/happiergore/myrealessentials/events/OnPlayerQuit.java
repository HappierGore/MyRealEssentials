package com.happiergore.myrealessentials.events;

import com.happiergore.myrealessentials.Behaviours.PauseMoveInventory;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlayerQuit {

    public void OnPlayerQuit(PlayerQuitEvent e) {
        new PauseMoveInventory().OnPlayerLeaves(e);
    }
}
