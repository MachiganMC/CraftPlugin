package be.machigan.craftplugin.command.arg.builder;

import be.machigan.craftplugin.command.arg.ArgumentTabCompleterType;
import be.machigan.craftplugin.command.arg.NamedPlayerArgument;
import be.machigan.craftplugin.command.arg.NamedUniversalSenderArgument;
import lombok.AllArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class NamedArgumentFactory extends ArgumentFactoryBuilder<NamedUniversalSenderArgument, NamedPlayerArgument> {
    private final String name;

    @Override
    protected NamedUniversalSenderArgument getUniversalSenderArgument(ArgumentBuilderDto<CommandSender> dto) {
        return new NamedUniversalSenderArgument(
                this.name,
                dto.getExecutor(),
                dto.getSubArguments(),
                dto.getOnNoArg(),
                ArgumentTabCompleterType.AUTO,
                null
        );
    }

    @Override
    protected NamedPlayerArgument getPlayerArgument(ArgumentBuilderDto<Player> dto) {
        return new NamedPlayerArgument(
                this.name,
                dto.getExecutor(),
                dto.getSubArguments(),
                dto.getOnNoArg(),
                ArgumentTabCompleterType.AUTO,
                null
        );
    }
}
