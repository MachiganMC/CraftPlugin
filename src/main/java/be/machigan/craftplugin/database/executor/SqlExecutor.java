package be.machigan.craftplugin.database.executor;

import be.machigan.craftplugin.database.ParameterSqlTask;
import be.machigan.craftplugin.lambda.ParameterRunnable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

@AllArgsConstructor
public class SqlExecutor {
    protected final Connection connection;

    public void query(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @Nullable ParameterSqlTask<ResultSet> fromResultSet
    ) throws SQLException {
        if (this.connection == null) return;
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            if (applyToPreparedStatement != null)
                applyToPreparedStatement.run(ps);
            if (fromResultSet == null) {
                ps.execute();
            } else {
                fromResultSet.run(ps.executeQuery());
            }
        }
    }

    public void query(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement
    ) throws SQLException {
        query(sql, applyToPreparedStatement, null);
    }

    public void queryCatchError(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @Nullable ParameterSqlTask<ResultSet> fromResultSet,
            @NotNull ParameterRunnable<SQLException> onError
    ) {
        try {
            query(sql, applyToPreparedStatement, fromResultSet);
        } catch (SQLException e) {
            onError.run(e);
        }
    }

    public void queryCatchError(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @NotNull ParameterRunnable<SQLException> onError
    ) {
        try {
            query(sql, applyToPreparedStatement, null);
        } catch (SQLException e) {
            onError.run(e);
        }
    }
}
