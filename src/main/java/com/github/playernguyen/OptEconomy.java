package com.github.playernguyen;

import com.github.playernguyen.apis.OptEconomyAPIManager;
import com.github.playernguyen.apis.OptEconomyPluginPlaceholderAPI;
import com.github.playernguyen.commands.OptEconomyCommandExecutorManager;
import com.github.playernguyen.commands.executors.OptEconomyCommandOptEconomy;
import com.github.playernguyen.databases.OptEconomyDatabase;
import com.github.playernguyen.databases.mysql.OptEconomyDatabaseMySQL;
import com.github.playernguyen.databases.sqlite.OptEconomyDatabaseSQLite;
import com.github.playernguyen.debuggers.OptEconomyDebugger;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;
import com.github.playernguyen.establishs.OptEconomySQLEstablishMySQL;
import com.github.playernguyen.establishs.OptEconomySQLEstablishSQLite;
import com.github.playernguyen.exceptions.InvalidStorageTypeException;
import com.github.playernguyen.exceptions.PluginNotFoundException;
import com.github.playernguyen.listeners.OptEconomyAbstractListener;
import com.github.playernguyen.listeners.OptEconomyListenerManager;
import com.github.playernguyen.listeners.OptEconomyPlayerListener;
import com.github.playernguyen.localizes.OptEconomyLocalizeConfiguration;
import com.github.playernguyen.loggers.OptEconomyExceptionCatcher;
import com.github.playernguyen.loggers.OptEconomyLogger;
import com.github.playernguyen.players.OptEconomyPlayerCacheManager;
import com.github.playernguyen.players.storages.OptEconomyPlayerStorageManager;
import com.github.playernguyen.players.storages.OptEconomyPlayerStorageManagerMySQL;
import com.github.playernguyen.players.storages.OptEconomyPlayerStorageManagerSQLite;
import com.github.playernguyen.runnables.OptEconomyRunnableVacuum;
import com.github.playernguyen.settings.OptEconomySettingConfiguration;
import com.github.playernguyen.settings.OptEconomySettingTemplate;
import com.github.playernguyen.storages.OptEconomyStorageType;
import com.github.playernguyen.vacuums.OptEconomyVacuum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private OptEconomyLogger logger;
    private OptEconomySettingConfiguration settingConfiguration;
    private OptEconomyDebugger debugger;
    private OptEconomySQLEstablish establish;
    private OptEconomyLocalizeConfiguration localizeConfiguration;
    private OptEconomyDatabase database;
    private OptEconomyPlayerStorageManager playerStorageManager;
    private OptEconomyPlayerCacheManager playerManager;
    private OptEconomyListenerManager listenerManager;
    private OptEconomyAPIManager APIManager;
    private OptEconomyRunnableVacuum runnableVacuum;
    private OptEconomyCommandExecutorManager executorManager;

    /**
     * Enable function
     */
    @Override
    public void onEnable() {
        try {
            this.setupLogger();
            this.setupInstance();
            this.setupDebugger();
            this.setupSetting();
            this.setupLocalize();
            this.setupEstablish();
            this.setupDatabases();
            this.setupPlayers();
            this.setupListener();
            this.setupDependencies();
            // No vacuum
//            this.setupVacuum();
            this.setupCommands();
        } catch (Exception e) {
            // Notice the Severe to user interface when catch error
            this.getOptLogger().error("Disable the plugin because of error: ");
            // Print the exception
            OptEconomyExceptionCatcher.stackTrace(e);
            // Disable the plugin
            this.getPluginLoader().disablePlugin(this);
        }
    }

    private void setupCommands() {
        if (this.executorManager == null) {
            this.executorManager = new OptEconomyCommandExecutorManager(this);
        } else {
            this.executorManager.clear();
        }
        this.executorManager.register(new OptEconomyCommandOptEconomy(this));
    }

