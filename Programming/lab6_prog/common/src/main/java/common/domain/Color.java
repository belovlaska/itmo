package common.domain;

import java.io.Serializable;

/**
 * Перечисление содержащее все цвета
 *
 * @author belovlaska
 * @version 1.0
 */

public enum Color implements Serializable {
    GREEN,
    RED,
    BLUE,
    ORANGE,
    YELLOW,
    WHITE;

    /**
     * Названия всех цветов через запятую
     *
     * @return строка со всеми цветами
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var value : values()) {
            nameList.append(value.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
