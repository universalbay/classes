package com.universalbay.classes;

import java.util.Map;
import java.util.List;

import org.bukkit.entity.HumanEntity;

public interface ClassManager {
    public List<CharacterClass> getAvailableClasses();

    public Map<HumanEntity, CharacterClass> getAssignedClasses();

    public CharacterClass getAssignedClass(HumanEntity entity);

    public void assignClass(HumanEntity entity, CharacterClass characterClass);

    public void unassignClass(HumanEntity entity);
}
