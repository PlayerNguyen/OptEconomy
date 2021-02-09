package com.github.playernguyen.commands;

/**
 * The parameter class of command.
 */
public class OptEconomyCommandParameter {
    private final String name;
    private final boolean required;

    public OptEconomyCommandParameter(String name, boolean required) {
        this.name = name;
        this.required = required;
    }

    /**
     * The name of parameter
     *
     * @return the parameter name
     */
    public String getName() {
        return name;
    }

    /**
     * @return whether is required or not
     */
    public boolean isRequired() {
        return required;
    }
}
