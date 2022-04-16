package db;

import helper.TextUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class YamlJBDC {

    private final File file;
    private final YamlConfiguration config;

    public YamlJBDC(File path, String fileName) {
        this.file = new File(path, fileName);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public YamlJBDC(String path, String fileName) {
        this.file = new File(path, fileName);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public boolean SaveFile() {
        try {
            this.config.save(file);
            return true;
        } catch (IOException ex) {
            System.out.println(TextUtils.errorMsg("&cThere was an error while trying to save " + file.getName(), false));
        }
        return false;
    }

    public void RemoveKey(String key) {
        this.config.set(key, null);
        this.SaveFile();
    }

    public Map<String, ItemStack> GetItemList(String commonPath) {
        Map<String, ItemStack> items = new HashMap<>();
        for (String key : this.config.getKeys(true)) {
            if (key.contains(commonPath)) {
                items.put(key.replace(commonPath, ""), config.getItemStack(key));
            }
        }
        return items;
    }

    public YamlConfiguration getConfig() {
        return this.config;
    }

}
