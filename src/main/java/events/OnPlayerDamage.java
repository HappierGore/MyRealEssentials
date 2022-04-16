package events;

import Behaviours.CancelTeleport;
import Behaviours.GodBehaviour;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlayerDamage {

    public static void OnPlayerDamage(EntityDamageEvent e) {
        CancelTeleport.OnPlayerDamaged(e);
        GodBehaviour.OnPlayerDamage(e);

    }
}
