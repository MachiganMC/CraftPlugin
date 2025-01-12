package be.machigan.craftplugin.config.datatype.database;

import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.database.SqliteConnection;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public class ConfigSqliteConnection extends ConfigDatabaseConnection<SqliteConnection> {
    protected final String defaultDatabase;

    public ConfigSqliteConnection(String pathInConfig, String defaultDatabase) {
        super(pathInConfig);
        this.defaultDatabase = defaultDatabase;
    }

    public ConfigSqliteConnection(String pathInConfig) {
        this(pathInConfig, "database");
    }

    @Override
    protected @Nullable SqliteConnection getValueFromConfig() {
        ConfigurationSection section = handler.getConfigFile().getConfigurationSection(this.pathInConfig);
        if (section == null)
            return this.getDefaultAndLog();
        String databaseName = section.getString("Database");
        if (databaseName == null)
            return this.getDefaultConnection();
        return new SqliteConnection(databaseName);
    }

    private SqliteConnection getDefaultAndLog() {
        CraftPlugin.getPlugin().getLogger().log(Level.WARNING, "Unable to find database name");
        return this.getDefaultConnection();
    }

    @Override
    protected @NotNull SqliteConnection getDefaultConnection() {
        return new SqliteConnection(this.defaultDatabase);
    }
}
