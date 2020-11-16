package com.github.playernguyen.establishs;

import com.github.playernguyen.OptEconomy;

/**
 * The inheritance of {@link OptEconomySQLEstablish} class
 *
 */
public class OptEconomySQLEstablishSQLite implements OptEconomySQLEstablish {

    private final OptEconomy instance;

    public OptEconomySQLEstablishSQLite(OptEconomy instance) {
        this.instance = instance;
    }
}
