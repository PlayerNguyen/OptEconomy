package com.github.playernguyen.commands.subs;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.apis.OptEconomyAPI;
import com.github.playernguyen.commands.OptEconomyCommandParameter;
import com.github.playernguyen.commands.OptEconomyCommandResult;
import com.github.playernguyen.commands.executors.OptEconomyCommandExecutor;
import com.github.playernguyen.localizes.OptEconomyLocalizeTemplate;
import com.github.playernguyen.loggers.OptEconomyExceptionCatcher;
import com.github.playernguyen.settings.OptEconomySettingTemplate;
import com.github.playernguyen.utils.OptEconomyNumberUtilities;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

/**
 * Give command of OptEconomy plugin. This command giving balance to player
 */
public class OptEconomyCommandSubGive extends OptEconomyCommandSub {
    public OptEconomyCommandSubGive(OptEconomy instance, String name, String description, OptEconomyCommandExecutor executor, List<String> aliases) {
        super(instance, name, description, executor, aliases);
        // - - - Parameters 'register - - - //
        this.getParameters().add(new OptEconomyCommandParameter(
                instance.getLocalizeConfiguration().raw(OptEconomyLocalizeTemplate.COMMAND_PARAM_TARGET).toString(),
                true
        ));
        this.getParameters().add(new OptEconomyCommandParameter(
                instance.getLocalizeConfiguration().raw(OptEconomyLocalizeTemplate.COMMAND_PARAM_AMOUNT).toString(),
                true
        ));
    }

    @Override
    public OptEconomyCommandResult execute(CommandSender sender, List<String> args) {
        // Invalid arguments; the args must not be null
        if (args.size() != 2) {
            sender.sendMessage(getInstance().getLocalizeConfiguration()
                    .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_RESPONSE_INVALID_ARGUMENTS).toString()
            );
            return null;
        }
        // Pre-check the arguments
        String playerName = args.get(0);
        Player player = Bukkit.getServer().getPlayerExact(playerName);
        // Whether not found this player
        if (player == null) {
            sender.sendMessage(getInstance().getLocalizeConfiguration()
                    .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_GIVE_RESPONSE_PLAYER_NOT_FOUND)
                    .replace("%player%", playerName)
                    .toString()
            );
            return null;
        }
        String stringAmount = args.get(1);
        try {
            double amount = Double.parseDouble(stringAmount);
            // Whether using integer
            if (getInstance().getSettingConfiguration().get(OptEconomySettingTemplate.GENERAL_USING_INTEGER)) {
                if (!OptEconomyNumberUtilities.isInteger(amount)) {
                    sender.sendMessage(getInstance().getLocalizeConfiguration()
                        .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_GIVE_RESPONSE_USE_INTEGER).toString()
                    );
                    return null;
                }
            }
            // Whether lower than or equal 0
            if (amount <= 0) {
                sender.sendMessage(
                        getInstance().getLocalizeConfiguration()
                            .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_RESPONSE_INVALID_NUMBER_VALUE)
                            .toString()
                );
                return null;
            }
            // Other conditions

            // Change balance
            boolean transactState = new OptEconomyAPI(getInstance()).transact(player.getUniqueId(), amount);
            // Whether not changed (or not updated)
            if (!transactState) {
                sender.sendMessage(
                        getInstance().getLocalizeConfiguration()
                                .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_GIVE_RESPONSE_FAILED_TRANSACT)
                                .replace("%player%", playerName)
                                .toString()
                );
                return OptEconomyCommandResult.NULL;
            }
            // Otherwise
            sender.sendMessage(
                    getInstance().getLocalizeConfiguration()
                            .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_GIVE_RESPONSE_SUCCEED_TRANSACT)
                            .replace("%deposit%", stringAmount)
                            .replace("%player%", playerName)
                            .toString()
            );
        } catch (NumberFormatException e) {
            sender.sendMessage(getInstance().getLocalizeConfiguration()
                .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_RESPONSE_INVALID_NUMBER_FORMAT).toString()
            );
            // throw the debug
            // OptEconomyExceptionCatcher.stackTrace(e);
        } catch (SQLException e) {
            sender.sendMessage(getInstance().getLocalizeConfiguration()
                    .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_RESPONSE_SQL_ERROR).toString()
            );
            // throw the debug
            OptEconomyExceptionCatcher.stackTrace(e);
        }
        return null;
    }

    @Override
    public List<String> tab(CommandSender sender, List<String> args) {
        // TODO add tabs
        return null;
    }
}
