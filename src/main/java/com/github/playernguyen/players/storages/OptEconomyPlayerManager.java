package com.github.playernguyen.players.storages;

import com.github.playernguyen.databases.OptEconomyDatabase;
import com.github.playernguyen.players.OptEconomyPlayer;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Player manager class is responsibility to provide and modify database data on server.
 */
public interface OptEconomyPlayerManager {

    /**
     * The database for manager to access.
     *
     * @return the {@link OptEconomyDatabase} class
     */
    OptEconomyDatabase getDatabase();

    /**
     * Request and receive data from database. Then do pre-process in order to filter the data and return it.
     *
     * @param uniqueId the unique id of that player.
     * @return the received with pre-processed data. Null whether system not found the player.
     * @throws SQLException SQL errors such as connections, wrong queries,...
     */
    @Nullable OptEconomyPlayer getPlayer(UUID uniqueId) throws SQLException;

    /**
     * Find that player are existed or not
     *
     * @param uniqueId the unique id of that player.
     * @return true whether found that player, false otherwise
     * @throws SQLException SQL errors such as connections, wrong queries,...
     */
    boolean hasPlayer(UUID uniqueId) throws SQLException;

    /**
     * Update the player with new balance value.
     *
     * @param player the player to update.
     * @return true whether the player was updated on server, false otherwise.
     * @throws SQLException SQL errors such as connections, wrong queries,...
     * @throws NullPointerException player not found.
     */
    boolean updatePlayer(OptEconomyPlayer player) throws SQLException;

    /**
     * Create new player and insert onto database server.
     *
     * @param player the player to create
     * @return status of query, true whether created, false otherwise.
     * @throws SQLException SQL errors such as connections, wrong queries,...
     */
    boolean createPlayer(OptEconomyPlayer player) throws SQLException;

}
