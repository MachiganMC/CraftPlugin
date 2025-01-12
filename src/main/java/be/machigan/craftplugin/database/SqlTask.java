package be.machigan.craftplugin.database;

import java.sql.SQLException;

public interface SqlTask {
    void run() throws SQLException;
}
