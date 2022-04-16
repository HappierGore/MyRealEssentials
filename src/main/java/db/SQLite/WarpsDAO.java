package db.SQLite;

import java.util.Map;
import org.bukkit.Location;

/**
 *
 * @author HappierGore
 */
public class WarpsDAO {

    private final String warpName;
    private final Location location;
    private final Map<String, String> extras;
    private String permission = "";

    //Used from DB
    public WarpsDAO(String warpName, Location location, Map<String, String> extras) {
        this.warpName = warpName;
        this.location = location;
        this.extras = extras;
        if (extras.get("type").equalsIgnoreCase("private")) {
            this.permission = "myessentials.warp." + warpName;
        }
    }

    public String getPermission() {
        return this.permission;
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
