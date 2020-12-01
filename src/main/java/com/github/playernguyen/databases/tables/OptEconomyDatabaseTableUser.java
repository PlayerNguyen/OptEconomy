package com.github.playernguyen.databases.tables;

import com.github.playernguyen.databases.OptEconomyDatabases;

import java.sql.SQLException;

/**
 * The user table of OptEconomy
 */
public abstract class OptEconomyDatabaseTableUser extends OptEconomyDatabaseTable {

    public OptEconomyDatabaseTableUser(String name, OptEconomyDatabases databases) throws SQLException {
        super(name, databases);
    }

    @Override
    protected void onCreate() throws SQLException {
        // Set up the database table
        this.getDatabases().executeUpdate(String.format("CREATE TABLE %s IF NOT EXISTS (" +
                        "id INT(32) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "username VARCHAR(255) NOT NULL," +
                        "uuid VARCHAR(255) NOT NULL," +
                        "balance VARCHAR(255) NOT NULL" +
                        ");",
                getName())
        );
    }
}
