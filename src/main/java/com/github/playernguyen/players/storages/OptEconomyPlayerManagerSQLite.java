package com.github.playernguyen.players.storages;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.databases.OptEconomyDatabase;

/**
 * Inheritance from {@link OptEconomyPlayerManagerAbstract}
 */
public class OptEconomyPlayerManagerSQLite extends OptEconomyPlayerManagerAbstract {

    public OptEconomyPlayerManagerSQLite(OptEconomy ins, OptEconomyDatabase databases) {
        super(ins, databases);
    }


}
