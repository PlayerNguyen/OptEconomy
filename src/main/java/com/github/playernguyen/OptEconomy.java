package com.github.playernguyen;

import com.github.playernguyen.establishs.OptEconomySQLEstablish;
import com.github.playernguyen.establishs.OptEconomySQLEstablishMySQL;
import com.github.playernguyen.establishs.OptEconomySQLEstablishSQLite;
import com.github.playernguyen.exceptions.InvalidStorageTypeException;
import com.github.playernguyen.localizes.OptEconomyLocalizeConfiguration;
import com.github.playernguyen.settings.OptEconomySettingConfiguration;
import com.github.playernguyen.settings.OptEconomySettingTemplate;
import com.github.playernguyen.storages.OptEconomyStorageType;
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
    private OptEconomySQLEstablish establish;
    private OptEconomyLocalizeConfiguration localizeConfiguration;

    /**
     * Enable function
     */
    @Override
    public void onEnable() {
        try {
            setupInstance();
            setupSetting();
            setupLocalize();
            setupEstablish();

        } catch (Exception e) {
            // Notice the Severe to user interface
            this.getLogger().severe("Unexpected error - Disable the plugin");
            // Print the exception
            e.printStackTrace();
            // Disable the plugin
            this.getPluginLoader().disablePlugin(this);
        }
    }

    /**
     * Set up the localize
     */
    private void setupLocalize() throws IOException {
        if (this.localizeConfiguration == null) {
            this.localizeConfiguration = new OptEconomyLocalizeConfiguration(this,
                    (String) getSettingConfiguration().get(OptEconomySettingTemplate.GENERAL_LANGUAGE_FILE_NAME)
            );
        } else {
            this.localizeConfiguration.reload();
        }
    }

    /**
     * Set up the SQL establish
     *
     * @throws InvalidStorageTypeException invalid storage type in configuration
     */
    private void setupEstablish() throws InvalidStorageTypeException, ClassNotFoundException {
        String storageType = (String) getSettingConfiguration().get(OptEconomySettingTemplate.GENERAL_STORAGE_TYPE);
        OptEconomyStorageType fromString = OptEconomyStorageType.getTypeFromString(storageType);
        // Whether null, handle otherwise
        if (fromString == null) {
            throw new InvalidStorageTypeException("Cannot found the storage type: " + storageType);
        } else {
            switch (fromString) {
                case SQLITE: {
                    this.establish = new OptEconomySQLEstablishSQLite(this);
                }
                case MYSQL: {
                    this.establish = new OptEconomySQLEstablishMySQL(this);
                }
            }
        }
    }

    /**
     * Set up for the setting. Init the new one or reload if the setting instanced
     *
     * @throws IOException whether cannot save the setting
     */
    private void setupSetting() throws IOException {
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

    /**
     * @return the establish class of SQL.
     */
    public OptEconomySQLEstablish getEstablish() {
        return establish;
    }

    /**
     * Get the localization configuration
     * @return the localization configuration
     */
    public OptEconomyLocalizeConfiguration getLocalizeConfiguration() {
        return localizeConfiguration;
    }
}
