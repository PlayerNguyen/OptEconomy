package com.github.playernguyen.databases;

import org.jetbrains.annotations.NotNull;

public interface OptEconomyTableField extends Comparable<OptEconomyTableField> {

    /**
     * Every field also having a name for itself. In table level, the field name is unique.
     *
     * @return the name of field
     */
    String getName();

    /**
     * The primitivity type of this field like varchar, integer,...
     *
     * @param <F> the generic field type class. Due to the variety of database.
     * @return the type of this field
     */
    <F extends OptEconomyTableFieldType> F getType();

    /**
     * The size of this field.
     *
     * @return size of this field
     */
    Integer getSize();

    /**
     * Not null value
     *
     * @return whether this field can be null
     */
    boolean canNull();

    /**
     * More arguments for field
     *
     * @return custom arguments for field
     */
    String args();

    /**
     * Compares name of object
     */
    @Override
    default int compareTo(@NotNull OptEconomyTableField o) {
        return this.getName().compareTo(o.getName());
    }
}
