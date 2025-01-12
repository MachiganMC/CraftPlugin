package be.machigan.craftplugin.database.executor;

import be.machigan.craftplugin.database.ParameterSqlTask;
import be.machigan.craftplugin.lambda.ParameterRunnable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class SqlExecutor {
    protected final Connection connection;

    public void secureQuery(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @Nullable ParameterRunnable<ResultSet> fromResultSet
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

    public void secureQuery(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement
    ) throws SQLException {
        secureQuery(sql, applyToPreparedStatement, null);
    }

    public void secureQueryCatchError(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @Nullable ParameterRunnable<ResultSet> fromResultSet,
            @NotNull ParameterRunnable<SQLException> onError
    ) {
        try {
            secureQuery(sql, applyToPreparedStatement, fromResultSet);
        } catch (SQLException e) {
            onError.run(e);
        }
    }

    public void secureQueryCatchError(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @NotNull ParameterRunnable<SQLException> onError
    ) {
        try {
            secureQuery(sql, applyToPreparedStatement, null);
        } catch (SQLException e) {
            onError.run(e);
        }
    }
}