//    private void setupVacuum() {
//        if (this.runnableVacuum == null) {
//            this.runnableVacuum = new OptEconomyRunnableVacuum(this, new OptEconomyVacuum(this));
//        } else {
//            this.runnableVacuum.cancel();
//        }
//        // Bukkit.getScheduler().runTaskTimerAsynchronously(this, this.runnableVacuum, 0, 0);
//        this.runnableVacuum.runTaskTimerAsynchronously(this, 0, 0);
//    }

    /**
     * Dependencies set up
     * @throws PluginNotFoundException whether not found the plugin
     */
    private void setupDependencies() throws PluginNotFoundException {
        if (this.APIManager == null) {
            this.APIManager = new OptEconomyAPIManager(this);
        } else {
            this.APIManager.clear();
        }
        // Register item
        this.APIManager.register(
                OptEconomyConstants.PLUGIN_NAME_PLACEHOLDER_API,
                new OptEconomyPluginPlaceholderAPI(this)
        );
    }

    /**
     * Set up the logger
     */
    private void setupLogger() {
        this.logger = new OptEconomyLogger();
        this.logger.good(ChatColor.AQUA + "OptEconomy v." + this.getDescription().getVersion());
    }

    /**
     * Set up the listener
     */
    private void setupListener() {
        this.getOptLogger().normal("Loading listener manager...");
        if (this.listenerManager == null) {
            this.listenerManager = new OptEconomyListenerManager(this);
        } else {
            for (OptEconomyAbstractListener listener : this.listenerManager) {
                this.listenerManager.unregister(listener);
            }
        }
        // Register listener
        this.listenerManager.register(new OptEconomyPlayerListener(this));
    }

    /**
     * Set up the debugger
     */
    private void setupDebugger() {
        if (this.debugger == null) {
            this.debugger = new OptEconomyDebugger(this);
        }
    }

    /**
     * Set up player
     */
    private void setupPlayers() throws SQLException {
        this.getOptLogger().normal("Loading player manager...");
        // Player storage manager set up
        if (playerStorageManager == null) {
            switch (getStorageType()) {
                case MYSQL: {
                    this.playerStorageManager = new OptEconomyPlayerStorageManagerMySQL(this, this.getDatabase());
                    break;
                }
                case SQLITE: {
                    this.playerStorageManager = new OptEconomyPlayerStorageManagerSQLite(this, this.getDatabase());
                    break;
                }
            }
        }

        // Player manager set up
        if (playerManager == null) {
            this.playerManager = new OptEconomyPlayerCacheManager(this);
        } else {
            playerManager.collection().clear();
        }
        // Refresh the online player
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            this.getOptLogger().info(String.format("Retrieving data from player %s", onlinePlayer.getUniqueId()));
            playerManager.get(onlinePlayer.getUniqueId());
        }
    }

    /**
     * Set up the database of plugin
     *
     * @throws InvalidStorageTypeException The storage not found
     * @throws SQLException                the SQL cannot be connected
     */
    private void setupDatabases() throws InvalidStorageTypeException, SQLException {
        this.getOptLogger().normal("Loading database data...");
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
//                    throw new NullPointerException();
                    this.database = new OptEconomyDatabaseSQLite(this, this.getEstablish());
                    break;
                }
                case MYSQL: {
                    this.database = new OptEconomyDatabaseMySQL(this, this.establish);
                    break;
                }
                default: throw new UnsupportedOperationException("Invalid database construction <?>");
            }
        }
    }

    /**
     * Set up the localize
     */
    private void setupLocalize() throws IOException {
        this.getOptLogger().normal("Loading language file...");
        if (this.localizeConfiguration == null) {
            this.localizeConfiguration = new OptEconomyLocalizeConfiguration(this,
                    getSettingConfiguration().get(OptEconomySettingTemplate.GENERAL_LANGUAGE_FILE_NAME)
            );
        } else {
            this.localizeConfiguration.reload();
        }
    }

    /**
     * Set up the SQL establishment
     *
     * @throws InvalidStorageTypeException invalid storage type in configuration
     */
    private void setupEstablish() throws Exception {
        this.getOptLogger().normal("Loading SQL establishment...");
        OptEconomyStorageType storageType = this.getStorageType();
        // Whether null, handle otherwise
        if (storageType == null) {
            throw new InvalidStorageTypeException("Cannot found the storage type: "
                    + this.settingConfiguration.get(OptEconomySettingTemplate.GENERAL_STORAGE_TYPE)
            );
        } else {
            this.getOptLogger().normal("Detecting configured storage type: " + storageType.toString().toUpperCase());
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
        this.getOptLogger().normal("Loading settings...");
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
        this.getOptLogger().normal(String.format(
                "%s version %s initiating...", this.getName(), this.getDescription().getVersion()
        ));
        instance = this;
    }

    /**
     * Get the current instance
     *
     * @return the current instance of OptEconomy
     */
    public static OptEconomy inst() {
//        System.out.println("[OptEconomy] " +
//                "Some plugin call OptEconomy outside (leaking databases information warning)");
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
     *
     * @return the localization configuration
     */
    public OptEconomyLocalizeConfiguration getLocalizeConfiguration() {
        return localizeConfiguration;
    }

    /**
     * Get the storage type which configured in the configuration
     *
     * @return the storage type which configured in the configuration
     */
    private OptEconomyStorageType getStorageType() {
        String storageType = this.getSettingConfiguration()
                .getString(OptEconomySettingTemplate.GENERAL_STORAGE_TYPE);

        return OptEconomyStorageType.getTypeFromString(storageType);
    }

    /**
     * The databases of player to get and interact with database server
     *
     * @return the {@link OptEconomyDatabase} class
     */
    public OptEconomyDatabase getDatabase() {
        return database;
    }

    /**
     * Get storage manager of Player which linked to the server
     *
     * @return the storage manager of player which linked to the server
     */
    public OptEconomyPlayerStorageManager getPlayerStorageManager() {
        return playerStorageManager;
    }

    /**
     * The debugger object
     *
     * @return the debugger object. To debug
     */
    public OptEconomyDebugger getDebugger() {
        return debugger;
    }

    /**
     * Player manager class to manage player cache and downloaded player
     *
     * @return the manager class of player
     */
    public OptEconomyPlayerCacheManager getPlayerManager() {
        return playerManager;
    }

    /**
     * @return A logger class to log out to the console
     */
    public OptEconomyLogger getOptLogger() {
        return this.logger;
    }

    /**
     * API Manager
     *
     * @return the API Manager class
     */
    public OptEconomyAPIManager getAPIManager() {
        return APIManager;
    }
}
