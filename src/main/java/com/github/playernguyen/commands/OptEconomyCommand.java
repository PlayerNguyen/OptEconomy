package com.github.playernguyen.commands;

import com.github.playernguyen.OptEconomy;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

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
        return this.getInstance().getDescription().getName().concat(".command.").concat(this.getName());
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
}
