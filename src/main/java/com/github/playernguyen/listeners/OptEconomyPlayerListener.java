package com.github.playernguyen.listeners;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.loggers.OptEconomyExceptionCatcher;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

/**
 * A player listener class to listen to the event
 */
public class OptEconomyPlayerListener extends OptEconomyAbstractListener {

    /**
     * Constructor of this class
     *
     * @param instance the instance of OptEconomy
     */
    public OptEconomyPlayerListener(OptEconomy instance) {
        super(instance);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Put player into cache
        this.getInstance().getDebugger().info(String.format(
                "Calling onJoin (%s)",
                this.getClass().getSimpleName()
        ));
        this.getInstance().getOptLogger().info(
                String.format("Getting player data of player %s",
                event.getPlayer().getUniqueId())
        );
        try {
            this.getInstance().getPlayerManager().get(event.getPlayer().getUniqueId());
        } catch (SQLException e) {
            OptEconomyExceptionCatcher.stackTrace(e);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // Whether quit, remove the player from cache and save it to database
        this.getInstance().getDebugger().info(String.format(
                "Calling onQuit (%s)",
                this.getClass().getSimpleName()
        ));
        this.getInstance().getOptLogger().info(
                String.format("Saving player data of player %s",
                event.getPlayer().getUniqueId())
        );
        try {
            this.getInstance().getPlayerManager().removePlayer(event.getPlayer().getUniqueId());
        } catch (SQLException e) {
            OptEconomyExceptionCatcher.stackTrace(e);
        }
    }
}
