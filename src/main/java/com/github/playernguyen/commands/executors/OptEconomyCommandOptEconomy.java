package com.github.playernguyen.commands.executors;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.commands.OptEconomyCommandParameter;
import com.github.playernguyen.commands.OptEconomyCommandResult;
import com.github.playernguyen.localizes.OptEconomyLocalizeTemplate;
import org.bukkit.command.CommandSender;

import java.util.List;

public class OptEconomyCommandOptEconomy extends OptEconomyCommandExecutor {


    public OptEconomyCommandOptEconomy(OptEconomy instance) {
        super(instance,
                OptEconomyConstants.COMMAND_OPTECONOMY,
                instance.getLocalizeConfiguration().color(OptEconomyLocalizeTemplate.COMMAND_MAIN_COMMAND_DESCRIPTION)
                    .toString()
        );
        this.getParameters().add(new OptEconomyCommandParameter(
                    instance.getLocalizeConfiguration().raw(OptEconomyLocalizeTemplate.COMMAND_PARAMETER_COMMAND)
                            .toString(),
                    true
                )
        );
    }

    @Override
    public OptEconomyCommandResult execute(CommandSender sender, List<String> args) {

        // No param

        return null;
    }

    @Override
    public List<String> tab(CommandSender sender, List<String> args) {



        return null;
    }
}
