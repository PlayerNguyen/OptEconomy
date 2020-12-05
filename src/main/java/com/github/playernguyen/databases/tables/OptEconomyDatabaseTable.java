package com.github.playernguyen.databases.tables;

import com.github.playernguyen.databases.OptEconomyDatabases;
import com.github.playernguyen.objects.OptEconomyPair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

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
     * @throws SQLException whether the SQL is not working
     */
    public ResultSet selectAll(String args, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = this.getDatabases().prepare(
                String.format("SELECT * FROM %s " + args, this.getName()),
                objects);
        return preparedStatement.executeQuery();
    }

    /**
     * Insert new item into the database
     *
     * @param pairs the {@link OptEconomyPair} objects
     * @return true whether succeed insert, false otherwise
     * @throws SQLException whether the SQL is not working
     */
    @SafeVarargs
    public final boolean insert(OptEconomyPair<String, Object>... pairs) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder(String.format("INSERT INTO %s (", this.name));

        // Element (key) build function
        for (OptEconomyPair<String, Object> pair : pairs) {
            queryBuilder.append(pair.getFirst());

            // Whether not last element, put `,`.
            // Or close tag otherwise
            if (!pair.getFirst().equals(pairs[pairs.length - 1].getFirst())) {
                queryBuilder.append(", ");
            } else {
                queryBuilder.append(") ");
            }
        }
        queryBuilder.append("VALUES (");
        // Data build function
        for (OptEconomyPair<String, Object> pair : pairs) {
            queryBuilder.append("?");

            if (!pair.getFirst().equals(pairs[pairs.length - 1].getFirst())) {
                queryBuilder.append(", ");
            } else {
                queryBuilder.append(");");
            }
        }
        // Prepare and execute
        PreparedStatement preparedStatement = this.getDatabases().prepare(queryBuilder.toString(),
                Arrays.stream(pairs).map(OptEconomyPair::getSecond).toArray()
        );
        // Execute and return rows affect. Whether 1 is true, 0 is false
        return (preparedStatement.executeUpdate() == 1);
    }

    @SafeVarargs
    public final boolean update(OptEconomyPair<String, Object> where, OptEconomyPair<String, Object>... pairs) throws SQLException {
        StringBuilder builder = new StringBuilder(String.format("UPDATE %s SET ", this.name));
        for (OptEconomyPair<String, Object> pair : pairs) {
            builder.append(pair.getFirst()).append("=").append("?");
            // Whether not the last. Put `,`
            if (!pair.getFirst().equals(pairs[pairs.length - 1].getFirst())) {
                builder.append(", ");
            }
        }
        builder.append(" WHERE ").append(where.getFirst()).append("=").append("?");
        PreparedStatement statement = this.getDatabases().prepare(
                builder.toString(),
                Arrays.stream(pairs).map(OptEconomyPair::getSecond).collect(Collectors.toList()).add(where.getSecond())
        );

        return (statement.executeUpdate() == 1);
    }

}
