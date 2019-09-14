package com.yerti.core.menus;

/**
 * Enum for the different rows in a menu
 * @see com.yerti.core.menus.Page
 */
public enum Row {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5), SIXTH(6);



    public int index;

    Row(int index) {
        this.index = index;
    }
}
