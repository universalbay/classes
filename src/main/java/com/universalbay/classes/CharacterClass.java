package com.universalbay.classes;

import org.bukkit.inventory.ItemStack;

/**
 * Represents a type of Class that a {@link HumanEntity} can be assigned.
 */
public interface CharacterClass {
    String getName();

    ItemStack getIcon();

    ItemStack[] getInventoryContents();
}
