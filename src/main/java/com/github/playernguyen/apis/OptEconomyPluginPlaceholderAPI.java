package com.github.playernguyen.apis;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.exceptions.PluginNotFoundException;

/**
 * A PlaceholderAPI provider.
 *
 * @see OptEconomyPluginProvider
 */
public class OptEconomyPluginPlaceholderAPI extends OptEconomyPluginProvider {

    public OptEconomyPluginPlaceholderAPI(OptEconomy instance) throws PluginNotFoundException {
        super(instance, OptEconomyConstants.PLUGIN_NAME_PLACEHOLDER_API);
    }

    @Override
    public void onEnable(OptEconomy instance) throws PluginNotFoundException {
        super.onEnable(instance);
        // Register class
        this.register(instance);
    }

    private void register(OptEconomy instance) throws PluginNotFoundException {
        // Register expansion
        instance.getDebugger().info("Registering an expansion of PlaceholderAPI");
        if (this.isEnabled()) {
            new OptEconomyPlaceholderAPIExpansion(instance).register();
        }
    }

}
