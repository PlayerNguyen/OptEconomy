package com.github.playernguyen.commands;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.commands.executors.OptEconomyCommandExecutor;
import com.github.playernguyen.manager.OptEconomyManagerList;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;

/**
 * The executor command manager to manage command
 * list which will register while setting up the plugin.
 */
public class OptEconomyCommandExecutorManager extends OptEconomyManagerList<OptEconomyCommandExecutor> {
    private final OptEconomy instance;

    public OptEconomyCommandExecutorManager(OptEconomy instance) {
        super(new ArrayList<>());
        this.instance = instance;
    }

    /**
     * Register the command executor
     *
     * @param executor the executor
     */
    public void register(OptEconomyCommandExecutor executor) {
        this.instance.getDebugger().info("Register executor " + executor.getName());
        // Get plugin command
        PluginCommand pluginCommand = this.instance.getServer().getPluginCommand(executor.getName());
        // Unless found it;
        if (pluginCommand == null)
            throw new NullPointerException("Must register in plugin.yml (" + executor.getName() + ")");
        else pluginCommand.setExecutor(executor);
    }
}
