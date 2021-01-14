package com.github.playernguyen.databases.mysql;

import com.github.playernguyen.databases.OptEconomyTableFieldType;

public enum OptEconomyTableFieldTypeMySQL implements OptEconomyTableFieldType {
    VARCHAR("VARCHAR"),
    INTEGER("INTEGER"),
    DOUBLE("DOUBLE")
    ;

    private final String declaration;

    OptEconomyTableFieldTypeMySQL(String declaration) {
        this.declaration = declaration;
    }

    @Override
    public String getDeclaration() {
        return declaration;
    }
}
