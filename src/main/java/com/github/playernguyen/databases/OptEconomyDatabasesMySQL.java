package com.github.playernguyen.databases;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.databases.tables.OptEconomyDatabaseTableUserMySQL;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;
import com.github.playernguyen.settings.OptEconomySettingTemplate;

import java.sql.SQLException;

/**
 * The database class of MySQL
 */
public class OptEconomyDatabasesMySQL extends OptEconomyDatabases {

    private final OptEconomy instance;

    /**
     * The constructor
     *
     * @param establish the establish class
     */
    public OptEconomyDatabasesMySQL(OptEconomy instance, OptEconomySQLEstablish establish)
            throws SQLException {
        super(establish);
        // Get the instance
        this.instance = instance;
        this.setUserTable(new OptEconomyDatabaseTableUserMySQL(
                (String) instance.getSettingConfiguration().get(OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY),
                this
        ));
    }

    /**
     * The instance of OptEconomy
     * @return the instance of OptEconomy
     */
    public OptEconomy getInstance() {
        return instance;
    }
}
