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
        OptEconomyString url = new OptEconomyString(instance, OptEconomyConstants.JDBC_DRIVER_URL)
                .replace("%host%", host)
                .replace("%port%", port)
                .replace("%database%", database)
                .replace("%params%", params);
        String username = (String) instance.getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_USERNAME);
        String password = (String) instance.getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_MYSQL_PASSWORD);
        instance.getDebugger().info(String.format(
                "Open connection in %s", this.getClass().getName()
        ));

        // Return the connection
        return DriverManager.getConnection(url.toString(), username, password);
    }

    public List<String> tableList() throws SQLException {
        try (Connection connection = this.openConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SHOW TABLES;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> list = new ArrayList<>();
            // Iterate the resultSet then put to the table
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            // Return a list
            return list;
        }
    }

    public String tableName() {
        return (String) instance.getSettingConfiguration().get(
                OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY);
    }
}
