package be.machigan.craftplugin.command.arg.builder;

import be.machigan.craftplugin.command.arg.ArgumentHolder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class ArgumentFactoryBuilder<A extends ArgumentHolder<CommandSender>, B extends ArgumentHolder<Player>> {
    protected abstract A getUniversalSenderArgument(ArgumentBuilderDto<CommandSender> dto);
    protected abstract B getPlayerArgument(ArgumentBuilderDto<Player> dto);

    public ArgumentBuilder<CommandSender, A> universalSender() {
        return new ArgumentBuilder<>(this::getUniversalSenderArgument);
    }

    public ArgumentBuilder<Player, B> player() {
        return new ArgumentBuilder<>(this::getPlayerArgument);
    }
}

