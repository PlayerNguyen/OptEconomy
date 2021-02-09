package com.github.playernguyen.databases;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class OptEconomyTableAbstract<T extends OptEconomyObject> implements OptEconomyTable<T> {
    private final String name;
    private final List<OptEconomyTableField> fieldSet = new ArrayList<>();
    private final OptEconomyDatabase database;
    private String primaryKey = null;

    public OptEconomyTableAbstract(String name, OptEconomyDatabase database) throws SQLException {
        this.name = name;
        this.database = database;
        // Load field and then create
        loadField();
        // Process and init
        if (fieldSet.size() == 0) {
            throw new IllegalStateException("Empty field table detect.");
        }
        database.getPlugin().getDebugger().info("Create table if not exist <OptEconomyTableAbstract->?>");
        this.createTableNotExist();
    }

    @Override
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public String getPrimaryKey() {
        return primaryKey;
    }

    protected abstract void loadField();

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<OptEconomyTableField> getFieldSet() {
        return fieldSet;
    }

    @Override
    public OptEconomyDatabase getDatabase() {
        return database;
    }

    /**
     * Create the table whether not exist.
     *
     * @throws SQLException cannot execute the SQL
     */
    private void createTableNotExist() throws SQLException {
        // Preconditions.checkNotNull(getPrimaryKey(),
        //        "The primary key must not be null. Set the primary key in loadField()");
        this.getDatabase().ready(String.format("CREATE TABLE IF NOT EXISTS %s (%s)",
                this.name,
                createQueryParameter()
        ), null).execute();
    }
}
