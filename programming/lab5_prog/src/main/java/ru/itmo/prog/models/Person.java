package ru.itmo.prog.models;

import ru.itmo.prog.controllers.CollectionController;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Класс человека
 *
 * @author belovlaska
 * @version 1.0
 */

public class Person implements Validateble{
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.ZonedDateTime birthday; //Поле может быть null
    private long height; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле не может быть null

    public Person(String name, ZonedDateTime birthday, long height, Color eyeColor, Color hairColor) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public long getHeight() {
        return height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return height == person.height && Objects.equals(name, person.name) && Objects.equals(birthday, person.birthday) && Objects.equals(eyeColor, person.eyeColor) && Objects.equals(hairColor, person.hairColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, height, eyeColor, hairColor);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", height=" + height +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                '}';
    }

    @Override
    public boolean validate() {
        if(name != null && !name.isEmpty()
                && height > 0
                && hairColor != null) return true;
        return false;
    }

}
