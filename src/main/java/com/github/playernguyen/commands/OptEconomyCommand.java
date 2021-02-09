package com.github.playernguyen.commands;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.localizes.OptEconomyLocalizeTemplate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * Command interface is an interface represent OptEconomy command (executor, sub-command). <br>
 * This class will include the permission and the instance of OptEconomy.
 */
public interface OptEconomyCommand extends Comparable<OptEconomyCommand> {
    /**
     * The name of command or even sub-command
     *
     * @return name of this command.
     */
    String getName();

    /**
     * The description of this command.
     * This description will be displayed when {@link org.bukkit.command.CommandSender} call
     *
     * @return the description of this command
     */
    String getDescription();

    /**
     * The parameter list to describe about the command
     *
     * @return the parameter list of this command
     */
    List<OptEconomyCommandParameter> getParameters();

    /**
     * A execute function of this command
     *
     * @param sender the sender who call this command
     * @param args   the arguments which the sender provides
     * @return result of this execute
     */
    OptEconomyCommandResult execute(CommandSender sender, List<String> args);

    /**
     * A tab-completion of this command
     *
     * @param sender the sender who tab
     * @param args   the argument which the sender provides
     * @return response as a List
     */
    List<String> tab(CommandSender sender, List<String> args);

    /**
     * Get instance of OptEconomy
     *
     * @return the instance of OptEconomy
     */
    OptEconomy getInstance();

    /**
     * A permission node which provide to server and represent for player to access this command
     *
     * @return a permission node (as {@link String}) of this command
     */
    default String getPermission() {
        return this.getInstance().getDescription().getName().toLowerCase().concat(".command.").concat(this.getName())
                .trim();
    }

    /**
     * Inherit from {@link Comparable} class.
     * This class comparing each the name of this command
     *
     * @param o the object to be compare
     * @return the int value (compare level)
     * @see Comparable
     */
    @Override
    default int compareTo(@NotNull OptEconomyCommand o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }

    /**
     * Build a parameter as string query
     *
     * @return the query of parameter
     */
    default String buildParameter() {
        // Creating new builder
        StringBuilder builder = new StringBuilder();
        // Putting bricks on it
        for (OptEconomyCommandParameter parameter : getParameters()) {
            if (parameter.isRequired()) {
                builder.append("<").append(parameter.getName()).append(">");
            } else {
                builder.append("[").append(parameter.getName()).append("]");
            }
            builder.append(" ");
        }
        // Painting and completing it
        return builder.toString();
    }

    /**
     * Send a help form to sender
     *
     * @param sender   the sender to send
     * @param commands a list of command
     */
    default void sendHelpForm(CommandSender sender, Collection<OptEconomyCommand> commands) {
        sender.sendMessage(ChatColor.GRAY + "-------------------------------");
        sender.sendMessage(ChatColor.BOLD + ""
                + ChatColor.GRAY + "*  " + "" + ChatColor.GOLD + "OptEconomy by " + ChatColor.AQUA
                + "Player_Nguyen ");
        sender.sendMessage(ChatColor.GRAY + "* ");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(
                "&7* &c<~> %s - &b[~] %s",
                this.getInstance().getLocalizeConfiguration().raw(OptEconomyLocalizeTemplate.COMMAND_PARAM_REQUIRED),
                this.getInstance().getLocalizeConfiguration().raw(OptEconomyLocalizeTemplate.COMMAND_PARAM_NON_REQUIRED)
        )));
        sender.sendMessage(ChatColor.GRAY + "-------------------------------");

        // Iterate the command list and send guide
        for (OptEconomyCommand command : commands) {
            sender.sendMessage(String.format(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            "&6/p &3%s &7%s: &7%s"
                    ),
                    command.getName().trim(),
                    command.buildParameter().trim(),
                    command.getDescription().trim()
            ));
        }
    }
}
