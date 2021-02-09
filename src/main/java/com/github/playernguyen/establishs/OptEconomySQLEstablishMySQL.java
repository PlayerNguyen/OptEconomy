package com.github.playernguyen.establishs;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.objects.OptEconomyString;
import com.github.playernguyen.settings.OptEconomySettingTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The MySQL establish which inherit from the {@link OptEconomySQLEstablish} class.
 */
public class OptEconomySQLEstablishMySQL implements OptEconomySQLEstablish {

    private final OptEconomy instance;

    public OptEconomySQLEstablishMySQL(OptEconomy instance) throws ClassNotFoundException, SQLException {
        this.instance = instance;
        // Init the driver
        Class.forName(OptEconomyConstants.MYSQL_DRIVER_MANAGER);
        // Open test connection
        instance.getDebugger().warning("Open the SQL connection to test");
        this.openConnection();
    }

    @Override
    public Connection openConnection() throws SQLException {
        // Init the driver
        String host = instance
                .getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_HOST);
        String port = instance
                .getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_PORT);
        String database = instance
                .getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_DATABASE);
        String params = instance
                .getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_PARAMS);
        OptEconomyString url = new OptEconomyString(instance, OptEconomyConstants.JDBC_DRIVER_URL)
                .replace("%host%", host)
                .replace("%port%", port)
                .replace("%database%", database)
                .replace("%params%", params);
        String username = instance.getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_USERNAME);
        String password = instance.getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_PASSWORD);
        instance.getDebugger().info(String.format(
                "Open connection in %s", this.getClass().getName()
        ));

        // Return the connection
        return DriverManager.getConnection(url.toString(), username, password);
    }

    @Override
    public String toString() {
        return "OptEconomySQLEstablishMySQL{" +
                "instance=" + instance +
                '}';
    }
}
