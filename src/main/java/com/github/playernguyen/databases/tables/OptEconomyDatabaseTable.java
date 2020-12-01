package com.github.playernguyen.databases.tables;

import com.github.playernguyen.databases.OptEconomyDatabases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The table of database class represent
 */
public abstract class OptEconomyDatabaseTable {

    private final String name;
    private final OptEconomyDatabases databases;

    public OptEconomyDatabaseTable(String name, OptEconomyDatabases databases) throws SQLException {
        this.name = name;
        this.databases = databases;
        // Create
        this.onCreate();
    }

    /**
     * The abstract class call when the constructor was created
     */
    protected abstract void onCreate() throws SQLException;

    /**
     * Get the current database class
     *
     * @return the current database class
     */
    public OptEconomyDatabases getDatabases() {
        return databases;
    }

    /**
     * The name of table
     *
     * @return the name of table which was initiated
     */
    public String getName() {
        return name;
    }

    /**
     * Select the data in database
     *
     * @param args    the argument after SELECT * FROM ...
     * @param objects the objects to be replaced
     * @return the result set which was selected
     * @throws SQLException whether the SQL in not working
     */
    public ResultSet selectAll(String args, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = this.getDatabases().prepare(
                String.format("SELECT * FROM %s " + args, this.getName()),
                objects);
        return preparedStatement.executeQuery();
    }


}
