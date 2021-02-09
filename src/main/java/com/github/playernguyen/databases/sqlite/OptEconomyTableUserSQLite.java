package com.github.playernguyen.databases.sqlite;

import com.github.playernguyen.databases.OptEconomyDatabase;
import com.github.playernguyen.databases.OptEconomyTableAbstract;
import com.github.playernguyen.databases.OptEconomyTableFieldConstructor;
import com.github.playernguyen.objects.OptEconomyDouble;
import com.github.playernguyen.players.OptEconomyPlayer;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OptEconomyTableUserSQLite extends OptEconomyTableAbstract<OptEconomyPlayer> {

    public OptEconomyTableUserSQLite(String name, OptEconomyDatabase database) throws SQLException {
        super(name, database);
    }

    @Override
    protected void loadField() {
        // Register field
        this.addField(new OptEconomyTableFieldConstructor("id", OptEconomyTableFieldTypeSQLite.INTEGER, null,
                false, "PRIMARY KEY AUTOINCREMENT")
        );
        this.addField(new OptEconomyTableFieldConstructor("username", OptEconomyTableFieldTypeSQLite.VARCHAR, null));
        this.addField(new OptEconomyTableFieldConstructor("uuid", OptEconomyTableFieldTypeSQLite.VARCHAR, null));
        this.addField(new OptEconomyTableFieldConstructor("balance", OptEconomyTableFieldTypeSQLite.VARCHAR, null));
        // Set the primary key to id
        this.setPrimaryKey(null);
    }

    @Override
    public List<OptEconomyPlayer> adapt(ResultSet response) throws SQLException {
        List<OptEconomyPlayer> players = new ArrayList<>();
        while (response.next()) {
            players.add(new OptEconomyPlayer(
                    UUID.fromString(response.getString("uuid")),
                    new OptEconomyDouble(response.getDouble("balance"))
            ));
        }
        return players;
    }

    @Override
    public Map<String, Object> redapt(OptEconomyPlayer object) {
        Map<String, Object> navigator = new HashMap<>();
        // Insert information
        navigator.put("username", Bukkit.getOfflinePlayer(object.getUniqueId()).getName());
        navigator.put("uuid", object.getUniqueId().toString());
        navigator.put("balance", object.getBalance().toDouble());
        // End
        return navigator;
    }
}
