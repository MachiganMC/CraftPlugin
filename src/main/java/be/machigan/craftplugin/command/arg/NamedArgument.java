package be.machigan.craftplugin.command.arg;

import be.machigan.craftplugin.command.PermissionExecutor;
import be.machigan.craftplugin.command.arg.sub.SubArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@Setter
@Accessors(chain = true)
public abstract class NamedArgument<T extends CommandSender> extends ArgumentHolder<T> {
    protected final String name;

    protected NamedArgument(
            @NotNull String name,
            @Nullable PermissionExecutor<T> executor,
            @NotNull SubArgumentHolder<T> subArguments,
            @Nullable Consumer<CommandData<T>> onNoArg,
            @NotNull ArgumentTabCompleterType tabCompleterType,
            @Nullable Function<Player, List<Object>> customTabCompleterGetter
    ) {
        super(executor, subArguments, onNoArg, tabCompleterType, customTabCompleterGetter);
        name = name.toLowerCase().strip();
        Preconditions.checkArgument(!name.isEmpty(), "Name of an argument/command cannot be empty");
        this.name = name;
    }

    @Override
    public NamedArgument<T> setCustomTabCompleterGetter(@NotNull Function<Player, List<Object>> tabCompleterGetter) {
        super.setCustomTabCompleterGetter(tabCompleterGetter);
        return this;
    }

    @Override
    public NamedArgument<T> setOnNoArg(Consumer<CommandData<T>> onNoArg) {
        super.setOnNoArg(onNoArg);
        return this;
    }

    @Override
    public NamedArgument<T> setTabCompleterType(ArgumentTabCompleterType tabCompleterType) {
        super.setTabCompleterType(tabCompleterType);
        return this;
    }
}
