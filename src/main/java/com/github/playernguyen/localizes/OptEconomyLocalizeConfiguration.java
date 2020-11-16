package com.github.playernguyen.localizes;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.configurations.OptEconomyConfiguration;
import com.github.playernguyen.objects.OptEconomyString;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * The localize class to load the localize
 */
public class OptEconomyLocalizeConfiguration extends OptEconomyConfiguration<OptEconomyLocalizeTemplate> {
    /**
     * Constructor of class
     *
     * @param instance  the instance class {@link OptEconomy}
     * @param name      the name of configuration. i.e config.yml,...
     * @throws IOException cannot save the configuration
     * @see OptEconomyConfiguration#save()
     */
    public OptEconomyLocalizeConfiguration(OptEconomy instance,
                                           String name) throws IOException {
        super(instance, name, OptEconomyLocalizeTemplate.values(), null);
    }

    /**
     * Get the raw localize
     * @param key the key of localize value
     * @return the raw value without translate the color
     */
    public OptEconomyString raw(OptEconomyLocalizeTemplate key) {
        return new OptEconomyString(this.get(key));
    }

    /**
     * Get the colorful localize
     * @param key the key of localize value
     * @return the colorful localize with translate color to get
     */
    public OptEconomyString colour(OptEconomyLocalizeTemplate key) {
        return new OptEconomyString(
                ChatColor.translateAlternateColorCodes(OptEconomyConstants.COLOUR_CHAR,
                        String.valueOf(this.get(key)))
        );
    }

    /**
     * Show the prefix color localize
     * @param key the key of localize value
     * @return the colorful localize with prefix
     */
    public OptEconomyString prefixedColour(OptEconomyLocalizeTemplate key) {
        return new OptEconomyString(
                ChatColor.translateAlternateColorCodes(OptEconomyConstants.COLOUR_CHAR,
                        this.get(OptEconomyLocalizeTemplate.PREFIX) + this.get(key))
        );
    }

    @Override
    public @Nullable String get(@NotNull OptEconomyLocalizeTemplate key) {
        return (String) super.get(key);
    }
}
