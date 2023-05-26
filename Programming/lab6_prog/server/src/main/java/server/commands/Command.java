package server.commands;

import common.network.requests.Request;
import common.network.responses.Response;

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
    public abstract Response execute(Request request);
}
