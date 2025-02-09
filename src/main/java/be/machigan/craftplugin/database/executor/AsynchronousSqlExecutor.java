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

    public static boolean hasRemainingTasks() {
        return !TASK_QUEUE.isEmpty();
    }

    @Override
    public void query(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @Nullable ParameterSqlTask<ResultSet> fromResultSet
    ) {
        TASK_QUEUE.addTask(() -> super.query(sql, applyToPreparedStatement, fromResultSet));
    }

    @Override
    public void query(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement
    ) {
        TASK_QUEUE.addTask(() -> super.query(sql, applyToPreparedStatement, null));
    }

    @Override
    public void queryCatchError(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @Nullable ParameterSqlTask<ResultSet> fromResultSet,
            @NotNull ParameterRunnable<SQLException> onError
    ) {
        TASK_QUEUE.addTask(() -> super.queryCatchError(sql, applyToPreparedStatement, fromResultSet, onError));
    }

    @Override
    public void queryCatchError(
            @NotNull String sql,
            @Nullable ParameterSqlTask<PreparedStatement> applyToPreparedStatement,
            @NotNull ParameterRunnable<SQLException> onError
    ) {
        this.queryCatchError(sql, applyToPreparedStatement, null, onError);
    }
}
