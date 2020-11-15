package com.github.playernguyen.settings;

import com.github.playernguyen.configurations.OptEconomyTemplate;

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

    ;

    private final String path;
    private final Object declare;
    private final String[] comments;

    OptEconomySettingTemplate(String path, Object declare) {
        this(path, declare, null);
    }

    OptEconomySettingTemplate(String path, Object declare, String[] comments) {
        this.path = path;
        this.declare = declare;
        this.comments = comments;
    }

    @Override
    public Object declare() {
        return declare;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public String[] comments() {
        return comments;
    }
}

