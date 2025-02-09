package be.machigan.craftplugin.database.executor;

import be.machigan.craftplugin.database.ParameterSqlTask;
import be.machigan.craftplugin.lambda.ParameterRunnable;
import com.google.common.base.Verify;
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
                try (ResultSet rs = ps.executeQuery()) {
                    fromResultSet.run(rs);
                }
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

    public <T> T queryGetGenerateValue(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            Class<T> generateKeyType
    ) throws SQLException {
        if (this.connection == null) return null;
        try (PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (applyToPreparedStatement != null)
                applyToPreparedStatement.run(ps);
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                Verify.verify(rs.next(), "No generated key for following statement : \"" + sql + "\"");
                return rs.getObject(1, generateKeyType);
            }
        }
    }

    public <T> T queryGetGenerateValueCatchError(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @NotNull ParameterRunnable<SQLException> onError,
            Class<T> generateKeyType
    ) {
        try {
            return this.queryGetGenerateValue(sql, applyToPreparedStatement, generateKeyType);
        } catch (SQLException e) {
            onError.run(e);
            return null;
        }
    }
}
