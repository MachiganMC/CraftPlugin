package be.machigan.craftplugin.config.datatype.database;

import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.database.MySqlConnection;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public class ConfigMySqlConnection extends ConfigDatabaseConnection<MySqlConnection> {
    protected final String defaultHost;
    protected final int defaultPort;
    protected final String defaultDatabase;
    protected final String defaultUser;
    protected final String defaultPassword;
    protected final boolean defaultUseSsl;

    public ConfigMySqlConnection(
            String pathInConfig,
            String defaultHost,
            int defaultPort,
            String defaultDatabase,
            String defaultUser,
            String defaultPassword,
            boolean defaultUseSsl
    ) {
        super(pathInConfig);
        this.defaultHost = defaultHost;
        this.defaultPort = defaultPort;
        this.defaultDatabase = defaultDatabase;
        this.defaultUser = defaultUser;
        this.defaultPassword = defaultPassword;
        this.defaultUseSsl = defaultUseSsl;
    }

    public ConfigMySqlConnection(String pathInConfig) {
        this(pathInConfig, "localhost", 3306, "minecraft", "root", "root", false);
    }

    @Override
    protected @Nullable MySqlConnection getValueFromConfig() {
        ConfigurationSection section = handler.getConfigFile().getConfigurationSection(this.pathInConfig);
        if (section == null) {
            CraftPlugin.getPlugin().getLogger().log(Level.WARNING, "Unable to find database credentials, set to defaults");
            return this.getDefaultConnection();
        }
        return new MySqlConnection(
                this.getOrDefault(section, "Host", this.defaultHost),
                section.getInt("Port"),
                this.getOrDefault(section,"Database", this.defaultDatabase),
                this.getOrDefault(section, "User", this.defaultUser),
                this.getOrDefault(section, "Password", this.defaultPassword),
                section.getBoolean("UseSSL")
        );
    }

    protected @NotNull String getOrDefault(ConfigurationSection section, String key, String defaultValue) {
        String value = section.getString(key);
        if (value != null)
            return value;
        CraftPlugin.getPlugin().getLogger().log(Level.WARNING, "Unable to find " + key + " set to \"" + defaultValue + "\"");
        return defaultValue;
    }

    @Override
    protected @NotNull MySqlConnection getDefaultConnection() {
        return new MySqlConnection(
                this.defaultHost,
                this.defaultPort,
                this.defaultDatabase,
                this.defaultUser,
                this.defaultPassword,
                this.defaultUseSsl
        );
    }
}
