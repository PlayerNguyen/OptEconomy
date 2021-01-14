package com.github.playernguyen.databases;

import java.sql.ResultSet;
import java.util.List;

/**
 * The database object is an adapter which have a method to convert from ResultSet to current object list.
 */
public interface OptEconomyDatabaseObject {

    /**
     * Database class will use this method to convert to the current object list
     * @param resultSet the result set (get from database)
     * @return the list of object from result
     */
    List<OptEconomyDatabaseObject> toObjectList(ResultSet resultSet);

}
