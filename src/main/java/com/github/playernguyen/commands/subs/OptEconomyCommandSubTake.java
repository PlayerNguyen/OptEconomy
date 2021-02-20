package com.github.playernguyen.commands.subs;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.commands.OptEconomyCommandParameter;
import com.github.playernguyen.commands.OptEconomyCommandResult;
import com.github.playernguyen.commands.executors.OptEconomyCommandExecutor;
import com.github.playernguyen.localizes.OptEconomyLocalizeTemplate;
import com.github.playernguyen.loggers.OptEconomyExceptionCatcher;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

public class OptEconomyCommandSubTake extends OptEconomyCommandSub {

    public OptEconomyCommandSubTake(OptEconomy instance, String name, String description, OptEconomyCommandExecutor executor, List<String> aliases) {
        super(instance, name, description, executor, aliases);
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
        // Whether invalid parameters
        if (args.size() < 2) {
            sender.sendMessage(
                    getInstance().getLocalizeConfiguration()
                            .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_RESPONSE_INVALID_ARGUMENTS)
                            .toString()
            );
            return null;
        }

        // Check target
        String _target = args.get(0);
        Player player = Bukkit.getPlayer(_target);
        if (player == null) {
            sender.sendMessage(
                    getInstance().getLocalizeConfiguration()
                            .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_TAKE_RESPONSE_PLAYER_NOT_FOUND)
                            .replace("%player%", _target)
                            .toString()
            );
            return null;
        }

        String _amount = args.get(1);
        try {
            double amount = Double.parseDouble(_amount);
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
            // TODO: filter add

            // Withdraw balance
            if (OptEconomy.api().withdraw(player.getUniqueId(), amount)) {
                sender.sendMessage(
                        getInstance().getLocalizeConfiguration()
                            .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_TAKE_RESPONSE_TOOK)
                            .replace("%point%", _amount)
                            .replace("%player%", _target)
                            .toString()
                );
            } else {
                sender.sendMessage(
                        getInstance().getLocalizeConfiguration()
                                .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_TAKE_RESPONSE_UNKNOWN_ERROR)
                                .replace("%point%", _amount)
                                .replace("%player%", _target)
                                .toString()
                );
            }

        } catch (NumberFormatException e) {
            sender.sendMessage(
                    getInstance().getLocalizeConfiguration()
                        .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_RESPONSE_INVALID_NUMBER_FORMAT)
                        .toString()
            );
            // OptEconomyExceptionCatcher.stackTrace(e);
        } catch (SQLException e) {
            sender.sendMessage(
                    getInstance().getLocalizeConfiguration()
                        .prefixedColor(OptEconomyLocalizeTemplate.COMMAND_RESPONSE_SQL_ERROR)
                        .toString()
            );
            OptEconomyExceptionCatcher.stackTrace(e);
        }
        return null;
    }

    @Override
    public List<String> tab(CommandSender sender, List<String> args) {
        return null;
    }
}
