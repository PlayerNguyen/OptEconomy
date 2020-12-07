package com.github.playernguyen.localizes;

import com.github.playernguyen.configurations.OptEconomyTemplate;

public enum  OptEconomyLocalizeTemplate implements OptEconomyTemplate {

    PREFIX("Prefix", "&7[&cOptEconomy&7] "),

    COMMAND_NO_PERMISSION("Command.NoPermission", "&cYou are not having the permission to do this command")

    ;

    private final String path;
    private final Object declare;

    OptEconomyLocalizeTemplate(String path, Object declare) {
        this.path = path;
        this.declare = declare;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public Object declare() {
        return declare;
    }
}
