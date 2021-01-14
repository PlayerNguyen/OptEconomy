package com.github.playernguyen.databases.mysql;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.databases.OptEconomyDatabaseAbstract;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;
import com.github.playernguyen.settings.OptEconomySettingTemplate;

import java.sql.SQLException;

public class OptEconomyDatabaseMySQL extends OptEconomyDatabaseAbstract {
    public OptEconomyDatabaseMySQL(OptEconomy plugin, OptEconomySQLEstablish establish) throws SQLException {
        super(plugin, establish);
        // ----------------- Tables setup ------------- //
        plugin.getDebugger().info("Add the user table into table set (i) <OptEconomyDatabaseMySQL>");
        addTable(
                new OptEconomyTableUserMySQL(plugin.getSettingConfiguration()
                        .get(OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY), this)
        );
    }


}
