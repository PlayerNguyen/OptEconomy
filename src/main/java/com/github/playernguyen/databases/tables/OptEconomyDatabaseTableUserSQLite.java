package com.github.playernguyen.databases.tables;

import com.github.playernguyen.databases.OptEconomyDatabase;

import java.sql.SQLException;

public class OptEconomyDatabaseTableUserSQLite extends OptEconomyDatabaseTableUser {

    public OptEconomyDatabaseTableUserSQLite(String name, OptEconomyDatabase databases) throws SQLException {
        super(name, databases);
    }

    @Override
    protected void onCreate() throws SQLException {
        // This method will not work;
        // super.onCreate();
        // Re-generate this one;
        this.getDatabase().executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s (" +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL," +
                        "uuid TEXT NOT NULL," +
                        "balance TEXT NOT NULL);",
                this.getName())
        );
    }
}
