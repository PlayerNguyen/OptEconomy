package com.github.playernguyen.databases.sqlite;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.databases.OptEconomyDatabaseAbstract;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;
import com.github.playernguyen.settings.OptEconomySettingTemplate;

import java.sql.SQLException;

public class OptEconomyDatabaseSQLite extends OptEconomyDatabaseAbstract {
    public OptEconomyDatabaseSQLite(OptEconomy plugin, OptEconomySQLEstablish establish) throws SQLException {
        super(plugin, establish);
        // ------------ Table set up ------------------ //
        // user table
        addTable(
                new OptEconomyTableUserSQLite(
                        plugin.getSettingConfiguration().getString(OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY),
                        this
                )
        );
    }
}
