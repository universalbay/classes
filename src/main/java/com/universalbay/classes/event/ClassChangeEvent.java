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

    public final HumanEntity getWho() {
        return this.entity;
    }

    public final CharacterClass getNewClass() {
        return this.newClass;
    }

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
