package db;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;

/**
 *
 * @author HappierGore
 */
public class WarpsDAO {

    private final String warpName;
    private final Location location;
    private final Map<String, String> extras = new HashMap<>();

    public WarpsDAO(String warpName, Location location) {
        this.warpName = warpName;
        this.location = location;
    }

    public String getWarpName() {
        return warpName;
    }

    public Location getLocation() {
        return location;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WarpsDAO{");
        sb.append("warpName=").append(warpName);
        sb.append(", location=").append(location);
        sb.append(", extras=").append(extras);
        sb.append(", memory=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }

}
