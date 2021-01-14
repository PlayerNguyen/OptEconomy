package com.github.playernguyen.apis;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.players.OptEconomyPlayer;

import java.sql.SQLException;
import java.util.UUID;

/**
 * The API class of OptEconomy. This class provide all accessible permission to interact with OptEconomy plugin.
 * Access to OptEconomy system maybe causing some issues and this class was created to fix that problem. 
 * 
 */
public class OptEconomyAPI {

    private final OptEconomy plugin;

    /**
     * The constructor for API
     * @param plugin the OptEconomy main class
     */
    public OptEconomyAPI(OptEconomy plugin) {
        this.plugin = plugin;
    }

    /**
     * Get the {@link OptEconomyPlayer}. Whether not found that player. Return new player object with init balance.
     *  otherwise return player.
     * @param uuid the uniquely identify of that player (UUID)
     * @return the player which storage
     */
    public OptEconomyPlayer getPlayer(UUID uuid) throws SQLException {
        return plugin.getPlayerManager().get(uuid);
    }




    /**
     * The result which will return after executed API
     */
    public enum WithdrawResult {
        SUCCESS,
    }

}
