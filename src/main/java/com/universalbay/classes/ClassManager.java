package com.universalbay.classes;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.universalbay.classes.event.ClassChangeEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

public class ClassManager { // TODO: Persistent storage of classes
    private List<CharacterClass> availableClasses;
    private Map<HumanEntity, CharacterClass> assignedClasses;

    public ClassManager() {
        this(16, Bukkit.getMaxPlayers());
    }

    public ClassManager(final int initialCharacterCapacity) {
        this(16, initialCharacterCapacity);
    }

    public ClassManager(final int initialClassesCapacity, final int initialCharacterCapacity) {
        this.availableClasses = new ArrayList();
        this.assignedClasses = new HashMap(initialCharacterCapacity);
    }

    public final List<CharacterClass> getAvailableClasses() {
        return availableClasses;
    }

    public final Map<HumanEntity, CharacterClass> getAssignedClasses() {
        return new HashMap(this.assignedClasses);
    }

    public final CharacterClass getAssignedClass(final HumanEntity entity) {
        return this.assignedClasses.get(entity);
    }

    public final void assignClass(final HumanEntity entity, final CharacterClass characterClass) {
        ClassChangeEvent event = new ClassChangeEvent(entity, characterClass);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) { // If event is cancelled, do nothing
            return;
        }

        this.assignedClasses.put(event.getWho(), event.getNewClass()); // Put the updated values in the store
    }

    public final void unassignClass(final HumanEntity entity) {
        ClassChangeEvent event = new ClassChangeEvent(entity, null);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) { // If event is cancelled, do nothing
            return;
        }

        this.assignedClasses.remove(entity);
    }
}
