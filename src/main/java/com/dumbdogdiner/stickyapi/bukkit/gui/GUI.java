/**
 * Copyright (c) 2020 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickyapi.bukkit.gui;

import java.util.HashMap;
import java.util.Map;

import com.dumbdogdiner.stickyapi.common.arguments.Arguments;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import lombok.Getter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

/**
 * Create and modify inventory GUIs
 */
public abstract class GUI implements Listener {

    @Getter
    private Inventory inventory;
    private int s;

    @Getter
    private Plugin plugin;
    private Map<Integer, ClickableSlot> clickableMap;

    /**
     * Create a new inventory GUI
     * 
     * @param pSize   The inventory size
     * @param pPlugin The plugin to register with
     */
    protected GUI(int pSize, Plugin pPlugin) {
        this.s = pSize;
        this.plugin = pPlugin;

        this.clickableMap = new HashMap<Integer, ClickableSlot>();

    }

    /**
     * Register a new inventory slot
     * 
     * @param pItem The item to put in the slot
     * @param pSlot The location of the slot
     */
    protected void registerSlot(ItemStack pItem, int pSlot) {
        if (pItem == null)
            return;
        inventory.setItem(pSlot, pItem);
    }

    /**
     * Register a slot as a clickable slot
     * 
     * @param cs The slot to register as clickable
     */
    protected void registerClickable(ClickableSlot cs) {
        if (cs.getItem() == null || inventory.getItem(cs.getSlot()) != null)
            return;
        clickableMap.put(cs.getSlot(), cs);
        inventory.setItem(cs.getSlot(), cs.getItem());
    }

    /**
     * Get a clickable slot
     * 
     * @param slot The slot to get
     * @return The ClickableSlot
     */
    protected ClickableSlot getClickable(int slot) {
        return clickableMap.get(slot);
    }

    /**
     * Build an inventory GUI with command arugments
     * 
     * @param player  The player to send the GUI to
     * @param rawArgs The raw arguments of the command they have executed
     * @param args    The parsed arguments of the command
     */
    protected abstract void buildGUI(Player player, String[] rawArgs, Arguments args);

    /**
     * Build an inventory GUI
     * 
     * @param player The player to send the GUI to
     */
    protected abstract void buildGUI(Player player);

    /**
     * Check whether or not a slot is valid
     * <p>
     * Returns true if the slot is a valid slot
     * 
     * @param pSlot The slot to check
     * @return {@link Boolean}
     */
    protected boolean isValidSlot(int pSlot) {
        return inventory.getItem(pSlot) != null;
    }

    /**
     * Check whether or not a slot is clickable
     * 
     * @param pSlot The slot to check Returns true if the slot is a clickable slot
     * @return {@link Boolean}
     */
    protected boolean isClickable(int pSlot) {
        return clickableMap.containsKey(pSlot);
    }

    @EventHandler
    protected abstract void onSlotClick(InventoryClickEvent e);

    /**
     * Create the new inventory
     * 
     * @param pTitle The title of the inventory Returns the inventory you've created
     * @return {@link Inventory}
     */
    protected Inventory makeInventory(String pTitle) {
        return this.inventory = Bukkit.createInventory(null, s, pTitle);
    }
}
