package be.machigan.craftplugin.command.arg.builder;

import be.machigan.craftplugin.command.PermissionExecutor;
import be.machigan.craftplugin.command.arg.sub.SubArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import lombok.Data;
import org.bukkit.command.CommandSender;

import java.util.function.Consumer;

@Data
public class ArgumentBuilderDto<S extends CommandSender> {
   private final PermissionExecutor<S> executor;
   private final SubArgumentHolder<S> subArguments;
   private final Consumer<CommandData<S>> onNoArg;
}
