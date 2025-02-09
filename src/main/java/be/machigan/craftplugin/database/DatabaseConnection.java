package be.machigan.craftplugin.database;

import be.machigan.craftplugin.database.executor.AsynchronousSqlExecutor;
import be.machigan.craftplugin.database.executor.SqlExecutor;
import be.machigan.craftplugin.internal.exception.DriverNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public abstract class DatabaseConnection {
    @Getter
    protected Connection connection;
    protected final String driverName;
    protected Boolean isConnected;

    protected abstract @NotNull Connection generateNewConnection() throws SQLException;

    public void connect() throws SQLException {
        if (this.isConnected()) return;
        if (this.isDriverNotFound())
            throw new DriverNotFoundException(this.driverName);
        this.connection = this.generateNewConnection();
        this.isConnected = this.connection.isValid(1);
    }

    public boolean isConnected() {
        return this.isConnected != null && this.isConnected;
    }

    public void disconnect() throws SQLException {
        if (this.connection == null) return;
        this.connection.close();
        this.connection = null;
        this.isConnected = null;
    }

    public void reloadConnection() throws SQLException {
        if (this.isConnected())
            this.disconnect();
        this.connect();
    }

    public SqlExecutor executor() {
        return new SqlExecutor(this.connection);
    }

    public AsynchronousSqlExecutor asynchronousExecutor() {
        return new AsynchronousSqlExecutor(this.connection);
    }

    protected boolean isDriverNotFound() {
        try {
            Class.forName(this.driverName);
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }

    public static boolean hasRemainingAsynchronousTasks() {
        return AsynchronousSqlExecutor.hasRemainingTasks();
    }

    public static void syncRemainingAsynchronousTasks() {
        while (true) {
            if (!hasRemainingAsynchronousTasks()) break;
        }
    }
}
