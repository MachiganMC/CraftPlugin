package be.machigan.craftplugin.command.arg.sub;

import be.machigan.craftplugin.command.arg.ArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class AnonymousSubArgument<T extends CommandSender> implements SubArgumentHolder<T>  {
    private final ArgumentHolder<T> nextArgument;

    @Override
    public void execute(CommandData<T> commandData) {
        if (this.nextArgument == null) return;
        commandData.getRemainingArguments().removeFirst();
        this.nextArgument.execute(new CommandData<>(
                commandData.getSender(),
                this.nextArgument,
                commandData.getCurrentArgument(),
                commandData.getRemainingArguments(),
                commandData.getArguments()
        ));
    }

    @Override
    public @NotNull List<String> getArgumentTabCompleter() {
        return Collections.emptyList();
    }

    @Override
    public @Nullable ArgumentHolder<T> next(String nextArgument) {
        return this.nextArgument;
    }
}
