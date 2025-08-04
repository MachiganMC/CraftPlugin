package be.machigan.craftplugin.command.data;

import be.machigan.craftplugin.command.arg.ArgumentHolder;
import lombok.Data;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

@Data
public class CommandData<T extends CommandSender> {
    @NotNull private final T sender;
    @NotNull private final ArgumentHolder<T> currentArgument;
    @Nullable private final ArgumentHolder<T> previousArgument;
    @NotNull private final LinkedList<String> remainingArguments;
    @NotNull private final List<String> arguments;

    public boolean isSendByPlayer() {
        return this.sender instanceof Player;
    }
}
