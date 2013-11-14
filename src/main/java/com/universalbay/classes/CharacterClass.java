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

    /**
     * Gets the starter/default inventory contents associated with this {@code CharacterClass}, in the
     * form of an {@link ItemStack} array. A {@link HumanEntity}'s inventory will be filled with the
     * contents of this array on picking this specific {@code CharacterClass}.
     *
     * @return  the default inventory contents of this {@code CharacterClass}
     */
    ItemStack[] getInventoryContents();
}
