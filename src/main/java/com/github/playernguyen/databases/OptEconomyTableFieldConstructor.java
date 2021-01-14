package com.github.playernguyen.databases;

public class OptEconomyTableFieldConstructor implements OptEconomyTableField {
    private final String name;
    private final OptEconomyTableFieldType type;
    private final Integer size;
    private final boolean canNull;
    private final String args;

    public OptEconomyTableFieldConstructor(String name, OptEconomyTableFieldType type, Integer size, boolean canNull, String args) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.canNull = canNull;
        this.args = args;
    }

    public OptEconomyTableFieldConstructor(String name, OptEconomyTableFieldType type, Integer size, boolean canNull) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.canNull = canNull;
        this.args = "";
    }

    public OptEconomyTableFieldConstructor(String name, OptEconomyTableFieldType type, Integer size) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.canNull = false;
        this.args = "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <F extends OptEconomyTableFieldType> F getType() {
        return (F) type;
    }

    @Override
    public Integer getSize() {
        return size;
    }

    @Override
    public boolean canNull() {
        return canNull;
    }

    @Override
    public String args() {
        return args;
    }
}
