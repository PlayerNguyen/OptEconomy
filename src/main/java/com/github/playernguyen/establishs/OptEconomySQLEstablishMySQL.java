package com.github.playernguyen.establishs;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.objects.OptEconomyString;
import com.github.playernguyen.settings.OptEconomySettingTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The MySQL establish which inherit from the {@link OptEconomySQLEstablish} class.
 */
public class OptEconomySQLEstablishMySQL implements OptEconomySQLEstablish {

    private final OptEconomy instance;

    public OptEconomySQLEstablishMySQL(OptEconomy instance) throws ClassNotFoundException {
        this.instance = instance;
        // Init the driver
        Class.forName(OptEconomyConstants.MYSQL_DRIVER_MANAGER);
    }

    @Override
    public Connection openConnection() throws SQLException {
        // Init the driver
        String host = (String) instance
                .getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_HOST);
        String port = (String) instance
                .getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_PORT);
        String database = (String) instance
                .getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_DATABASE);
        String params = (String) instance
                .getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_PARAMS);
        OptEconomyString url = new OptEconomyString(OptEconomyConstants.JDBC_DRIVER_URL)
                .replace("%host%", host)
                .replace("%port%", port)
                .replace("%database%", database)
                .replace("%params%", params);
        return DriverManager.getConnection(url.toString());
    }
}
