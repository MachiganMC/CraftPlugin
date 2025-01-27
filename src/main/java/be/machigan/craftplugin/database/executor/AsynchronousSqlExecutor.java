package be.machigan.craftplugin.database.executor;

import be.machigan.craftplugin.database.AsynchronousSqlTaskQueue;
import be.machigan.craftplugin.database.ParameterSqlTask;
import be.machigan.craftplugin.lambda.ParameterRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AsynchronousSqlExecutor extends SqlExecutor {
    private static final AsynchronousSqlTaskQueue TASK_QUEUE = new AsynchronousSqlTaskQueue();

    public AsynchronousSqlExecutor(Connection connection) {
        super(connection);
    }

    @Override
    public void secureQuery(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @Nullable ParameterSqlTask<ResultSet> fromResultSet
    ) {
        TASK_QUEUE.addTask(() -> super.secureQuery(sql, applyToPreparedStatement, fromResultSet));
    }

    @Override
    public void secureQuery(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement
    ) {
        this.secureQuery(sql, applyToPreparedStatement, null);
    }

    @Override
    public void secureQueryCatchError(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @Nullable ParameterSqlTask<ResultSet> fromResultSet,
            @NotNull ParameterRunnable<SQLException> onError
    ) {
        TASK_QUEUE.addTask(() -> super.secureQueryCatchError(sql, applyToPreparedStatement, fromResultSet, onError));
    }

    @Override
    public void secureQueryCatchError(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @NotNull ParameterRunnable<SQLException> onError
    ) {
        this.secureQueryCatchError(sql, applyToPreparedStatement, null, onError);
    }
}
