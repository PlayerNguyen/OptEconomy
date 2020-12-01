package com.github.playernguyen.listeners;

import com.github.playernguyen.OptEconomy;
import org.bukkit.event.Listener;

/**
 * The abstract class of Listener to listen to data
 */
public abstract class OptEconomyAbstractListener implements Listener {

    private final OptEconomy instance;

    public OptEconomyAbstractListener(OptEconomy instance) {
        this.instance = instance;
    }

    /**
     * The instance of OptEconomy
     * @return instance of OptEconomy
     */
    public OptEconomy getInstance() {
        return instance;
    }


}
