package com.github.playernguyen.databases.tables;

public abstract class OptEconomyDatabaseTableField<T extends OptEconomyDatabaseFieldType> {
    private final String name;
    private final T field;
    private final boolean isNull;
    private final Integer size;
    private final String customArgument;

    public OptEconomyDatabaseTableField(String name, T field, boolean isNull, Integer size, String customArgument) {
        this.name = name;
        this.field = field;
        this.isNull = isNull;
        this.size = size;
        this.customArgument = customArgument;
    }

    public OptEconomyDatabaseTableField(String name, T field, boolean isNull, Integer size) {
        this.name = name;
        this.field = field;
        this.isNull = isNull;
        this.size = size;
        this.customArgument = null;
    }

    public OptEconomyDatabaseTableField(String name, T field, boolean isNull) {
        this.name = name;
        this.field = field;
        this.isNull = isNull;
        this.size = field.getSize();
        this.customArgument = null;
    }

    public String getName() {
        return name;
    }

    public boolean isNull() {
        return isNull;
    }

    public Integer getSize() {
        return size;
    }

    public String getCustomArgument() {
        return customArgument;
    }

    public T getField() {
        return field;
    }

    /**
     * Declare String returner
     *
     * @return the String which return to SQL builder
     */
    public abstract String toDeclaredString();
}
