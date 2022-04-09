package db;

import com.happiergore.myrealessentials.main;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HappierGore
 */
public class SQLite {

    public static String path;
    public static final String WARPS_TABLE = "warps";
    public static final String DBNAME = "MRE.db";

    public static boolean initialize() {
        try {
            try {
                Class.forName("org.sqlite.JDBC").newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace(System.out);
                return false;
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
            return false;
        }

        File dataFolder = main.getPlugin(main.class).getDataFolder();

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
            //Generar base de datos en caso de que no exista
        }

        path = "jdbc:sqlite:" + dataFolder.getAbsolutePath().replace('\\', '/') + "/" + DBNAME;

        WarpsJBDC.load();

        generateDB();

        return true;
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println("Error from connect: " + e.getMessage());
        }
        return conn;
    }

    private static void generateDB() {

        // create warps table
        String sql = "CREATE TABLE IF NOT EXISTS " + WARPS_TABLE + "(\"name\" TEXT NOT NULL UNIQUE, \"location\" TEXT, \"extras\" TEXT, PRIMARY KEY(\"name\"))";

        try ( Connection conn = connect();  Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            stmt.close();

        } catch (SQLException e) {
            System.out.println("[DataBase] Error while creating database" + e);
        }
    }

}
