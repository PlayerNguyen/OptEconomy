package com.github.playernguyen.players.storages;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.databases.OptEconomyDatabase;

/**
 * Inheritance from {@link OptEconomyPlayerStorageManager}.
 */
public class OptEconomyPlayerStorageManagerMySQL extends OptEconomyPlayerStorageManager {

    public OptEconomyPlayerStorageManagerMySQL(OptEconomy ins,
                                               OptEconomyDatabase databases) {
        super(ins, databases);
    }

}
