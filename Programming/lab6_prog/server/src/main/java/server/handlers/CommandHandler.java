package server.handlers;

import common.network.requests.Request;
import common.network.responses.Response;
import common.network.responses.NoSuchCommandResponse;
import server.managers.CommandManager;

public class CommandHandler {
    private final CommandManager manager;

    public CommandHandler(CommandManager manager) {
        this.manager = manager;
    }

    public Response handle(Request request) {
        var command = manager.getCommands().get(request.getName());
        if (command == null) return new NoSuchCommandResponse(request.getName());
        manager.addToHistory(command.getName());
        return command.execute(request);
    }
}