package com.github.playernguyen.databases;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface OptEconomyDatabase {

    /**
     * In database, these are many tables. Which table have a unique name to search.
     * The database set uses to store it as a data structure.
     *
     * @return the set which store all database tables inside.
     */
    Set<OptEconomyTable<?>> getTableSet();

    /**
     * Add new table into the table set.
     *
     * @param table the table element.
     */
    default void addTable(OptEconomyTable<?> table) {
        this.getTableSet().add(table);
    }

    /**
     * Get the table in the set.
     *
     * @param name the table name
     * @return the table in set as {@link OptEconomyTable} object.
     */
    default OptEconomyTable<?> getTable(String name) {
        return this.getTableSet().stream().filter(e -> e.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    /**
     * Check whether the table exist in set
     *
     * @param name the table name
     * @return whether the table is exist or not.
     */
    default boolean hasTable(String name) {
        return this.getTable(name) != null;
    }

    /**
     * The establish class which provide the establishment to SQL connection
     *
     * @return the establish class
     */
    OptEconomySQLEstablish getEstablish();

    /**
     * Get plugin instance
     *
     * @return the plugin instance
     */
    OptEconomy getPlugin();

    /**
     * Open the connection and prepare for the data
     *
     * @param query the query connection to provide
     * @param args  the argument list to provide
     * @return the prepared statement
     * @throws SQLException whether cannot use SQL.
     */
    default PreparedStatement ready(String query, List<Object> args) throws SQLException {
        Connection connection = this.getEstablish().openConnection();
        this.getPlugin().getDebugger().info("Generate query " + query);
        PreparedStatement statement = connection.prepareStatement(query);
        if (args != null) {
            // Iterate the list to append data
            int i = 0;
            for (Object object : args) {
                statement.setObject(++i, object);
            }
        }
        return statement;
    }

}
