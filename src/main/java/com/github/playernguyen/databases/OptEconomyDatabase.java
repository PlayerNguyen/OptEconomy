package com.github.playernguyen.databases;

import com.github.playernguyen.databases.tables.OptEconomyDatabaseTable;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * The represent class for the database
 */
public abstract class OptEconomyDatabase {
    private final OptEconomySQLEstablish establish;
    //    private OptEconomyDatabaseTableUser userTable;
    private final Set<OptEconomyDatabaseTable<?>> tableSet = new TreeSet<>();

    public OptEconomyDatabase(OptEconomySQLEstablish establish)
            throws SQLException {
        // Load the variables
        this.establish = establish;
    }

    /**
     * The establish of database
     *
     * @return the establish of database
     */
    public OptEconomySQLEstablish getEstablish() {
        return establish;
    }

    /**
     * Prepare the statement to call query
     *
     * @param prepareString the query to prepare
     * @param objects       the object list
     * @return the class which returned
     * @throws SQLException whether cannot prepare the statement
     */
    public PreparedStatement prepare(String prepareString, Object... objects) throws SQLException {
        Connection connection = establish.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(prepareString);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
        return preparedStatement;
    }

    /**
     * Executing the statement query
     *
     * @param string  the query to execute
     * @param objects the objects will be replaced by the object
     * @return the boolean statement of execute
     * @throws SQLException unless the SQL work.
     * @see PreparedStatement#execute()
     */
    public boolean execute(String string, Object... objects) throws SQLException {
        PreparedStatement prepare = this.prepare(string, objects);
        return prepare.execute();
    }

    /**
     * Executing the ResultSet query
     *
     * @param string  the query to execute
     * @param objects the objects will be replaced by objeects
     * @return the {@link ResultSet} will be returned
     * @throws SQLException unless the SQL work
     */
    public ResultSet executeQuery(String string, Object... objects) throws SQLException {
        PreparedStatement prepare = this.prepare(string, objects);
        return prepare.executeQuery();
    }

    /**
     * Executing the updater query.
     *
     * @param string  the query
     * @param objects the objects to be replace
     * @return the rows affected
     */
    public int executeUpdate(String string, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = this.prepare(string, objects);
        return preparedStatement.executeUpdate();
    }

    /**
     * The table set which contain many table.
     *
     * @return the table set object
     */
    public Set<OptEconomyDatabaseTable<?>> getTableSet() {
        return tableSet;
    }
}
