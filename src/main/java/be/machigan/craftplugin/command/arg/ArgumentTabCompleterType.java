package be.machigan.craftplugin.command.arg;

import be.machigan.craftplugin.command.TabCompleterContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum ArgumentTabCompleterType {
    EMPTY(l -> Collections.emptyList()),
    PLAYERS_LIST(l -> Bukkit.getOnlinePlayers().stream().map(Player::getName).toList()),
    AUTO(c -> c.getCurrentArgument().getSubArguments().getArgumentTabCompleter()),
    CUSTOM_LIST(
            c -> {
                ArgumentHolder<?> argument = c.getCurrentArgument();
                if (argument == null)
                    return Collections.emptyList();
                return argument
                        .getCustomTabCompleterGetter()
                        .apply(c.getSender())
                        .stream()
                        .map(String::valueOf)
                        .toList();
            }
    );

    private final Function<TabCompleterContext, List<String>> argumentToStringListConvertor;

    public List<String> complete(TabCompleterContext context, String nextArg) {
        List<String> tab = new ArrayList<>();
        List<String> completions = this.argumentToStringListConvertor.apply(context);
        StringUtil.copyPartialMatches(nextArg, completions, tab);
        return tab;
    }
}
