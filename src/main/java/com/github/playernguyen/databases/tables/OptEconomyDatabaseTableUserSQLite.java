package com.github.playernguyen.databases.tables;

import com.github.playernguyen.databases.OptEconomyDatabases;

import java.sql.SQLException;

public class OptEconomyDatabaseTableUserSQLite extends OptEconomyDatabaseTableUser {

    public OptEconomyDatabaseTableUserSQLite(String name, OptEconomyDatabases databases) throws SQLException {
        super(name, databases);
    }

    @Override
    protected void onCreate() throws SQLException {
        // This will not work
        // super.onCreate();
        // Create this one
        this.getDatabases().executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s (" +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL," +
                        "uuid TEXT NOT NULL," +
                        "balance TEXT NOT NULL);",
                this.getName())
        );
    }
}
