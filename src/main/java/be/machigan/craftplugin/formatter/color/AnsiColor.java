package be.machigan.craftplugin.formatter.color;

public class AnsiColor {
    public static String ansi(String s) {
        return s.replace("&0", AnsiColorCode.BLACK.toString())
                .replace("&1", AnsiColorCode.BLUE.toString())
                .replace("&2", AnsiColorCode.GREEN.toString())
                .replace("&3", AnsiColorCode.CYAN.toString())
                .replace("&4", AnsiColorCode.RED.toString())
                .replace("&5", AnsiColorCode.PURPLE.toString())
                .replace("&e", AnsiColorCode.YELLOW.toString())
                .replace("&f", AnsiColorCode.WHITE.toString()) + AnsiColorCode.RESET;

    }

    public enum AnsiColorCode {
        RESET("\033[0m"),
        BLACK("\033[0;30m"),
        RED("\033[0;31m"),
        GREEN("\033[0;32m"),
        YELLOW("\033[0;33m"),
        BLUE("\033[0;34m"),
        PURPLE("\033[0;35m"),
        CYAN("\033[0;36m"),
        WHITE("\033[0;37m");
        private final String ansiValue;
        AnsiColorCode(String ansiValue) {
            this.ansiValue = ansiValue;
        }

        @Override
        public String toString() {
            return this.ansiValue;
        }
    }
}
