package com.github.playernguyen.commands.executors;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.commands.OptEconomyCommandParameter;
import com.github.playernguyen.commands.OptEconomyCommandResult;
import com.github.playernguyen.commands.subs.OptEconomyCommandSub;
import com.github.playernguyen.commands.subs.OptEconomyCommandSubGive;
import com.github.playernguyen.commands.subs.OptEconomyCommandSubMe;
import com.github.playernguyen.localizes.OptEconomyLocalizeTemplate;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The main executor for OptEconomy
 */
public class OptEconomyCommandOptEconomy extends OptEconomyCommandExecutor {
    public OptEconomyCommandOptEconomy(OptEconomy instance) {
        super(instance,
                OptEconomyConstants.COMMAND_OPTECONOMY,
                instance.getLocalizeConfiguration().color(OptEconomyLocalizeTemplate.COMMAND_MAIN_COMMAND_DESCRIPTION)
                        .toString()
        );
        // Parameters add
        this.getParameters().add(new OptEconomyCommandParameter(
                        instance.getLocalizeConfiguration().raw(OptEconomyLocalizeTemplate.COMMAND_PARAMETER_COMMAND)
                                .toString(),
                        true
                )
        );
        // points me
        this.registerSubCommand(
                new OptEconomyCommandSubMe(
                        instance,
                        "me",
                        instance.getLocalizeConfiguration().raw(OptEconomyLocalizeTemplate.COMMAND_ME_DESCRIPTION)
                                .toString(),
                        this,
                        Collections.singletonList("m")
                )
        );
        // points give
        this.registerSubCommand(new OptEconomyCommandSubGive(
                instance,
                "give",
                instance.getLocalizeConfiguration().raw(OptEconomyLocalizeTemplate.COMMAND_GIVE_DESCRIPTION)
                        .toString(),
                this,
                Collections.singletonList("g")
        ));
    }

    @Override
    public OptEconomyCommandResult execute(CommandSender sender, List<String> args) {
        // Without aliases
        if (args.size() == 0) {
            sendHelpForm(sender, new ArrayList<>(this.getSubCommandList()));
            return null;
        }
        // Find sub-command
        //  *using stream api for better algorithm*
        OptEconomyCommandSub subCommand = this.getSubCommandList()
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(args.get(0)) ||
                        e.getAliases().contains(args.get(0))
                )
                .findAny()
                .orElse(null);
        // Whether sub command not found
        if (subCommand == null) {
            sender.sendMessage(
                    getInstance()
                            .getLocalizeConfiguration()
                            .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_SUB_COMMAND_NOT_FOUND)
                            .toString()
            );
            return null;
        }
        // Whether player not having permission
        if (!sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(
                    getInstance()
                            .getLocalizeConfiguration()
                            .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_NO_PERMISSION)
                            .toString()
            );
            return null;
        }
        // Return the sub command execute
        return subCommand.execute(sender, args.subList(0, args.size() - 1));
    }

    @Override
    public List<String> tab(CommandSender sender, List<String> args) {
        return null;
    }
}
