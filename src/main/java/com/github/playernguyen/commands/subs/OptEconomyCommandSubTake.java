package com.github.playernguyen.commands.subs;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.commands.OptEconomyCommandResult;
import com.github.playernguyen.commands.executors.OptEconomyCommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class OptEconomyCommandSubTake extends OptEconomyCommandSub {

    public OptEconomyCommandSubTake(OptEconomy instance,
                                    String name,
                                    String description,
                                    OptEconomyCommandExecutor executor,
                                    List<String> aliases) {
        super(instance, name, description, executor, aliases);
        // TODO add parameters
    }

    @Override
    public OptEconomyCommandResult execute(CommandSender sender, List<String> args) {
        // TODO add methods
        return null;
    }

    @Override
    public List<String> tab(CommandSender sender, List<String> args) {
        // TODO add tabs
        return null;
    }
}
