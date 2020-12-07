package com.github.playernguyen.apis;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.exceptions.PluginNotFoundException;
import com.github.playernguyen.loggers.OptEconomyExceptionCatcher;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

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
    public void onEnable(OptEconomy instance) {
        super.onEnable(instance);
        // Register class
        this.register(instance);
    }

    private void register(OptEconomy instance) {
        // Register expansion
        instance.getDebugger().info("Registering an expansion of PlaceholderAPI");
        this.getExpansion(instance).register();
    }

    public PlaceholderExpansion getExpansion(OptEconomy instance) {
        return new PlaceholderExpansion() {
            @Override
            public @NotNull String getIdentifier() {
                return instance.getName();
            }

            @Override
            public @NotNull String getAuthor() {
                return instance
                        .getDescription()
                        .getAuthors()
                        .toString()
                        .replace("[", "")
                        .replace("]", "");
            }

            @Override
            public @NotNull String getVersion() {
                return instance.getDescription().getVersion();
            }

            @Override
            public String onPlaceholderRequest(Player player, @NotNull String params) {

                // %opteconomy_points%
                if (params.equals("points")) {
                    try {
                        return instance.getPlayerManager().get(player.getUniqueId()).getBalance()
                                .formatted("#.##");
                    } catch (SQLException e) {
                        OptEconomyExceptionCatcher.stackTrace(e);
                    }
                }

                return null;
            }
        };
    }

}
