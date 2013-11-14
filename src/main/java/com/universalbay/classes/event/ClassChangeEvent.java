package com.universalbay.classes.event;

import com.universalbay.classes.CharacterClass;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.entity.HumanEntity;

/**
 * Called when the {@link CharacterClass} of a {@link HumanEntity} changes. The newly assigned can be null, and will be
 * in case the {@code HumanEntity} has passed.
 */
public class ClassChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final HumanEntity entity;
    private CharacterClass newClass;
    private boolean cancelled;

    public ClassChangeEvent(final HumanEntity entity, final CharacterClass newClass) {
        this.entity = entity;
        this.newClass = newClass;
    }

    /**
     * Returns the {@link HumanEntity} whose {@link CharacterClass} was subject to change.
     *
     * @return  the {@code HumanEntity} whose {@code CharacterClass} was subject to change
     */
    public final HumanEntity getWho() {
        return this.entity;
    }

    /**
     * Returns the newly assigned {@link CharacterClass}. If you're looking for the {@code CharacterClass} currently
     * assigned to the {@link HumanEntity}, use {@link ClassManager#getAssignedClass(HumanEntity)}.
     *
     * @return  the {@code HumanEntity} whose {@code CharacterClass} was subject to change
     */
    public final CharacterClass getNewClass() {
        return this.newClass;
    }

    /**
     * Sets the {@link CharacterClass} to assign to the {@link HumanEntity}.
     *
     * @param   newClass    the new {@code CharacterClass} to assign the {@code HumanEntity}
     */
    public final void setNewClass(final CharacterClass newClass) {
        this.newClass = newClass;
    }

    @Override
    public final boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public final void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public final HandlerList getHandlers() {
        return this.handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
