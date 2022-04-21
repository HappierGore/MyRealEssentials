package com.happiergore.myrealessentials.events;

import com.happiergore.myrealessentials.Behaviours.CancelTeleport;
import com.happiergore.myrealessentials.Behaviours.GodBehaviour;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlayerDamage {

    public void OnPlayerDamage(EntityDamageEvent e) {
        CancelTeleport.OnPlayerDamaged(e);
        GodBehaviour.OnPlayerDamage(e);

    }
}
