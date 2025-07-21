package be.machigan.craftplugin.command;

import be.machigan.craftplugin.command.data.CommandData;
import be.machigan.craftplugin.internal.exception.StopCommandSignal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class PermissionExecutor<T extends CommandSender> implements Consumer<CommandData<T>> {
    protected final String requiredPermission;
    protected final Consumer<CommandData<T>> onExecute;
    protected final Consumer<CommandData<T>> onNoPerm;

    @Override
    public void accept(CommandData<T> commandData) {
        if (!canExecute(commandData))
            throw new StopCommandSignal();
        if (this.onExecute != null)
            this.onExecute.accept(commandData);
    }

    public boolean canExecute(CommandData<T> commandData) {
        if (this.requiredPermission == null) return true;
        CommandSender sender = commandData.getSender();
        if (!(sender instanceof Player player) || player.hasPermission(this.requiredPermission)) return true;
        if (this.onNoPerm != null)
            this.onNoPerm.accept(commandData);
        return false;
    }

    public boolean canExecute(Player player) {
        return this.requiredPermission == null || player.hasPermission(this.requiredPermission);
    }
}
