package com.happiergore.myrealessentials.User;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class UserState {

    public static Map<Player, UserState> players = new HashMap<>();

    private boolean god;

    public boolean isGod() {
        return god;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

}
