package Behaviours;

import User.UserState;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodBehaviour {

    public static void OnPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (UserState.players.containsKey(player)) {
                UserState userState = UserState.players.get(player);
                if (userState.isGod()) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
