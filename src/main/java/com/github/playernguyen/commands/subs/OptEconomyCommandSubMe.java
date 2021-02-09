package com.github.playernguyen.commands.subs;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.commands.OptEconomyCommandParameter;
import com.github.playernguyen.commands.OptEconomyCommandResult;
import com.github.playernguyen.commands.executors.OptEconomyCommandExecutor;
import com.github.playernguyen.localizes.OptEconomyLocalizeTemplate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The sub command me to show personal balance of player.
 */
public class OptEconomyCommandSubMe extends OptEconomyCommandSub {
    public OptEconomyCommandSubMe(OptEconomy instance,
                                  String name,
                                  String description,
                                  OptEconomyCommandExecutor executor,
                                  List<String> aliases) {
        super(instance,
                name,
                description,
                executor,
                aliases
        );

    }

    @Override
    public OptEconomyCommandResult execute(CommandSender sender, List<String> args) {
        // Permissions check
        if (!sender.hasPermission(this.getPermission()))
            return OptEconomyCommandResult.NO_PERMISSION;
        // Not a player ~
        if (!(sender instanceof Player)) {
            sender.sendMessage(
                    getInstance().getLocalizeConfiguration()
                            .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_REQUIRE_PLAYER_SENDER)
                            .toString()
            );
        } else {
            // Response a player ~
            sender.sendMessage(
                    getInstance().getLocalizeConfiguration()
                            .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_ME_RESPONSE)
                            .toStringWithPlaceholder((Player) sender)
            );
        }
        return null;
    }

    @Override
    public List<String> tab(CommandSender sender, List<String> args) {
        // TODO add tabs
        return null;
    }
}
