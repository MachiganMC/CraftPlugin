package be.machigan.craftplugin.database;

import be.machigan.craftplugin.CraftPlugin;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;

public class AsynchronousSqlTaskQueue {
    private final LinkedList<SqlTask> taskQueue = new LinkedList<>();

    public void addTask(SqlTask task) {
        if (taskQueue.isEmpty()) {
            taskQueue.add(task);
            Bukkit.getScheduler().runTaskAsynchronously(CraftPlugin.getPlugin(), this::executeTask);
        } else {
            taskQueue.add(task);
        }
    }

    private void executeTask() {
        try {
            taskQueue.get(0).run();
        } catch (SQLException e) {
            CraftPlugin.getPlugin().getLogger().log(Level.WARNING, e.getMessage());
        } finally {
            taskQueue.poll();
            this.runNextTask();
        }
    }

    private void runNextTask() {
        if (taskQueue.isEmpty()) return;
        this.executeTask();
    }
}
