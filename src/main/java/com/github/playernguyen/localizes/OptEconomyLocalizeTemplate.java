package com.github.playernguyen.localizes;

import com.github.playernguyen.configurations.OptEconomyTemplate;

public enum  OptEconomyLocalizeTemplate implements OptEconomyTemplate {

    PREFIX("Prefix", "&7[&cOptEconomy&7] "),

    COMMAND_NO_PERMISSION("Command.NoPermission", "&cYou are not having the permission to do this command"),

    COMMAND_MAIN_COMMAND_DESCRIPTION("Command.OptEconomy.MainDescription", "The command of OptEconomy"),
    COMMAND_PARAMETER_COMMAND("Command.Parameter.Command", "command"),

    COMMAND_PARAM_REQUIRED("Command.Param.Required", "Required"),
    COMMAND_PARAM_NON_REQUIRED("Command.Param.Non-Required", "Non-Required"),

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
