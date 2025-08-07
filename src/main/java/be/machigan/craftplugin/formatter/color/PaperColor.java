package be.machigan.craftplugin.formatter.color;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaperColor extends ColorBuilder<Component> {
    @Override
    public @NotNull Component toColoredComponent(String toColor) {
        Component component = Component.empty();
        String s = this.replaceColor(toColor);
        List<String> colors = findColors(s);
        if (colors.isEmpty()) return Component.text(s);
        String[] texts = s.split(RGB_COLOR_REGEX);
        component = component.append(Component.text(texts[0]));
        for (int i = 0, j = 1; i < colors.size() && j < texts.length; i++, j++) {
            component = component.append(Component
                    .text(texts[j])
                    .color(TextColor.fromHexString(colors.get(i))));
        }
        return component;
    }

    private static List<String> findColors(String s) {
        List<String> colors = new ArrayList<>();
        Matcher matcher = Pattern.compile(RGB_COLOR_REGEX).matcher(s);
        while (matcher.find()) {
            colors.add("#" + s
                    .substring(matcher.start(), matcher.end())
                    .replaceAll("(§x)|§", "")
            );
        }
        return colors;
    }
}
