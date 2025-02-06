package be.machigan.craftplugin.database;

import be.machigan.craftplugin.CraftPlugin;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;

public class AsynchronousSqlTaskQueue {
    private final LinkedList<SqlTask> taskQueue = new LinkedList<>();

    public void addTask(SqlTask task) {
        taskQueue.addLast(task);
        if (taskQueue.size() == 1) {
            // The Thread is used instead of Bukkit#runTaskAsynchronously because, with the Bukkit the FIFO of the
            // LinkedList is not always respected and only god knows why.
            new Thread(this::executeTask).start();
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
