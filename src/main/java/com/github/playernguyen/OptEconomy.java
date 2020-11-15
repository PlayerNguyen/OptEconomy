package com.github.playernguyen;

import com.github.playernguyen.settings.OptEconomySettingConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * The main class for OptEconomy
 */
public final class OptEconomy extends JavaPlugin {

    // Global fields
    private static OptEconomy instance;

    // Local fields
    private OptEconomySettingConfiguration settingConfiguration;

    /**
     * Enable function
     */
    @Override
    public void onEnable() {
        try {
            setupInstance();
            setupSettings();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Set up for the setting. Init the new one or reload if the setting instanced
     *
     * @throws IOException whether cannot save the setting
     */
    private void setupSettings() throws IOException {
        if (this.settingConfiguration == null) {
            this.settingConfiguration = new OptEconomySettingConfiguration(this);
        } else {
            this.settingConfiguration.reload();
        }
    }

    /**
     * Set up the instance of OptEconomy
     */
    private void setupInstance() {
        instance = this;
    }

    /**
     * Get the current instance
     *
     * @return the current instance of OptEconomy
     */
    public static OptEconomy inst() {
        return instance;
    }

    /**
     * @return the setting configuration class
     */
    public OptEconomySettingConfiguration getSettingConfiguration() {
        return settingConfiguration;
    }
}
