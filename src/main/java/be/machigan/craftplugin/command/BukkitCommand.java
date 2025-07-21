package be.machigan.craftplugin.command;

import be.machigan.craftplugin.command.arg.ArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import be.machigan.craftplugin.internal.exception.StopCommandSignal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ApiStatus.Internal
public class BukkitCommand<T extends CommandSender> extends Command {
    private final ArgumentHolder<T> command;

    public BukkitCommand(@NotNull String name, ArgumentHolder<T> command) {
        super(name);
        this.command = command;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        LinkedList<String> arguments = new LinkedList<>(Arrays.asList(args));
        try {
            this.command.execute(new CommandData<>(
                    this.command.convertToSender(sender),
                    this.command,
                    null,
                    arguments,
                    arguments
            ), arguments);
        } catch (StopCommandSignal ignored) {}
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (!(sender instanceof Player player) || args.length == 0)
            return Collections.emptyList();
        if (args.length == 1)
            return this.command.getTabCompleterType().complete(this.command, args[0]);
        ArgumentHolder<T> currentArgument = this.command;
        for (int i = 1; i < args.length; i++) {
            currentArgument = currentArgument.getSubArguments().get(args[i - 1]);
            if (currentArgument == null)
                return Collections.emptyList();
        }
        if (currentArgument.getExecutor().canExecute(player))
            return currentArgument.getTabCompleterType().complete(currentArgument, args[args.length - 1]);
        return Collections.emptyList();
    }
}
