package com.github.playernguyen.databases.tables;

public interface OptEconomyDatabaseFieldType {

    /**
     * The name of variable in SQL level
     * @return the variable name
     */
    String getName();

    /**
     * Size of that data type in SQL Level
     * @return the size of data
     */
    Integer getSize();

    /**
     * Parse out to string value
     * @return the query string
     */
    default String toDeclaredString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" ");
        if (getSize() != null) {
            builder.append("(").append(getSize()).append(")").append(" ");
        }
        return builder.toString();
    }

}
