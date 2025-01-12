package be.machigan.craftplugin.database;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnection extends DatabaseConnection {
    protected final String host;
    protected final Integer port;
    protected final String database;
    protected final String user;
    protected final String password;
    protected final boolean useSsl;

    public MySqlConnection(String host, Integer port, String database, String user, String password, boolean useSsl) {
        super("com.mysql.cj.jdbc.Driver");
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.useSsl = useSsl;
    }

    @Override
    protected @NotNull Connection generateNewConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", this.user);
        properties.setProperty("password", this.password);
        properties.setProperty("useSSL", String.valueOf(this.useSsl));
        return DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                    properties
            );
    }
}
