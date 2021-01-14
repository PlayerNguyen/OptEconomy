package com.github.playernguyen.databases;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.databases.tables.OptEconomyDatabaseTableUserMySQL;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;

import java.sql.SQLException;

public class OptEconomyDatabaseSQLite extends OptEconomyDatabase {

    private final OptEconomy instance;

    public OptEconomyDatabaseSQLite(OptEconomy instance, OptEconomySQLEstablish establish) throws SQLException {
        super(establish);
        // Set up instance
        this.instance = instance;
        // Then set up the table
//        this.setUserTable(new OptEconomyDatabaseTableUserSQLite(
//                getInstance()
//                        .getSettingConfiguration().get(OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY),
//                this
//        ));
    }

    public OptEconomy getInstance() {
        return instance;
    }

}
