package com.github.playernguyen.storages;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The storage type of OptEconomy
 */
public enum OptEconomyStorageType {

    MYSQL("mysql"),
    SQLITE("sqlite");

    private final String name;

    /**
     * Constructor
     *
     * @param name the name of storage type
     */
    OptEconomyStorageType(String name) {
        this.name = name;
    }

    /**
     * @return the name of storage type
     */
    public String getName() {
        return name;
    }

    /**
     * Linear search for the storage type
     * @param s the string
     * @return the storage type object, null otherwise
     */
    @Nullable
    public static OptEconomyStorageType getTypeFromString(Object s) {
        for (OptEconomyStorageType value : OptEconomyStorageType.values()) {
            if (value.name.equalsIgnoreCase(s)) { return value; }
        }
        return null;
    }
}
