package com.universalbay.classes;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

import com.universalbay.classes.event.ClassDialogOpenEvent;

import org.bukkit.ChatColor;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.ConfigurationSection;

public class Classes extends JavaPlugin implements Listener {
    private ClassManager classManager;
    private Map<HumanEntity, ItemStack[]> availableClassesCache;

    @Override
    public final void onEnable() {
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        this.getServer().getPluginManager().registerEvents(this, this); // TODO: Outsource eventlistener

        // Register the ClassManager as a service for other plugins to use and abuse
        this.classManager = new ClassManager(this.getServer().getMaxPlayers());
        this.getServer().getServicesManager().register(ClassManager.class, this.classManager, this, ServicePriority.Normal);

        // Load the CharacterClasses from the config file
        ConfigurationSection classesSection = this.getConfig().getConfigurationSection("classes"); // Get the classes section from the config
        Set<String> classKeys = classesSection.getKeys(false); // Get all the child keys of the classes section non-recursively

        // Add the classes specified in the config file to the classmanager
        for (String classKey : classKeys) {
            this.classManager.getAvailableClasses().add(new ConfiguredCharacterClass(classesSection.getConfigurationSection(classKey)));
        }

        // Initialise the availableClassesCache
        this.availableClassesCache = new HashMap();
    }

    @EventHandler
    public final void onInventoryClick(final InventoryClickEvent event) {
        if (!availableClassesCache.containsKey(event.getWhoClicked())) {
            return;
        }

        /* Perform a deep equality comparison to verify that the inventory contents are equal. Assuming it is not
           possible to fabricate an inventory identical to the one generated by this plugin THIS OPERATION IS SAFE.
           However it is not wise to rely on this fact alone as many things can go wrong.
           TODO: Make this not be so terrible */
        if (!Arrays.deepEquals(event.getView().getTopInventory().getContents(), availableClassesCache.get(event.getWhoClicked()))) {
            return;
        }

        event.setResult(Result.DENY); // Deny the transaction as the inventory should remain intact

        /* Check if the user picked a class or moved other items around. If getCurrentItem() is an ItemStack equal to a
           CharacterClasses icon it SHOULD be safe to assume the user clicked it. */
        if (!Arrays.asList(availableClassesCache.get(event.getWhoClicked())).contains(event.getCurrentItem())) {
            return;
        }

        CharacterClass pickedClass = null;
        for (CharacterClass availableClass : this.classManager.getAvailableClasses()) {
            if (availableClass.getIcon().equals(event.getCurrentItem())) {
                pickedClass = availableClass;
            }
        }

        this.classManager.assignClass(event.getWhoClicked(), pickedClass);

        event.getWhoClicked().closeInventory();
        event.getWhoClicked().getInventory().setContents(this.classManager.getAssignedClass(event.getWhoClicked()).getInventoryContents());
        if (event.getWhoClicked() instanceof CommandSender) {
            ((CommandSender) event.getWhoClicked()).sendMessage(ChatColor.GREEN + " ... you picked the "
                                                                + ChatColor.GOLD + pickedClass.getName()
                                                                + ChatColor.GREEN + " class!");
        }
    }

    @EventHandler
    public final void onEntityDeath(final EntityDeathEvent event) {
        if (!(event.getEntity() instanceof HumanEntity)) {
            return;
        }
        this.classManager.unassignClass((HumanEntity) event.getEntity());
    }

    @EventHandler
    public final void onPlayerRespawn(final PlayerRespawnEvent event) {
        event.getPlayer().getInventory().addItem(this.getConfig().getItemStack("classSelection.selectionWand"));
    }

    @EventHandler
    public final void onPlayerInteract(final PlayerInteractEvent event) {
        if (!event.getPlayer().getInventory().getItemInHand().equals(this.getConfig().getItemStack("classSelection.selectionWand"))) { // Ensure the wand is being used
            return;
        } else if (event.getAction() != Action.RIGHT_CLICK_AIR) { // Ensure that the wand is swung in the air
            return;
        }

        List<CharacterClass> availableClasses = new ArrayList(this.classManager.getAvailableClasses());
        ClassDialogOpenEvent openEvent = new ClassDialogOpenEvent(event.getPlayer(), availableClasses);
        this.getServer().getPluginManager().callEvent(openEvent);

        if (openEvent.isCancelled()) {
            return;
        }

        List<ItemStack> itemIcons = new ArrayList(openEvent.getAvailableClasses().size());
        for (CharacterClass characterClass : openEvent.getAvailableClasses()) { // Transfer the icon values from the CharacterClasses
            itemIcons.add(characterClass.getIcon());
        }

        /* Create a new inventory and put its contents in the availableClassesCache. This way, if the classes
           available change AFTER the inventory has been opened it is still detectable as a class selection dialog.
           Also makes it possible for other plugins to modify one players available classes only. */
        Inventory inventory = this.getServer().createInventory(null, itemIcons.size() - itemIcons.size() % 9 + 9, "Select a Class");
        inventory.setContents(itemIcons.toArray(new ItemStack[itemIcons.size()]));
        this.availableClassesCache.put(event.getPlayer(), inventory.getContents());

        event.getPlayer().openInventory(inventory);
        event.getPlayer().sendMessage(ChatColor.GREEN + "Please select a class...");
    }
}
