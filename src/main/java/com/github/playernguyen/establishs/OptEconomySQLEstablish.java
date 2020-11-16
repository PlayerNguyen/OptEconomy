package com.github.playernguyen.establishs;

import java.sql.Connection;
import java.sql.SQLException;

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


}
