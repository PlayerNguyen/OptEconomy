package com.github.playernguyen.apis;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.exceptions.PluginNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * This class will provide API system to OptEconomy to depend on plugin
 */
public abstract class OptEconomyPluginProvider {

    private final String pluginName;
    private boolean enabled = false;

    /**
     * A clearly constructor
     *
     * @param pluginName an API plugin name
     * @param isDepend   OptEconomy depend on it or not
     */
    public OptEconomyPluginProvider(OptEconomy instance,
                                    String pluginName,
                                    boolean isDepend
    ) throws PluginNotFoundException {
        this.pluginName = pluginName;

        // Initial
        if (this.getPlugin() != null) {
            this.enabled = true;
            if (isDepend) {
                throw new PluginNotFoundException(String.format("Plugin not found %s", this.pluginName));
            }
            // Info to server
            instance.getOptLogger().info("Detecting plugin " + this.pluginName + " as an API");
            // Call onEnable
            this.onEnable(instance);
        }

    }

    /**
     * A short constructor with is depend be as false (soft depend mode)
     *
     * @param pluginName an API plugin name
     */
    public OptEconomyPluginProvider(OptEconomy instance, String pluginName) throws PluginNotFoundException {
        this(instance, pluginName, false);
    }

    /**
     * Request a plugin. Unless OptEconomy found it, throw an exception
     *
     * @throws PluginNotFoundException unless OptEconomy found the plugin
     */
    private void requestPlugin() throws PluginNotFoundException {
        // Whether OptEconomy not found the plugin
        if (Bukkit.getPluginManager().getPlugin(this.pluginName) == null) {
            throw new PluginNotFoundException("Plugin not found");
        }
    }

    /**
     * Get the current plugin with their name
     *
     * @return the current plugin with the name
     */
    public Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin(this.pluginName);
    }

    /**
     * Call this when OptEconomy when enabling
     *
     * @param instance the OptEconomy plugin instance
     */
    public void onEnable(OptEconomy instance) {
    }

    /**
     * @return Whether is enable, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }
}
