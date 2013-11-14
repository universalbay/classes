package com.universalbay.classes;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.ConfigurationSection;

public final class ConfiguredCharacterClass implements CharacterClass {
    private ConfigurationSection configurationSection;

    protected ConfiguredCharacterClass(final ConfigurationSection configurationSection) {
        this.configurationSection = configurationSection;
    }

    public String getName() {
        return this.configurationSection.getName();
    }

    public ItemStack getIcon() {
        return this.configurationSection.getItemStack("itemIcon");
    }

    public ItemStack[] getInventoryContents() {
        List<ItemStack> items = (List<ItemStack>) configurationSection.getList("items"); // TODO: Casting might be unsafe. For now, consider it safe to fail.
        return items.toArray(new ItemStack[items.size()]);
    }
}
