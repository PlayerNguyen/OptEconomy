package com.github.playernguyen.databases;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;

import java.util.Set;
import java.util.TreeSet;

public abstract class OptEconomyDatabaseAbstract implements OptEconomyDatabase {
    private final OptEconomy plugin;
    private final OptEconomySQLEstablish establish;
    private final Set<OptEconomyTable<?>> tableSet;

    public OptEconomyDatabaseAbstract(OptEconomy plugin, OptEconomySQLEstablish establish) {
        this.plugin = plugin;
        this.establish = establish;
        this.tableSet = new TreeSet<>();
    }

    @Override
    public OptEconomySQLEstablish getEstablish() {
        return establish;
    }

    @Override
    public Set<OptEconomyTable<?>> getTableSet() {
        return tableSet;
    }

    /**
     * The plugin instance to get utility
     *
     * @return the plugin instance
     */
    public OptEconomy getPlugin() {
        return plugin;
    }
}
