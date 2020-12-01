package com.github.playernguyen;

import com.github.playernguyen.databases.OptEconomyDatabaseSQLite;
import com.github.playernguyen.databases.OptEconomyDatabases;
import com.github.playernguyen.databases.OptEconomyDatabasesMySQL;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;
import com.github.playernguyen.establishs.OptEconomySQLEstablishMySQL;
import com.github.playernguyen.establishs.OptEconomySQLEstablishSQLite;
import com.github.playernguyen.exceptions.InvalidStorageTypeException;
import com.github.playernguyen.localizes.OptEconomyLocalizeConfiguration;
import com.github.playernguyen.players.OptEconomyPlayerManager;
import com.github.playernguyen.players.storages.OptEconomyPlayerStorageManager;
import com.github.playernguyen.players.storages.OptEconomyPlayerStorageManagerMySQL;
import com.github.playernguyen.players.storages.OptEconomyPlayerStorageManagerSQLite;
import com.github.playernguyen.settings.OptEconomySettingConfiguration;
import com.github.playernguyen.settings.OptEconomySettingTemplate;
import com.github.playernguyen.storages.OptEconomyStorageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;

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
    private OptEconomyDatabases databases;
    private OptEconomyPlayerStorageManager playerStorageManager;
    private OptEconomyPlayerManager playerManager;

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
            setupDatabases();
            setupPlayers();
        } catch (Exception e) {
            // Notice the Severe to user interface when catch error
            this.getLogger().severe("Unexpected error - Disable the plugin");
            // Print the exception
            e.printStackTrace();
            // Disable the plugin
            this.getPluginLoader().disablePlugin(this);
        }
    }

    private void setupPlayers() {
        // Player storage manager set up
        if (playerStorageManager == null) {
            switch (getStorageType()) {
                case MYSQL: {
                    this.playerStorageManager = new OptEconomyPlayerStorageManagerMySQL(this.getDatabases());
                    break;
                }
                case SQLITE: {
                    this.playerStorageManager = new OptEconomyPlayerStorageManagerSQLite(this.getDatabases());
                    break;
                }
            }
        }
        // Player manager set up
        if (playerManager == null) {
            this.playerManager = new OptEconomyPlayerManager(this);
        } else {
            playerManager.collection().clear();
        }
    }

    private void setupDatabases() throws InvalidStorageTypeException, SQLException {
        OptEconomyStorageType storageType = this.getStorageType();
        // Whether null, handle otherwise
        if (storageType == null) {
            throw new InvalidStorageTypeException(
                    "Cannot found the storage type: " +
                            this.settingConfiguration.get(OptEconomySettingTemplate.GENERAL_STORAGE_TYPE)
            );
        } else {
            switch (storageType) {
                case SQLITE: {
                    this.databases = new OptEconomyDatabaseSQLite(this, this.getEstablish());
                    break;
                }
                case MYSQL: {
                    this.databases = new OptEconomyDatabasesMySQL(this, this.getEstablish());
                    break;
                }
            }
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
    private void setupEstablish() throws Exception {
        OptEconomyStorageType storageType = this.getStorageType();
        // Whether null, handle otherwise
        if (storageType == null) {
            throw new InvalidStorageTypeException("Cannot found the storage type: "
                    + this.settingConfiguration.get(OptEconomySettingTemplate.GENERAL_STORAGE_TYPE)
            );
        } else {
            this.getLogger().info("Select storage type: " + storageType.toString());
            switch (storageType) {
                case SQLITE: {
                    this.establish = new OptEconomySQLEstablishSQLite(this);
                    break;
                }
                case MYSQL: {
                    this.establish = new OptEconomySQLEstablishMySQL(this);
                    break;
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

    /**
     * Get the storage type which configured in the configuration
     * @return the storage type which configured in the configuration
     */
    private OptEconomyStorageType getStorageType() {
        String storageType = (String) getSettingConfiguration().get(OptEconomySettingTemplate.GENERAL_STORAGE_TYPE);
        return OptEconomyStorageType.getTypeFromString(storageType);
    }

    /**
     * The databases of player to get and interact with database server
     * @return the {@link OptEconomyDatabases} class
     */
    public OptEconomyDatabases getDatabases() {
        return databases;
    }

    /**
     * Get storage manager of Player which linked to the server
     * @return the storage manager of player which linked to the server
     */
    public OptEconomyPlayerStorageManager getPlayerStorageManager() {
        return playerStorageManager;
    }
}
