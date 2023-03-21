package ru.itmo.prog.models;

/**
 * Перечисление содержащее все единицы измерений
 *
 * @author belovlaska
 * @version 1.0
 */

public enum UnitOfMeasure {
    PCS,
    LITERS,
    GRAMS;

/**
 * Названия всех единиц измерений через запятую
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
