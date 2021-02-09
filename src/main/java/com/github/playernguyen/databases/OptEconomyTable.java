package com.github.playernguyen.databases;

import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface OptEconomyTable<T extends OptEconomyObject> extends Comparable<OptEconomyTable<?>> {

    /**
     * The unique name of table to identify the table name.
     *
     * @return the unique name of table
     */
    @NotNull
    String getName();

    /**
     * The field list of this table which uses to declare and init the table.
     *
     * @return the field set
     */
    List<OptEconomyTableField> getFieldSet();

    /**
     * Compare two name of table. Inherit from {@link Comparable}
     *
     * @param o the another table one
     * @return the index of the comparison
     */
    @Override
    default int compareTo(@NotNull OptEconomyTable<?> o) {
        return this.getName().compareTo(o.getName());
    }

    /**
     * The main database to get utility for SQL
     *
     * @return the main database
     */
    OptEconomyDatabase getDatabase();

    /**
     * The primary key of this table
     *
     * @return the primary key name
     */
    String getPrimaryKey();

    /**
     * Set the primary key for this table
     *
     * @param name the primary key field name
     */
    void setPrimaryKey(String name);

    /**
     * Add new field into field set
     *
     * @param field the field set
     */
    default void addField(OptEconomyTableField field) {
        this.getFieldSet().add(field);
    }

    /**
     * This method uses to fetch results from SQL execution response.
     *
     * @param response the response set which will be returned
     * @return the list which was converted from response.
     */
    List<T> adapt(ResultSet response) throws SQLException;

    /**
     * This method uses to insert data into the SQL (send request and insert).
     *
     * @return the map which navigate field to data
     */
    Map<String, Object> redapt(T object);

    /**
     * Create the query uses to init the file
     *
     * @return the query builder
     */
    default String createQueryParameter() {
        StringBuilder builder = new StringBuilder();
        Iterator<OptEconomyTableField> iterator = getFieldSet().iterator();
        while (iterator.hasNext()) {
            OptEconomyTableField field = iterator.next();
            // Append the name of query
            builder.append(field.getName()).append(" ").append(field.getType().getDeclaration()).append(" ");
            // Append size into query
            if (field.getSize() != null) {
                builder.append("(").append(field.getSize()).append(")").append(" ");
            }
            // Nullable?
            if (!field.canNull()) {
                builder.append(" NOT NULL ");
            }
            // Moreover
            if (!field.args().equalsIgnoreCase("")) {
                builder.append(" ").append(field.args()).append(" ");
            }

            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        // Primary
        if (this.getPrimaryKey() != null) {
            builder.append(", PRIMARY KEY(").append(getPrimaryKey()).append(")");
        }
        return builder.toString();
    }

    /**
     * Select data from the SQL by using `where` key.
     *
     * @param where       from where
     * @param alternative the alternative replacement from PreparedStatement to prevent SQL injection.
     * @return the list of selection
     * @throws SQLException whether cannot execute the statement
     */
    default List<T> selectWhere(String where, String... alternative) throws SQLException {

        try (PreparedStatement ready = this.getDatabase().ready(String
                        .format("SELECT * FROM %s WHERE %s", getName(), where),
                Arrays.asList(alternative)
        )) {
            // Return as an adapted list
            return adapt(ready.executeQuery());
        }
    }

    /**
     * Insert new row onto the SQL structure.
     *
     * @param object the new object to be added
     * @throws SQLException whether conflict the identify or/and cannot execute SQL
     */
    default boolean insert(T object) throws SQLException {
        Map<String, Object> redapter = redapt(object);
        // ----- Keys and values ------
        StringBuilder keyQuery = new StringBuilder();
        StringBuilder result = new StringBuilder();
        Iterator<String> keyIter = redapter.keySet().iterator();
        while (keyIter.hasNext()) {
            String next = keyIter.next();
            keyQuery.append(next);
            result.append("?");

            if (keyIter.hasNext()) {
                keyQuery.append(", ");
                result.append(", ");
            }
        }
        // Prepare and build up command
        PreparedStatement ready = this.getDatabase().ready(
                String.format(
                        "INSERT INTO %s (%s) VALUES (%s)",
                        this.getName(),
                        keyQuery,
                        result
                ), new ArrayList<>(redapter.values())
        );
        return ready.executeUpdate() == 1;
    }

    default void update(T object, String where, String ...alternative) throws SQLException {
        Map<String, Object> redapt = redapt(object);
        // Build queries
        StringBuilder query = new StringBuilder();
        Iterator<String> iterator = redapt.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            query.append(next).append("=?");

            if (iterator.hasNext()) {
                query.append(", ");
            }
        }
        // End queries
        this.getDatabase().ready(
                String.format(
                        "UPDATE %s SET %s WHERE " + where,
                        this.getName(),
                        query.toString(),
                        where
                ), Collections.singletonList(new ArrayList<>(redapt.values()).addAll(Arrays.asList(alternative)))
        );
    }
}
