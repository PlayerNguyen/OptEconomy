package com.github.playernguyen.commands.subs;

import com.github.playernguyen.commands.OptEconomyCommand;
import com.github.playernguyen.commands.executors.OptEconomyCommandExecutor;

/**
 * An abstract class of sub-command
 */
public abstract class OptEconomyCommandSub implements OptEconomyCommand {

    private String name;
    private String description;

    private OptEconomyCommandExecutor executor;

}
