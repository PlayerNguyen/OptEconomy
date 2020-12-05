package com.github.playernguyen.loggers;

import com.github.playernguyen.OptEconomyConstants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * This class is not relate to the {@link java.util.logging.Logger}.
 * Custom logger to print in OptEconomy Plugin
 *
 */
public class OptEconomyLogger {

    private static final String PREFIX = OptEconomyConstants.LOGGER_PREFIX;

    /**
     * Function to print a string out. This is private access function
     * @param string the string to print
     */
    private void print(String string) {
        Bukkit.getConsoleSender().sendMessage(
            ChatColor.translateAlternateColorCodes('&', PREFIX + string)
        );
    }

    /**
     * Warning to the logger (console)
     * @param string the string to be warned
     */
    public void warn(String string) {
        this.print(ChatColor.YELLOW + string);
    }

    /**
     * Info level to print
     * @param string the string to announce
     */
    public void info(String string) {
        this.print(ChatColor.GRAY + string);
    }


    /**
     * The critical error level print function
     * @param string the string to served
     */
    public void error(String string) {
        this.print(ChatColor.RED + string);
    }

    /**
     * A normal level print function
     * @param string the string to print out
     */
    public void normal(String string) {
        this.print(ChatColor.RESET + string);
    }


}
