package be.machigan.craftplugin.database;

import java.sql.SQLException;

public interface ParameterSqlTask<T> {
    void run(T t) throws SQLException;
}
