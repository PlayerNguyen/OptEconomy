package com.github.playernguyen.databases.tables;

public class OptEconomyDatabaseTableFieldMySQL extends OptEconomyDatabaseTableField<OptEconomyDatabaseFieldTypeMySQL> {


    public OptEconomyDatabaseTableFieldMySQL(String name,
                                             OptEconomyDatabaseFieldTypeMySQL field,
                                             boolean isNull,
                                             Integer size,
                                             String customArgument) {
        super(name, field, isNull, size, customArgument);
    }

    public OptEconomyDatabaseTableFieldMySQL(String name,
                                             OptEconomyDatabaseFieldTypeMySQL field,
                                             boolean isNull,
                                             Integer size) {
        super(name, field, isNull, size);
    }

    public OptEconomyDatabaseTableFieldMySQL(String name,
                                             OptEconomyDatabaseFieldTypeMySQL field,
                                             boolean isNull) {
        super(name, field, isNull);
    }

    @Override
    public String toDeclaredString() {
        StringBuilder builder = new StringBuilder();

        // name fieldName(size)
        builder.append(getName()).append(" ");
        builder.append(getField().getName()).append(" ");

        // Whether the size not be null
        Integer size = (this.getSize() == null) ? this.getField().getSize() : this.getSize();
        builder.append("(").append(size).append(") ");

        // Whether not null
        if (!isNull()) {
            builder.append("NOT NULL");
        }

        return builder.toString();
    }
}
