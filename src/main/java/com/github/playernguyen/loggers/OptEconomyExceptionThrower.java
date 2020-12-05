package com.github.playernguyen.loggers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

/**
 * The class to catch the exception and then throw it more colorful
 */
public class OptEconomyExceptionThrower {

    public static void throwException(Exception exception) {
        ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();

        // Send it more colorful
        consoleSender.sendMessage(ChatColor.RED + "===== [Exception catching] =====");
        consoleSender.sendMessage(ChatColor.YELLOW + exception.getClass().getName() + ": " + ChatColor.RED
                + exception.getMessage());
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            consoleSender.sendMessage(ChatColor.RED + "  ~  " + stackTraceElement.toString());
        }
    }

}
