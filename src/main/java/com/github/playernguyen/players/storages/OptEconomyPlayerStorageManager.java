package com.github.playernguyen.players.storages;

import com.github.playernguyen.databases.OptEconomyDatabases;
import com.github.playernguyen.objects.OptEconomyDouble;
import com.github.playernguyen.players.OptEconomyPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The player manager to manage online or offline player
 */
public abstract class OptEconomyPlayerStorageManager {

    private final OptEconomyDatabases databases;

    public OptEconomyPlayerStorageManager(OptEconomyDatabases databases) {
        this.databases = databases;
    }

    public OptEconomyDatabases getDatabases() {
        return databases;
    }

    /**
     * Get user from uuid
     *
     * @param from the uuid to get
     * @return the user at first rows whether find multiple choice or the user in {@link #find(UUID)}
     *  list whether found single. Null whether the find list length is 0
     * @throws SQLException unless connected to the SQL Server or trouble
     * @see #find(UUID)
     */
    public OptEconomyPlayer get(UUID from) throws SQLException {
        List<OptEconomyPlayer> playerResponseList = this.find(from);
        // Whether found nothing
        if (playerResponseList.size() == 0) {
            return null;
        }
        // Or found, response the first data in array
        return playerResponseList.get(0);
    }

    /**
     * Find user from UUID
     *
     * @param from the uuid to find
     * @return the user list from uuid
     * @throws SQLException unless connected to the SQL Server or trouble
     */
    public List<OptEconomyPlayer> find(UUID from) throws SQLException {
        ResultSet resultSet = this.getDatabases().getUserTable().selectAll("WHERE uuid=?", from);
        List<OptEconomyPlayer> temp = new ArrayList<>();
        while (resultSet.next()) {
            UUID uuid = UUID.fromString(resultSet.getString("uuid"));
            double balance = resultSet.getDouble("balance");
            temp.add(new OptEconomyPlayer(uuid, new OptEconomyDouble(balance), System.currentTimeMillis()));
        }
        return temp;
    }
}
