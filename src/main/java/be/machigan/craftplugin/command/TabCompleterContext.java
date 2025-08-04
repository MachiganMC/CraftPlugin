package be.machigan.craftplugin.command;

import be.machigan.craftplugin.command.arg.ArgumentHolder;
import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class TabCompleterContext {
    private final ArgumentHolder<?> currentArgument;
    private final Player sender;
}
