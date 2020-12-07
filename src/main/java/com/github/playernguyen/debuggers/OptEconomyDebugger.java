package com.github.playernguyen.debuggers;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.Collection;

/**
 * Debugger, who will tell the truth to me (developer)
 */
public class OptEconomyDebugger {

    private final File debuggerActiveFile;

    public OptEconomyDebugger(OptEconomy instance) {
        if (!instance.getDataFolder().exists() && !instance.getDataFolder().mkdir()) {
            throw new IllegalStateException("Cannot find and create file");
        }
        this.debuggerActiveFile = new File(instance.getDataFolder(), OptEconomyConstants.DEBUG_FILE_NAME);
        // If exist, active
        if (this.debuggerActiveFile.exists()) {
            instance.getOptLogger().warn("Enabling the debug mode (in Debug.profile)");
        }
    }

    /**
     * Print the text
     * @param string the string to be printed
     */
    public void logOut(String string) {
        if (debuggerActiveFile.exists()) {
            Bukkit.getConsoleSender().sendMessage(
                    ChatColor.RED + "" + ChatColor.BOLD +
                            "OptDebugger " + ChatColor.RESET
                            + ChatColor.translateAlternateColorCodes('&', string)
            );
        }
    }

    /**
     * Set the info level to debugger and send it to console
     *
     * @param string the string will be appeared to the console
     */
    public void info(String string) {
        this.logOut(ChatColor.GRAY + string);
    }

    /**
     * Set the warning level to debugger and send it to console
     *
     * @param string the string will be appeared
     */
    public void warning(String string) {
        this.logOut(ChatColor.YELLOW + string);
    }

    /**
     * Set the error level and send it to console
     *
     * @param string the string as error content
     */
    public void error(String string) {
        this.logOut(ChatColor.RED + string);
    }

    /**
     * Iterate the {@link Collection}
     *
     * @param collection the collection to print out
     */
    public void iterateOut(Collection<?> collection) {
        this.logOut("================");
        this.logOut(collection.getClass().getName() + ": ");
        for (Object o : collection) {
            this.logOut("   " + o.toString() + " (" + o.getClass().getName() + ")");
        }
        this.logOut("================");
    }

}
