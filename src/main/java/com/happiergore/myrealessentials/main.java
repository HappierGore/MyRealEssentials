package com.happiergore.myrealessentials;

import Behaviours.PauseMoveInventory;
import commands.CommandManager;
import commands.Arguments.argsComplete;
import db.SQLite.SQLite;
import events.*;
import static helper.TextUtils.parseColor;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author HappierGore
 */
public class main extends JavaPlugin implements Listener {

    private String sversion;

    public static FileConfiguration configYML;

    public static LuckPerms luckPerms;

    @Override
    public void onEnable() {

        System.out.println(parseColor("\n&3------------------ §bMyRealEssentials - Logger §3------------------"));

        if (!SQLite.initialize() || !setupManager()) {
            System.out.println(parseColor("\n&cThere was an error when trying to load My Real Essentials, disabling it..."));
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        luckPerms = provider.getProvider();

        System.out.println(parseColor("&9------------------------------------------------------------------"));

        configYML = getConfig();

        //Crear config.yml en caso de que no exista
        saveDefaultConfig();

        //Comandos
        registerCommands();

        //Registrar eventos
        getServer().getPluginManager().registerEvents(this, this);
    }

    //***********************
    //        EVENTOS
    //***********************
    @EventHandler
    public void OnEntityDamage(EntityDamageEvent e) {
        OnPlayerDamage.OnPlayerDamage(e);
    }

    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent e) {
        PlayerMove.PlayerMoveEvent(e);
    }

    @EventHandler
    public void OnClickInventory(InventoryClickEvent e) {
        OnClickInventory.OnClickInventory(e);
    }

    @EventHandler
    public void OnCloseInventory(InventoryCloseEvent e) {
        OnCloseInventory.OnCloseInventory(e);
    }

    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent e) {
        PauseMoveInventory.OnPlaceBlock(e);
    }

    @EventHandler
    public void OnItemDrop(PlayerDropItemEvent e) {
        PauseMoveInventory.OnDropItem(e);
    }

    @EventHandler
    public void OnPlayerLeaves(PlayerQuitEvent e) {
        OnPlayerQuit.OnPlayerQuit(e);
    }

    @EventHandler
    public void OnPlayerJoins(PlayerJoinEvent e) {
        OnPlayerJoins.OnPlayerJoins(e);
    }

    //***********************
    //        Helper
    //***********************
    private void registerCommands() {
        CommandManager.init();
        argsComplete args = new argsComplete();
        CommandManager.getRegisteredCommands().forEach(cmd -> {
            this.getCommand(cmd.getCmdName()).setExecutor(new CommandManager());
            if (cmd.getCmdType().getArgsSize() > 0) {
                this.getCommand(cmd.getCmdName()).setTabCompleter(args);
            }
        });
    }

    private void successMessage(String version) {

        StringBuilder enabledMsg = new StringBuilder();
        enabledMsg.append("\n&bMy real essentials has been loaded sucessfuly!\n\n");
        enabledMsg.append("Client version: ").append(sversion).append(" \nPlugin version selected: ").append(version);
        enabledMsg.append("\n\n&6Autor: HappierGore\n");
        enabledMsg.append("&9Discord: &nHappierGore#1197\n");
        enabledMsg.append("&3Spigot: https://www.spigotmc.org/resources/authors/happiergore.1046101/\n");
        enabledMsg.append("&eSupport: https://discord.gg/ZKy5eVPxW5\n");
        enabledMsg.append("&9Please, leave me a rating! <3\n\n");

        System.out.println(parseColor(enabledMsg.toString()));
    }

    private boolean setupManager() {
        sversion = "N/A";
        try {
            sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("WARNING: MyRealEssentials wasn't able to get your client version.\nWill start with default version...");
            sversion = "v1_18";
            return false;
        }

        if (sversion.contains("v1_5")) {
            successMessage("v1.5");
            return true;
        } else if (sversion.contains("v1_6")) {
            successMessage("v1.6");
            return true;
        } else if (sversion.contains("v1_7")) {
            successMessage("v1.7");
            return true;
        } else if (sversion.contains("v1_8")) {
            successMessage("v1.8");
            return true;
        } else if (sversion.contains("v1_9")) {
            successMessage("v1.9");
            return true;
        } else if (sversion.contains("v1_10")) {
            successMessage("v1.10");
            return true;
        } else if (sversion.contains("v1_11")) {
            successMessage("v1.11");
            return true;
        } else if (sversion.contains("v1_12")) {
            successMessage("v1.12");
            return true;
        } else if (sversion.contains("v1_13")) {
            successMessage("v1.13");
            return true;
        } else if (sversion.contains("v1_14")) {
            successMessage("v1.14");
            return true;
        } else if (sversion.contains("v1_15")) {
            successMessage("v1.15");
            return true;
        } else if (sversion.contains("v1_16")) {
            successMessage("v1.16");
            return true;
        } else if (sversion.contains("v1_17")) {
            successMessage("v1.17");
            return true;
        } else if (sversion.contains("v1_18")) {
            successMessage("v1.18");
            return true;
        }
        System.out.println("The version " + sversion + " is not suported.");
        return false;
    }
}
