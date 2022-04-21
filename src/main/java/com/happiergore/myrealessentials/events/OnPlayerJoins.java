
package com.happiergore.myrealessentials.events;

import com.happiergore.myrealessentials.Behaviours.PauseMoveInventory;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlayerJoins {
    public void OnPlayerJoins(PlayerJoinEvent e){
        new PauseMoveInventory().OnPlayerJoins(e);
    }
}
