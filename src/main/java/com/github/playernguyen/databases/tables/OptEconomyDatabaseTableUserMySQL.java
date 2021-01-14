package com.github.playernguyen.databases.tables;

import com.github.playernguyen.databases.OptEconomyDatabase;

import java.sql.SQLException;

/**
 * Using for MySQL
 * @see OptEconomyDatabaseTableUser
 */
public class OptEconomyDatabaseTableUserMySQL extends OptEconomyDatabaseTableUser {

    public OptEconomyDatabaseTableUserMySQL(String name, OptEconomyDatabase databases)
            throws SQLException {
        super(name, databases);
    }
}
