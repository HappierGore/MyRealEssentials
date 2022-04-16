/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import Behaviours.PauseMoveInventory;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappierGore
 */
public class OnCloseInventory {

    public static void OnCloseInventory(InventoryCloseEvent e) {
        PauseMoveInventory.OnCloseInventory(e);
    }
}
