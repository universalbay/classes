package com.universalbay.classes;

import org.bukkit.inventory.ItemStack;

public interface CharacterClass {
    String getName();

    ItemStack getIcon();

    ItemStack[] getInventoryContents();
}
