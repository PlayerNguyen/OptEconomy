package com.github.playernguyen.players.storages;

import com.github.playernguyen.databases.OptEconomyDatabases;

/**
 * Inheritance from {@link OptEconomyPlayerStorageManager}.
 */
public class OptEconomyPlayerStorageManagerMySQL extends OptEconomyPlayerStorageManager {

    public OptEconomyPlayerStorageManagerMySQL(OptEconomyDatabases databases) {
        super(databases);
    }

}
