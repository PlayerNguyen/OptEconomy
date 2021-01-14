package com.github.playernguyen.commands.subs;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.commands.OptEconomyCommand;
import com.github.playernguyen.commands.OptEconomyCommandParameter;
import com.github.playernguyen.commands.executors.OptEconomyCommandExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class of sub-command
 */
public abstract class OptEconomyCommandSub implements OptEconomyCommand {

    private final OptEconomy instance;
    private final String name;
    private final String description;
    private final List<OptEconomyCommandParameter> parameters;
    private final OptEconomyCommandExecutor executor;

    public OptEconomyCommandSub(OptEconomy instance,
                                String name,
                                String description,
                                OptEconomyCommandExecutor executor) {
        this.instance = instance;
        this.name = name;
        this.description = description;
        this.parameters = new ArrayList<>();
        this.executor = executor;
    }

    @Override
    public OptEconomy getInstance() {
        return instance;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<OptEconomyCommandParameter> getParameters() {
        return parameters;
    }

    /**
     * The main executor to depend on
     *
     * @return the main executor
     */
    public OptEconomyCommandExecutor getExecutor() {
        return executor;
    }

    @Override
    public String getPermission() {
        return this.executor.getPermission().concat(this.getName());
    }
}
