package be.machigan.craftplugin.command.arg.builder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArgumentFactory {
    public static NamedArgumentFactory named(String name) {
        return new NamedArgumentFactory(name);
    }

    public static AnonymousArgumentFactory anonymous() {
        return new AnonymousArgumentFactory();
    }
}
