package events;

import User.UserState;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author HappierGore
 */
public class OnPlayerDamage {

    public static void OnPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (UserState.players.containsKey((Player) e.getEntity())) {
                UserState userState = UserState.players.get((Player) e.getEntity());
                if(userState.isGod()){
                    e.setCancelled(true);
                }
            }
        }
    }
}
