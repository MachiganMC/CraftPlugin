package be.machigan.craftplugin.database;

import be.machigan.craftplugin.CraftPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection extends DatabaseConnection {
    private final String databaseName;

    public SqliteConnection(String databaseName) {
        super("org.sqlite.JDBC");
        this.databaseName = databaseName;
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected @NotNull Connection generateNewConnection() throws SQLException {
        File dataFile = new File(CraftPlugin.getPlugin().getDataFolder(), this.databaseName + ".db");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new SQLException("Unable to create database " + this.databaseName);
            }
        }
        return DriverManager.getConnection("jdbc:sqlite:" + dataFile);
    }
}
