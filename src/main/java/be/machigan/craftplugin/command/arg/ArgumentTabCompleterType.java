package be.machigan.craftplugin.command.arg;

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
    ARGUMENTS_LIST(a -> a.getSubArguments().values().stream().map(ArgumentHolder::getName).toList()),
    CUSTOM_LIST(a -> a.getSubArguments() != null ? a.getCustomTabCompleterGetter().get() : Collections.emptyList());

    private final Function<ArgumentHolder<?>, List<String>> argumentToStringListConvertor;

    public List<String> complete(ArgumentHolder<?> currentArg, String nextArg) {
        List<String> tab = new ArrayList<>();
        List<String> completions = this.argumentToStringListConvertor.apply(currentArg);
        StringUtil.copyPartialMatches(nextArg, completions, tab);
        return tab;
    }
}
