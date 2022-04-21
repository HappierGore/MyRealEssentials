/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happiergore.myrealessentials.events;

import com.happiergore.myrealessentials.Behaviours.PauseMoveInventory;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappierGore
 */
public class OnCloseInventory {

    public void OnCloseInventory(InventoryCloseEvent e) {
        new PauseMoveInventory().OnCloseInventory(e);
    }
}
