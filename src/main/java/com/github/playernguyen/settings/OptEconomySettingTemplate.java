package com.github.playernguyen.settings;

import com.github.playernguyen.configurations.OptEconomyTemplate;
import com.github.playernguyen.storages.OptEconomyStorageType;

/**
 * The setting template for the setting configuration.
 */
public enum OptEconomySettingTemplate implements OptEconomyTemplate {

//      Change to Debug.profile
//    DEVELOPER_DEBUG("Developer.Debug", false),
    // Connections
    CONNECTION_MYSQL_HOST("Connection.MySQL.Host", "localhost"),
    CONNECTION_MYSQL_PORT("Connection.MySQL.Port", "3306"),
    CONNECTION_MYSQL_USERNAME("Connection.MySQL.Username", "root"),
    CONNECTION_MYSQL_PASSWORD("Connection.MySQL.Password", ""),
    CONNECTION_MYSQL_DATABASE("Connection.MySQL.Database", "opteco"),
    CONNECTION_MYSQL_PARAMS("Connection.MySQL.Params", "useSSL=false"),

    CONNECTION_TABLES_OPTECONOMY("Connection.Tables.OptEco", "opteco"),
    // Generals
    GENERAL_STORAGE_TYPE("General.StorageType", OptEconomyStorageType.SQLITE.getName()),
    GENERAL_SQLITE_FILENAME("General.SQLite.Filename", "Data.db"),
    GENERAL_LANGUAGE_FILE_NAME("General.LanguageFileName", "Language.yml"),
    GENERAL_USING_INTEGER("General.UsingInteger", "false"),
    GENERAL_POINT_CURRENCY("General.PointCurrency.Single", "coin"),
    // Options
    CACHE_STORAGE_DURATION("CacheStorageDuration", 500L),
//    VACUUM_CACHE_UPDATE_DURATION("Vacuum.Duration", 500L),
    // Command
    COMMAND_TAB_COMPLETION("Command.TabCompletion", true)

    ;

    private final String path;
    private final Object declare;


    OptEconomySettingTemplate(String path, Object declare) {
        this.path = path;
        this.declare = declare;
    }

    @Override
    public Object declare() {
        return declare;
    }

    @Override
    public String path() {
        return path;
    }


}


