package be.machigan.craftplugin.menu.inventory;

import lombok.Getter;

@Getter
public enum InventoryMenuSize {
    ONE_ROW(1),
    TWO_ROW(2),
    THREE_ROW(3),
    FOUR_ROW(4),
    FIVE_ROW(5),
    SIX_ROW(6)
    ;

    private final int size;

    InventoryMenuSize(int rowCount) {
        this.size = rowCount * 9;
    }
}
