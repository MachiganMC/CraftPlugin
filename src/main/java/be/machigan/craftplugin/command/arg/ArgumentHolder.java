package be.machigan.craftplugin.command.arg;

import be.machigan.craftplugin.command.CommandUtils;
import be.machigan.craftplugin.command.PermissionExecutor;
import be.machigan.craftplugin.command.arg.sub.SubArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@Setter
@Accessors(chain = true)
public abstract class ArgumentHolder<T extends CommandSender> {
    protected final PermissionExecutor<T> executor;
    protected Consumer<CommandData<T>> onNoArg;
    protected final SubArgumentHolder<T> subArguments;
    protected ArgumentTabCompleterType tabCompleterType;
    protected Function<Player, List<Object>> customTabCompleterGetter;

    protected ArgumentHolder(
            @Nullable PermissionExecutor<T> executor,
            @NotNull SubArgumentHolder<T> subArguments,
            @Nullable Consumer<CommandData<T>> onNoArg,
            @NotNull ArgumentTabCompleterType tabCompleterType,
            @Nullable Function<Player, List<Object>> customTabCompleterGetter
    ) {
        this.executor = executor;
        this.onNoArg = onNoArg;
        this.subArguments = subArguments;
        this.tabCompleterType = tabCompleterType;
        this.customTabCompleterGetter = customTabCompleterGetter;
    }

    public void execute(CommandData<T> commandData) {
        CommandUtils.executeIfNotNull(this.executor, commandData);
        if (commandData.getRemainingArguments().isEmpty()) {
            CommandUtils.executeIfNotNull(this.onNoArg, commandData);
            return;
        }
        this.subArguments.execute(commandData);
    }

    public abstract T convertToSender(CommandSender sender);

    public ArgumentHolder<T> setCustomTabCompleterGetter(@NotNull Function<Player, List<Object>> tabCompleterGetter) {
        Objects.requireNonNull(tabCompleterGetter);
        this.customTabCompleterGetter = tabCompleterGetter;
        this.tabCompleterType = ArgumentTabCompleterType.CUSTOM_LIST;
        return this;
    }
}
