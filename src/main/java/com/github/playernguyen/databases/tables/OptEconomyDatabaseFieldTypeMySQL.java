package com.github.playernguyen.databases.tables;

public enum OptEconomyDatabaseFieldTypeMySQL implements OptEconomyDatabaseFieldType {

    VARCHAR("VARCHAR", 255),
    INTEGER("INTEGER", 255),

    ;

    private final String name;
    private final Integer size;

    OptEconomyDatabaseFieldTypeMySQL(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getSize() {
        return size;
    }
}
