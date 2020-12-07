package com.github.playernguyen.runnables;

import com.github.playernguyen.OptEconomy;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This is an abstract class to improve the runnable
 */
public abstract class OptEconomyRunnable extends BukkitRunnable {

    private final OptEconomy instance;

    public OptEconomyRunnable(OptEconomy instance) {
        this.instance = instance;
    }

    /**
     * The instance of OptEconomy
     *
     * @return instance of OptEconomy
     */
    public OptEconomy getInstance() {
        return instance;
    }
}
