package com.github.playernguyen.localizes;

import com.github.playernguyen.configurations.OptEconomyTemplate;

public enum  OptEconomyLocalizeTemplate implements OptEconomyTemplate {

    PREFIX("Prefix", "&7[&cOptEconomy&7] ")


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
