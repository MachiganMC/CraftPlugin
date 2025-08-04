package be.machigan.craftplugin.command.arg.sub;

import be.machigan.craftplugin.command.CommandUtils;
import be.machigan.craftplugin.command.arg.ArgumentHolder;
import be.machigan.craftplugin.command.arg.NamedArgument;
import be.machigan.craftplugin.command.data.CommandData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class NamedSubArgumentHolder<T extends CommandSender> implements SubArgumentHolder<T> {
    private final Map<String, ? extends NamedArgument<T>> subArguments;
    private Consumer<CommandData<T>> onArgumentNotExist;

    @Override
    public void execute(CommandData<T> commandData) {
        String nextArgumentString = commandData.getRemainingArguments().removeFirst();
        ArgumentHolder<T> nextArgument = this.subArguments.get(nextArgumentString);
        if (nextArgument == null) {
            CommandUtils.executeIfNotNull(this.onArgumentNotExist, commandData);
            return;
        }
        nextArgument.execute(new CommandData<>(
                commandData.getSender(),
                nextArgument,
                commandData.getCurrentArgument(),
                commandData.getRemainingArguments(),
                commandData.getArguments()
        ));
    }

    @Override
    public @NotNull List<String> getArgumentTabCompleter() {
        return this.subArguments.keySet().stream().toList();
    }

    @Override
    public @Nullable ArgumentHolder<T> next(String nextArgument) {
        return this.subArguments.get(nextArgument);
    }


}
