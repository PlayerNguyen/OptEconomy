package com.github.playernguyen.databases.tables;

import com.github.playernguyen.databases.OptEconomyDatabase;

import java.sql.SQLException;

/**
 * The user table of OptEconomy
 */
public abstract class OptEconomyDatabaseTableUser extends OptEconomyDatabaseTable {

    public OptEconomyDatabaseTableUser(String name, OptEconomyDatabase databases) throws SQLException {
        super(name, databases);
        // Register the field
        // Set up the database table
        this.addField(new OptEconomyDatabaseTableFieldMySQL("id",
                OptEconomyDatabaseFieldTypeMySQL.INTEGER,
                false,
                255,
                " PRIMARY KEY ")
        );
        this.addField(new OptEconomyDatabaseTableFieldMySQL("username",
                OptEconomyDatabaseFieldTypeMySQL.VARCHAR,
                false)
        );
        this.addField(new OptEconomyDatabaseTableFieldMySQL("uuid",
                OptEconomyDatabaseFieldTypeMySQL.VARCHAR,
                false)
        );
        this.addField(new OptEconomyDatabaseTableFieldMySQL("balance",
                OptEconomyDatabaseFieldTypeMySQL.VARCHAR,
                false)
        );
    }

    @Override
    protected void onCreate() throws SQLException {
        // Set up the database table
//        this.getDatabase().executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s (" +
//                        "id INT(32) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
//                        "username VARCHAR(255) NOT NULL," +
//                        "uuid VARCHAR(255) NOT NULL," +
//                        "balance VARCHAR(255) NOT NULL" +
//                        ");",
//                getName())
//        );
    }
}
