package be.machigan.craftplugin.config.datatype.database;

import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.config.Config;
import be.machigan.craftplugin.database.DatabaseConnection;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class ConfigDatabaseConnection<T extends DatabaseConnection> extends Config<T> {
    public ConfigDatabaseConnection(String pathInConfig) {
        super(pathInConfig, null, null);
    }

    protected abstract @NotNull T getDefaultConnection();

    public boolean isConnected() {
        return this.value != null && this.value.isConnected();
    }

    public Connection getConnection() {
        return this.value.getConnection();
    }

    @Override
    public void reload() {
        try {
            if (this.isConnected())
                this.value.disconnect();
            super.reload();
            this.value.connect();
        } catch (SQLException e) {
            CraftPlugin.getPlugin().getLogger().log(Level.WARNING, e.getMessage());
        }
    }
}
