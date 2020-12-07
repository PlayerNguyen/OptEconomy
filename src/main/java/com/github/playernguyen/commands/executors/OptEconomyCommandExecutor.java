package com.github.playernguyen.commands.executors;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.commands.OptEconomyCommand;
import com.github.playernguyen.commands.OptEconomyCommandParameter;
import com.github.playernguyen.commands.subs.OptEconomyCommandSub;
import com.github.playernguyen.localizes.OptEconomyLocalizeTemplate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * An abstract class command executor
 */
public abstract class OptEconomyCommandExecutor implements OptEconomyCommand, TabExecutor {

    private final OptEconomy instance;
    private final String name;
    private final String description;
    private final List<OptEconomyCommandParameter> parameters = new ArrayList<>();
    private final Set<OptEconomyCommandSub> subCommandList = new TreeSet<>();

    public OptEconomyCommandExecutor(OptEconomy instance, String name, String description) {
        this.instance = instance;
        this.name = name;
        this.description = description;
    }

    /**
     * Sub-command set
     *
     * @return the sub command
     */
    public Set<OptEconomyCommandSub> getSubCommandList() {
        return subCommandList;
    }

    @Override
    public OptEconomy getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<OptEconomyCommandParameter> getParameters() {
        return parameters;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        // Whether sender have no permission
        if (!sender.hasPermission(this.getPermission())) {
            sender.sendMessage(
                    instance.getLocalizeConfiguration().prefixedColour(OptEconomyLocalizeTemplate.COMMAND_NO_PERMISSION)
                        .toString()
            );
        }
        return true;
    }
}
