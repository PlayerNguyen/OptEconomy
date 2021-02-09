package com.github.playernguyen.databases.sqlite;

import com.github.playernguyen.databases.OptEconomyTableFieldType;

public enum  OptEconomyTableFieldTypeSQLite implements OptEconomyTableFieldType {

    VARCHAR("VARCHAR"),
    INTEGER("INTEGER")

    ;

    private final String declaration;

    OptEconomyTableFieldTypeSQLite(String declaration) {
        this.declaration = declaration;
    }

    @Override
    public String getDeclaration() {
        return declaration;
    }
}
