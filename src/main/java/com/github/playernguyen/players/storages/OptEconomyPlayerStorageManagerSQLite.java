package com.github.playernguyen.players.storages;

import com.github.playernguyen.databases.OptEconomyDatabases;

/**
 * Inheritance from {@link OptEconomyPlayerStorageManager}
 */
public class OptEconomyPlayerStorageManagerSQLite extends OptEconomyPlayerStorageManager {

    public OptEconomyPlayerStorageManagerSQLite(OptEconomyDatabases databases) {
        super(databases);
    }


}
