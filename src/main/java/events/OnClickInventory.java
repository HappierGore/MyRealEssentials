/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import Behaviours.PauseMoveInventory;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 *
 * @author HappierGore
 */
public class OnClickInventory {

    public static void OnClickInventory(InventoryClickEvent e) {
        PauseMoveInventory.OnClickInventory(e);
    }
}
