package com.universalbay.classes.event;

import java.util.List;

import com.universalbay.classes.CharacterClass;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.entity.HumanEntity;

/**
 * Called when a {@link HumanEntity} picks a new class using the selection dialog.
 */
public class ClassDialogOpenEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final HumanEntity entity;
    private final List<CharacterClass> availableClasses;
    private boolean cancelled;

    /**
     * Constructs a {@code ClassDialogOpenEvent}.
     *
     * @param   entity              the {@link HumanEntity} who opened the dialog
     * @param   availableClasses    the {@link List} of {@link CharacterClass CharacterClasses} to display in the dialog
     */
    public ClassDialogOpenEvent(final HumanEntity entity, final List<CharacterClass> availableClasses) {
        this.entity = entity;
        this.availableClasses = availableClasses;
    }

    public final List<CharacterClass> getAvailableClasses() {
        return this.availableClasses;
    }

    public final HumanEntity getWho() {
        return this.entity;
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
