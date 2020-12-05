package com.github.playernguyen.listeners;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.manager.OptEconomyManagerList;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

/**
 * The listener manager class to manage listener
 */
public class OptEconomyListenerManager extends OptEconomyManagerList<OptEconomyAbstractListener> {

    private final OptEconomy instance;

    public OptEconomyListenerManager(OptEconomy instance) {
        this.instance = instance;
    }

    /**
     * Register new listener item
     *
     * @param item the listener to register
     */
    public void register(OptEconomyAbstractListener item) {
        // Add new
        this.collection().add(item);
        // Register
        Bukkit.getPluginManager().registerEvents(item, instance);
    }

    /**
     * Un-register item
     * @param item the listener item to unregister
     */
    public void unregister(OptEconomyAbstractListener item) {
        // Unregister
        HandlerList.unregisterAll(item);
        // Remove
        this.collection().remove(item);

    }

}
