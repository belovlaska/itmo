package ru.itmo.prog.commands;

import ru.itmo.prog.exceptions.IncorrectScriptException;
import ru.itmo.prog.exceptions.InvalidValueException;
import ru.itmo.prog.exceptions.InvalidFormException;

public abstract class Command {
    private String name, description;

    /**
     * Базовый класс команды
     * @author belovlaska
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Базовый вызов команды
     */
    public abstract boolean execute(String[] args);
}
