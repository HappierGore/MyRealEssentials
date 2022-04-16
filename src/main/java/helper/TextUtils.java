package helper;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 *
 * @author HappierGore
 */
public final class TextUtils {

    public static String parseColor(String text) {
        return text.replace("&", "§");
    }

    public static String errorMsg(String msg, boolean includeLoggerHeader) {
        StringBuilder str = new StringBuilder();
        if (includeLoggerHeader) {
            str.append("\n&3------------------ §bMyRealEssentials - Logger §3------------------");
        }
        str.append("\n&4ERROR:\n");
        str.append(msg);
        if (includeLoggerHeader) {
            str.append("\n&9------------------------------------------------------------------");
        }
        return str.toString();
    }

    public static Map<String, String> formatLocation(Location location) {
        Map result = new HashMap<String, String>();
        result.put("world", location.getWorld().getName());
        result.put("X", location.getX());
        result.put("Y", location.getY());
        result.put("Z", location.getZ());
        result.put("pitch", location.getPitch());
        result.put("yaw", location.getYaw());
        return result;
    }

    public static Location getLocation(Map<String, String> locationMap) {
        String worldName = locationMap.get("world");
        double x = Double.parseDouble(locationMap.get("X")),
                y = Double.parseDouble(locationMap.get("Y")),
                z = Double.parseDouble(locationMap.get("Z"));
        float pitch = Float.parseFloat(locationMap.get("pitch")),
                yaw = Float.parseFloat(locationMap.get("yaw"));

        return new Location(Bukkit.getWorld(worldName), x, y, z, pitch, yaw);
    }

    public static Location getLocation(String locationString) {
        String worldName = "";
        double x = 0.0, y = 0.0, z = 0.0;
        float pitch = 0.0f, yaw = 0.0f;

        for (String entry : locationString.split(",")) {
            String key = entry.split("=")[0];
            String value = entry.split("=")[1];
            switch (key.toLowerCase().trim()) {
                case "world" -> {
                    worldName = value;
                }
                case "x" -> {
                    x = Double.parseDouble(value);
                }
                case "y" -> {
                    y = Double.parseDouble(value);
                }
                case "z" -> {
                    z = Double.parseDouble(value);
                }
                case "pitch" -> {
                    pitch = Float.parseFloat(value);
                }
                case "yaw" -> {
                    yaw = Float.parseFloat(value);
                }

            }
        }
        return new Location(Bukkit.getWorld(worldName), x, y, z, pitch, yaw);
    }

}
