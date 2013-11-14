package com.universalbay.classes;

import org.bukkit.inventory.ItemStack;

/**
 * Represents a type of Class that a {@link HumanEntity} can be assigned.
 */
public interface CharacterClass {
    /**
     * Gets the name of this {@code CharacterClass}.
     *
     * @return  a String containing the name of this {@code CharacterClass}
     */
    String getName();

    /**
     * Gets the icon of this {@code CharacterClass}.
     *
     * @return  the icon of this {@code CharacterClass} in the form of an {@link ItemStack}
     */
    ItemStack getIcon();

    ItemStack[] getInventoryContents();
}
