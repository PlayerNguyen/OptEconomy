package com.github.playernguyen.apis;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.manager.OptEconomyManagerAbstractMap;

/**
 * The manager class to contain all provider inside
 */
public class OptEconomyAPIManager extends OptEconomyManagerAbstractMap<String, OptEconomyPluginProvider> {

    private final OptEconomy instance;

    public OptEconomyAPIManager(OptEconomy instance) {
        this.instance = instance;
    }

    /**
     * Register new API plugin
     *
     * @param name     the name of plugin
     * @param provider {@link OptEconomyPluginProvider} class to add
     */
    public void register(String name, OptEconomyPluginProvider provider) {
        instance.getDebugger().info(String.format("Adding %s (%s)",
                provider.toString(),
                provider.getClass().getSimpleName()));
        this.map().put(name, provider);
    }

    /**
     * Clear the OptEconomy
     */
    public void clear() {
        this.map().clear();
    }

    /**
     * Check that plugin is enabled or not
     *
     * @param name the name of plugin
     * @return whether is enable return true, false otherwise
     */
    public boolean isEnabled(String name) {
        OptEconomyPluginProvider provider = this.map().get(name);
        return provider != null && provider.isEnabled();
    }

}
