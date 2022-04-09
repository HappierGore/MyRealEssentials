package db;

import static helper.TextUtils.parseColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 *
 * @author HappierGore
 */
public class WarpsJBDC {

    private static final String WARPS_PREFFIX = "[Warps]:";

    private static final String SQL_SELECT = "SELECT * from " + SQLite.WARPS_TABLE;
    private static final String SQL_INSERT = "INSERT INTO " + SQLite.WARPS_TABLE + "(name, location, extras) VALUES(?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE " + SQLite.WARPS_TABLE + " SET location = ?, extras = ? WHERE name = ?";
    private static final String SQL_DELETE = "DELETE FROM " + SQLite.WARPS_TABLE + " WHERE name = ?";

    public static Map<String, WarpsDAO> WARPS_REGISTERED = new HashMap<>();

    public static void load() {
        try ( Connection conn = SQLite.connect();  PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT); // set the corresponding param
                  ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String warpName = rs.getString("name");
                String worldName = "";
                double x = 0.0, y = 0.0, z = 0.0;
                float pitch = 0.0f, yaw = 0.0f;
                for (String entry : rs.getString("location").split(",")) {
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
                Map<String, String> extra = new HashMap<>();
                Location location = new Location(Bukkit.getWorld(worldName), x, y, z, pitch, yaw);
                String extras = rs.getString("extras");
                extras = extras.replaceAll("\\{|\\}", " ");
                if (!extras.equals("")) {
                    for (String entry : extras.split(",")) {
                        String key = entry.split("=")[0].trim();
                        String value = entry.split("=")[1].trim();
                        extra.put(key, value);
                    }
                }
                WarpsDAO warp = new WarpsDAO(warpName, location, extra);

                WARPS_REGISTERED.put(warp.getWarpName(), warp);
            }
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(parseColor(WARPS_PREFFIX + " Error from load :\n "));
            e.printStackTrace(System.out);
        }
    }

    public static boolean insert(WarpsDAO warp) {
        try ( Connection conn = SQLite.connect();  PreparedStatement db = conn.prepareStatement(SQL_INSERT)) {
            StringBuilder fxLocation = new StringBuilder();
            fxLocation.append("world=").append(warp.getLocation().getWorld().getName());
            fxLocation.append(", X=").append(warp.getLocation().getX());
            fxLocation.append(", Y=").append(warp.getLocation().getY());
            fxLocation.append(", Z=").append(warp.getLocation().getZ());
            fxLocation.append(", pitch=").append(warp.getLocation().getPitch());
            fxLocation.append(", yaw=").append(warp.getLocation().getYaw());

            db.setString(1, warp.getWarpName());
            db.setString(2, fxLocation.toString());
            db.setObject(3, warp.getExtras());

            db.executeUpdate();

            WARPS_REGISTERED.put(warp.getWarpName(), warp);

            db.close();
        } catch (SQLException e) {
            System.out.println(parseColor(WARPS_PREFFIX + " Error from add warp: \n"));
            e.printStackTrace(System.out);
            return false;
        }
        return true;
    }

    public static boolean delete(WarpsDAO warp) {
        try ( Connection conn = SQLite.connect();  PreparedStatement db = conn.prepareStatement(SQL_DELETE)) {
            db.setString(1, warp.getWarpName());

            db.executeUpdate();

            WARPS_REGISTERED.remove(warp.getWarpName());

            db.close();
        } catch (SQLException e) {
            System.out.println(parseColor(WARPS_PREFFIX + " Error from remove warp: \n"));
            e.printStackTrace(System.out);
            return false;
        }
        return true;
    }

    public static boolean update(WarpsDAO warp) {
        try ( Connection conn = SQLite.connect();  PreparedStatement db = conn.prepareStatement(SQL_UPDATE)) {
            StringBuilder fxLocation = new StringBuilder();
            fxLocation.append("world=").append(warp.getLocation().getWorld().getName());
            fxLocation.append(", X=").append(warp.getLocation().getX());
            fxLocation.append(", Y=").append(warp.getLocation().getY());
            fxLocation.append(", Z=").append(warp.getLocation().getZ());
            fxLocation.append(", pitch=").append(warp.getLocation().getPitch());
            fxLocation.append(", yaw=").append(warp.getLocation().getYaw());

            db.setString(1, fxLocation.toString());
            db.setObject(2, warp.getExtras());
            db.setString(3, warp.getWarpName());

            db.executeUpdate();

            WARPS_REGISTERED.put(warp.getWarpName(), warp);

            db.close();
        } catch (SQLException e) {
            System.out.println(parseColor(WARPS_PREFFIX + " Error from update warp: \n"));
            e.printStackTrace(System.out);
            return false;
        }
        return true;
    }
}
