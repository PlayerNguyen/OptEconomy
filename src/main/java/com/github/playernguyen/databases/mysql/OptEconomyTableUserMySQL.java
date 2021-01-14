package com.github.playernguyen.databases.mysql;

import com.github.playernguyen.databases.OptEconomyDatabase;
import com.github.playernguyen.databases.OptEconomyObject;
import com.github.playernguyen.databases.OptEconomyTableAbstract;
import com.github.playernguyen.databases.OptEconomyTableFieldConstructor;
import com.github.playernguyen.objects.OptEconomyDouble;
import com.github.playernguyen.players.OptEconomyPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class OptEconomyTableUserMySQL extends OptEconomyTableAbstract<OptEconomyPlayer> {

    public OptEconomyTableUserMySQL(String name, OptEconomyDatabase database) throws SQLException {
        super(name, database);
    }

    @Override
    protected void loadField() {
        // Register field
        this.addField(new OptEconomyTableFieldConstructor("id", OptEconomyTableFieldTypeMySQL.INTEGER, 32));
        this.addField(new OptEconomyTableFieldConstructor("username", OptEconomyTableFieldTypeMySQL.VARCHAR, 255));
        this.addField(new OptEconomyTableFieldConstructor("uuid", OptEconomyTableFieldTypeMySQL.VARCHAR, 255));
        this.addField(new OptEconomyTableFieldConstructor("balance", OptEconomyTableFieldTypeMySQL.VARCHAR, 32));
        // Set the primary key to id
        this.setPrimaryKey("id");
    }

    /**
     * @see OptEconomyObject
     */
    @Override
    public List<OptEconomyPlayer> adapt(ResultSet response) throws SQLException {
        List<OptEconomyPlayer> responseList = new ArrayList<>();
        // Iterate and push into the list
        while (response.next()) {
            // Create new object and add into list
            UUID uuid = UUID.fromString(response.getString("uuid"));
            double money = response.getDouble("value");

            OptEconomyPlayer player = new OptEconomyPlayer(uuid,
                    new OptEconomyDouble(money),
                    System.currentTimeMillis()
            );
            // Add into the response list
            responseList.add(player);
        }
        return responseList;
    }
}
