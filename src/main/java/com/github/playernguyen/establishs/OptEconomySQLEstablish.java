package com.github.playernguyen.establishs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * The class establish is an class that will establish SQL connection
 * when user calls.
 */
public interface OptEconomySQLEstablish {

    /**
     * Open the SQL connection
     *
     * @return the SQL connection which was initiated with configured by start the server
     * @throws SQLException the exception when system
     *                      cannot open the SQL connection
     */
    Connection openConnection() throws SQLException;

//    /**
//     * Show the table list
//     * @return the table list as {@link List}
//     */
//    List<String> tableList() throws SQLException;

//    /**
//     * The name of table in configuration
//     * @return the name of table
//     */
//    String tableName();
}
