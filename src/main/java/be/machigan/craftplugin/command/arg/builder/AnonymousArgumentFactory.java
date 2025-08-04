package be.machigan.craftplugin.command.arg.builder;

import be.machigan.craftplugin.command.arg.AnonymousPlayerArgument;
import be.machigan.craftplugin.command.arg.AnonymousUniversalSenderArgument;
import be.machigan.craftplugin.command.arg.ArgumentTabCompleterType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnonymousArgumentFactory extends ArgumentFactoryBuilder<AnonymousUniversalSenderArgument, AnonymousPlayerArgument> {
    @Override
    protected AnonymousUniversalSenderArgument getUniversalSenderArgument(ArgumentBuilderDto<CommandSender> dto) {
        return new AnonymousUniversalSenderArgument(
                dto.getExecutor(),
                dto.getSubArguments(),
                dto.getOnNoArg(),
                ArgumentTabCompleterType.AUTO,
                null
        );
    }

    @Override
    protected AnonymousPlayerArgument getPlayerArgument(ArgumentBuilderDto<Player> dto) {
        return new AnonymousPlayerArgument(
                dto.getExecutor(),
                dto.getSubArguments(),
                dto.getOnNoArg(),
                ArgumentTabCompleterType.AUTO,
                null
        );
    }
}
