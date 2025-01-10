package be.machigan.craftplugin.formatter.color;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApiStatus.Internal
public abstract class ColorBuilder<C> {
    protected static final String RGB_COLOR_REGEX = "§x(§[a-fA-F0-9]){6}";
    private static final List<Character> COLOR_CODE = Arrays.asList(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'k', 'l', 'm', 'n', 'o'
    );
    private static final List<Character> COLOR_CHAR = Arrays.asList('§', '&');
    private static final Map<String, ChatColor> STYLE_CODE_TRANSLATION = new HashMap<>(Map.of(
            "&l", ChatColor.BOLD,
            "&o", ChatColor.ITALIC,
            "&n", ChatColor.UNDERLINE,
            "&m", ChatColor.STRIKETHROUGH,
            "&k", ChatColor.MAGIC
    ));

    public abstract @NotNull C toColoredComponent(String toColor);

    public @NotNull List<C> toColoredComponents(List<String> toColor) {
        return toColor
                .stream()
                .map(this::toColoredComponent)
                .toList()
                ;
    }

    @SuppressWarnings("deprecation") // deprecated by Paper, but the server can be on Spigot
    protected String replaceColor(String text) {
        String b1;
        String b2;
        String w;
        while (
                (b1 = StringUtils.substringBetween(text, "<s:", ">")) != null
                && (b2 = StringUtils.substringBetween(text, "<e:", ">")) != null
                && ( w = StringUtils.substringBetween(text, "<s:" + b1 + ">", "<e:" + b2 + ">")) != null
        ) {
            final String[] copy = {w};
            StringBuilder subS = new StringBuilder();
            STYLE_CODE_TRANSLATION.forEach((code, translation) -> {
                if (copy[0].contains(code)) {
                    subS.append(translation);
                    copy[0] = copy[0].replace(code, "");
                }
            });
            String colored = this.applyGradient(copy[0], b1, b2, subS.toString());
            text = text.replace("<s:" + b1 + ">" + w + "<e:" + b2 + ">", colored);
        }

        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String hex = text.substring(matcher.start(), matcher.end());
            text = text.replace(hex, net.md_5.bungee.api.ChatColor.of(hex).toString());
            matcher = pattern.matcher(text);
        }

        return ChatColor.translateAlternateColorCodes('&', text)
                .replace("\t", "   ")
                .replace("\\t", "   ")
                ;
    }

    private static int getCountedChar(String message) {
        char[] messageArray = message.toCharArray();
        int notCounted = 0;
        boolean keepColor = false;
        for (int i = 0; i < messageArray.length; i++) {
            Character c = messageArray[i];
            if (COLOR_CHAR.contains(c) && i < messageArray.length - 1 && COLOR_CODE.contains(messageArray[i + 1])) {
                keepColor = true;
                notCounted++;
                continue;
            }
            if (keepColor && COLOR_CHAR.contains(messageArray[i - 1])) {
                notCounted++;
                continue;
            }
            if (i != 0 && COLOR_CHAR.contains(messageArray[i - 1]) && messageArray[i] == 'r') {
                notCounted++;
                continue;
            }
            if (keepColor && COLOR_CHAR.contains(c) && i < messageArray.length - 1 && messageArray[i + 1] == 'r') {
                keepColor = false;
                notCounted++;
            } else if (keepColor) {
                notCounted++;

            }
        }
        return message.length() - notCounted;
    }

    protected String applyGradient(String message, String from, String to, String subS) {
        if (message == null) return null;
        if (from.length() != 6 || to.length() != 6) return message;

        String[] fromA = from.split("(?<=\\G..)");
        String[] toA = to.split("(?<=\\G..)");
        int[] step = new int[3];

        int countedChar = getCountedChar(message);
        if (countedChar == 0)
            countedChar = message.length();
        int[] current = {Integer.parseInt(fromA[0], 16), Integer.parseInt(fromA[1], 16), Integer.parseInt(fromA[2], 16)};
        for (int i = 0; i < 3; i++)
            step[i] = (Integer.parseInt(fromA[i], 16) - Integer.parseInt(toA[i], 16)) / countedChar;

        StringBuilder sb = new StringBuilder();
        boolean keepColor = false;
        char[] messageChar = message.toCharArray();
        for (int i = 0; i < messageChar.length; i++) {
            Character c = messageChar[i];
            String[] hex = new String[3];
            for (int j = 0; j < 3; j++)
                hex[j] = Integer.toHexString(current[j]);
            if (COLOR_CHAR.contains(c) && i < messageChar.length - 1 && COLOR_CODE.contains(messageChar[i + 1])) {
                keepColor = true;
                sb.append("§").append(messageChar[i + 1]);
                continue;
            }
            if (
                    (keepColor && COLOR_CHAR.contains(messageChar[i - 1]))
                    || (i != 0 && COLOR_CHAR.contains(messageChar[i - 1]) && messageChar[i] == 'r')
            ) continue;
            if (keepColor && COLOR_CHAR.contains(c) && i < messageChar.length - 1 && messageChar[i + 1] == 'r') {
                keepColor = false;
                continue;
            }
            if (keepColor) {
                sb.append(c);
                continue;
            }
            for (int j = 0; j < 3; j++) {
                if (hex[j].length() == 1)
                    hex[j] = "0" + hex[j];
            }

            sb.append(net.md_5.bungee.api.ChatColor.of("#" + hex[0] + hex[1] + hex[2])).append(subS).append(c);
            for (int j = 0; j < 3; j++)
                current[j] -= step[j];
        }
        return sb.append(ChatColor.RESET).toString();
    }
}
