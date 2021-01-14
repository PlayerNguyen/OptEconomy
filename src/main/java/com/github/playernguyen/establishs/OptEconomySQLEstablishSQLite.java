package com.github.playernguyen.establishs;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.settings.OptEconomySettingTemplate;

import java.io.File;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The inheritance of {@link OptEconomySQLEstablish} class
 */
public class OptEconomySQLEstablishSQLite implements OptEconomySQLEstablish {

    private final OptEconomy instance;
    private final String url;

    public OptEconomySQLEstablishSQLite(OptEconomy instance) throws SQLException {
        this.instance = instance;
        File dataFolder = instance.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            throw new IllegalStateException("Cannot found and create data folder...");
        }
        String path = new File(dataFolder.getPath(),
                (String) instance.getSettingConfiguration().get(OptEconomySettingTemplate.GENERAL_SQLITE_FILENAME)
        ).getPath();
        this.url = OptEconomyConstants.JDBC_SQLITE_DRIVER_URL.replace("%path%", path);
        // Open connection to test
        instance.getDebugger().warning("Open the SQL connection to test");
        this.openConnection();

    }

    @Override
    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(this.url);
    }

    public List<String> tableList() throws SQLException {
        Connection connection = this.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM sqlite_master WHERE type='table' AND NAME NOT LIKE 'sqlite_%'");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> tableList = new LinkedList<>();
        while (resultSet.next()) {
            tableList.add(resultSet.getString(1));
        }
        return tableList;
    }

    public String tableName() {
        return (String) instance.getSettingConfiguration().get(
                OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY);
    }
}
