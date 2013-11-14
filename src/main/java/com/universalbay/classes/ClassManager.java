package com.universalbay.classes;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.universalbay.classes.event.ClassChangeEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

/**
 * Keeps track of {@link CharacterClass CharacterClasses} associated with {@link HumanEntity HumanEntities}.
 */
public class ClassManager { // TODO: Persistent storage of classes
    private List<CharacterClass> availableClasses;
    private Map<HumanEntity, CharacterClass> assignedClasses;

    /**
     * Constructs a {@code ClassManager}.
     * The initial capacity of the {@link #availableClasses} {@link List} is 16 and {@link Bukkit#getMaxPlayers()} for
     * the {@link #assignedClasses} {@link Map}.
     */
    public ClassManager() {
        this(16, Bukkit.getMaxPlayers());
    }

    /**
     * Constructs a {@code ClassManager} with an initial capacity for the {@link #assignedClasses} {@link Map}.
     * The initial capacity of the {@link #availableClasses} {@link List} is 16.
     *
     * @param   initialCharacterCapacity    the initial capacity of the {@code assignedClasses} {@code Map}
     */
    public ClassManager(final int initialCharacterCapacity) {
        this(16, initialCharacterCapacity);
    }

    /**
     * Constructs a {@code ClassManager} with an initial capacity for the {@link #availableClasses} {@link List} and
     * the {@link #assignedClasses} {@link Map}.
     *
     * @param   initialClassesCapacity      the initial capacity of the {@code availableClasses} {@code List}
     * @param   initialCharacterCapacity    the initial capacity of the {@code assignedClasses} {@code Map}
     */
    public ClassManager(final int initialClassesCapacity, final int initialCharacterCapacity) {
        this.availableClasses = new ArrayList();
        this.assignedClasses = new HashMap(initialCharacterCapacity);
    }

    /**
     * Gets a {@link List} of all currently available {@link CharacterClass CharacterClasses}.
     *
     * @return  a {@code List} of all available {@code CharacterClasses}
     */
    public final List<CharacterClass> getAvailableClasses() {
        return availableClasses;
    }

    /**
     * Gets a {@link Map} of all the assigned {@link CharacterClass CharacterClasses} and who they're assigned to.
     *
     * @return  a {@code Map} mapping {@link HumanEntity HumanEntities} to a specific {@link CharacterClass}
     */
    public final Map<HumanEntity, CharacterClass> getAssignedClasses() {
        return new HashMap(this.assignedClasses);
    }

    /**
     * Gets the {@link CharacterClass} assigned to a {@link HumanEntity}.
     *
     * @param   entity  the {@code HumanEntity} to get the assigned {@code CharacterClass} from
     * @return          the assigned {@code CharacterClass}
     */
    public final CharacterClass getAssignedClass(final HumanEntity entity) {
        return this.assignedClasses.get(entity);
    }

    /**
     * Assigns a {@link CharacterClass} to the specified {@link HumanEntity} and calls the appropriate events. A
     * {@link ClassChangeEvent} is called, and if cancelled, it'll simply return and do nothing. There is no way to
     * know if the event was cancelled or not other than listening with the lowest possible priority, however this
     * shouldn't be necessary as it can be verified with {@link #getAssignedClass(HumanEntity)}.
     *
     * @param   entity          the {@code HumanEntity} this {@code CharacterClass} is assigned to
     * @param   characterClass  the {@code CharacterClass} to assign
     */
    public final void assignClass(final HumanEntity entity, final CharacterClass characterClass) {
        ClassChangeEvent event = new ClassChangeEvent(entity, characterClass);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) { // If event is cancelled, do nothing
            return;
        }

        this.assignedClasses.put(event.getWho(), event.getNewClass()); // Put the updated values in the store
    }

    /**
     * Unassigns a {@link CharacterClass} from the specified {@link HumanEntity} and calls the appropriate events. A
     * {@link ClassChangeEvent} is called, and if cancelled, it'll simply return and do nothing. There is no way to
     * know if the event was cancelled or not other than listening with the lowest possible priority, however this
     * shouldn't be necessary as it can be verified with {@link #getAssignedClass(HumanEntity)}.
     *
     * @param   entity  the {@code HumanEntity} from which the {@code CharacterClass} should be unassigned
     */
    public final void unassignClass(final HumanEntity entity) {
        ClassChangeEvent event = new ClassChangeEvent(entity, null);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) { // If event is cancelled, do nothing
            return;
        }

        this.assignedClasses.remove(entity);
    }
}
