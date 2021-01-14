package com.github.playernguyen.databases.tables;

import com.github.playernguyen.databases.OptEconomyDatabase;
import com.github.playernguyen.databases.OptEconomyDatabaseObject;
import com.github.playernguyen.objects.OptEconomyPair;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The table of database class represent.
 * @param <
 */
public abstract class OptEconomyDatabaseTable<T extends OptEconomyDatabaseObject> implements Comparable<OptEconomyDatabaseTable> {
    private final String name;
    private final OptEconomyDatabase database;
    private final List<OptEconomyDatabaseTableField<?>> fieldList;

    public OptEconomyDatabaseTable(String name, OptEconomyDatabase databases) throws SQLException {
        this.name = name;
        this.database = databases;
        this.fieldList = new ArrayList<>();
        // Call set up function;
        this.setup();
        // Call create function;
        this.onCreate();
    }

    /**
     * Set up the table in database.
     *
     * @throws SQLException whether cause SQL issues.
     */
    private void setup() throws SQLException {
        StringBuilder dataSet = new StringBuilder();

        Iterator<OptEconomyDatabaseTableField<?>> iterator = fieldList.iterator();
        while (iterator.hasNext()) {
            OptEconomyDatabaseTableField<?> next = iterator.next();
            dataSet.append(next.toDeclaredString());
            if (iterator.hasNext()) {
                dataSet.append(", ");
            }
        }

        this.database.executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s (%s)",
                this.getName(),
                dataSet.toString())
        );
    }

    /**
     * Add new field into the field list.
     *
     * @param field the field want to add to field list
     * @return whether field was added.
     */
    public boolean addField(OptEconomyDatabaseTableField<?> field) {
        return this.fieldList.add(field);
    }

    /**
     * The abstract class call when the constructor was created
     */
    protected void onCreate() throws SQLException {
    }

    /**
     * Get the current database class
     *
     * @return the current database class
     */
    public OptEconomyDatabase getDatabase() {
        return database;
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
        PreparedStatement preparedStatement = this.getDatabase().prepare(
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
        PreparedStatement preparedStatement = this.getDatabase().prepare(queryBuilder.toString(),
                Arrays.stream(pairs).map(OptEconomyPair::getSecond).toArray()
        );
        // Execute and return rows affect. Whether 1 is true, 0 is false.
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
        PreparedStatement statement = this.getDatabase().prepare(
                builder.toString(),
                Arrays.stream(pairs).map(OptEconomyPair::getSecond).collect(Collectors.toList()).add(where.getSecond())
        );

        return (statement.executeUpdate() == 1);
    }

    /**
     * The field list
     *
     * @return the result of that list
     */
    public List<OptEconomyDatabaseTableField<?>> getFieldList() {
        return fieldList;
    }

    /**
     * Inheriting from {@link Comparable}
     */
    @Override
    public int compareTo(@NotNull OptEconomyDatabaseTable o) {
        return this.name.compareTo(o.name);
    }

    public List<T> select() {

    }
}
