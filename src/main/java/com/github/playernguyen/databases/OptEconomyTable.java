package com.github.playernguyen.databases;

import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface OptEconomyTable<T extends OptEconomyObject> extends Comparable<OptEconomyTable<?>> {

    /**
     * The unique name of table to identify the table name.
     *
     * @return the unique name of table
     */
    @NotNull
    String getName();

    /**
     * The field set of this table which uses to declare and init the table.
     *
     * @return the field set
     */
    Set<OptEconomyTableField> getFieldSet();

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
     * This method uses to fetch the result from SQL execution response.
     *
     * @param response the response set which will be returned
     * @return the list which was converted from response.
     */
    List<T> adapt(ResultSet response) throws SQLException;

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

    default List<T> selectWhere(String where, String... alternative) throws SQLException {
        PreparedStatement ready = this.getDatabase().ready(String.format("SELECT * FROM %s WHERE %s", getName(), where),
                Arrays.asList(alternative)
        );
        // Return as an adapted list
        return adapt(ready.executeQuery());
    }

}
