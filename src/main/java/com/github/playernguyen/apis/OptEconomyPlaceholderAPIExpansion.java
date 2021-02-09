package com.github.playernguyen.apis;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.loggers.OptEconomyExceptionCatcher;
import com.github.playernguyen.settings.OptEconomySettingTemplate;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

/**
 * Expansion of OptEconomy, using to integrate with PlaceholderAPI plugin.
 */
public class OptEconomyPlaceholderAPIExpansion extends PlaceholderExpansion {
    private final OptEconomy instance;

    public OptEconomyPlaceholderAPIExpansion(OptEconomy instance) {
        this.instance = instance;
    }

    @Override
    public @NotNull String getIdentifier() {
        return instance.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return "Player_Nguyen";
    }

    @Override
    public @NotNull String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        // ----------- Register zone ------------- //
        // %opteconomy_points%
        if (params.equals("points")) {
            try {
                return instance.getPlayerManager().get(player.getUniqueId()).getBalance()
                        .formatted("#.##");
            } catch (SQLException e) {
                OptEconomyExceptionCatcher.stackTrace(e);
            }
        }
        // %opteconomy_default_points%
        if (params.equals("default_points")) {
            try {
                return instance.getPlayerManager().get(player.getUniqueId()).getBalance()
                        .formatted("#.##");
            } catch (SQLException e) {
                return "0";
            }
        }
        // %opteconomy_currency%
        if (params.equals("currency")) {
            return instance.getSettingConfiguration().getString(OptEconomySettingTemplate.GENERAL_POINT_CURRENCY);
        }
        // Not detect any circumstance
        return null;
    }
}
