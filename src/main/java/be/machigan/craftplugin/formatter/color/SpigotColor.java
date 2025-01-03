package be.machigan.craftplugin.formatter.color;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
@ApiStatus.Internal
public class SpigotColor extends ColorBuilder<BaseComponent[]> {
    @Override
    public BaseComponent @NotNull [] toColoredComponent(String toColor) {
        List<TextComponent> bc = new ArrayList<>();
        String s = this.replaceColor(toColor);
        if (!Pattern.compile(RGB_COLOR_REGEX).matcher(s).find()) {
            return new BaseComponent[]{new TextComponent(s)};
        }
        int i = 0;
        Matcher matcher = Pattern.compile(RGB_COLOR_REGEX).matcher(s);
        bc.add(new TextComponent(s.split(RGB_COLOR_REGEX)[0]));
        while (matcher.find()) {
            i++;
            String color = s.substring(matcher.start(), matcher.end());
            color = color.replace("§x", "").replace("§", "");
            TextComponent message = new TextComponent(s.split(RGB_COLOR_REGEX)[i]);
            message.setColor(net.md_5.bungee.api.ChatColor.of("#" + color));
            bc.add(message);
        }
        return bc.toArray(new BaseComponent[0]);
    }

    public TextComponent toColoredTextComponent(String color) {
        return new TextComponent(this.toColoredComponent(color));
    }
}
