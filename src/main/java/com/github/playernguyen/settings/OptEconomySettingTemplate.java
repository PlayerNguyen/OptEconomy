package com.github.playernguyen.settings;

import com.github.playernguyen.configurations.OptEconomyTemplate;
import com.github.playernguyen.storages.OptEconomyStorageType;

/**
 * The setting template for the setting configuration.
 */
public enum OptEconomySettingTemplate implements OptEconomyTemplate {


    DEVELOPER_DEBUG("Developer.Debug", false),
    // Connection
    CONNECTION_MYSQL_HOST("Connection.MySQL.Host", "localhost"),
    CONNECTION_MYSQL_PORT("Connection.MySQL.Port", "3306"),
    CONNECTION_MYSQL_USERNAME("Connection.MySQL.Username", "root"),
    CONNECTION_MYSQL_PASSWORD("Connection.MySQL.Password", ""),
    CONNECTION_MYSQL_DATABASE("Connection.MySQL.Database", "opteco"),
    CONNECTION_MYSQL_PARAMS("Connection.MySQL.Params", "useSSL=false"),
    // General
    GENERAL_STORAGE_TYPE("General.StorageType", OptEconomyStorageType.SQLITE.getName()),
    GENERAL_LANGUAGE_FILE_NAME("General.LanguageFileName", "language.yml"),

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


