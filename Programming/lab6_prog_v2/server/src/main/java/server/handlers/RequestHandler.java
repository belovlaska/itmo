package server.handlers;


import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseResult;
import server.managers.CommandManager;

import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * Handles requests.
 */
public class RequestHandler {
    private final CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Handles requests.
     *
     * @param request Request to be processed.
     * @return Response to request.
     */
    public Response handle(Request request) {
        commandManager.addToHistory(request.getCommandName());
        ResponseResult responseResult = executeCommand(
                request.getCommandName(),
                request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        return new Response(responseResult, ResponseOutputer.getAndClear());
    }

    /**
     * Executes a command from a request.
     *
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */
    private ResponseResult executeCommand(String command, String commandStringArgument,
                                          Object commandObjectArgument) {
        if (command.isEmpty()) {
            return ResponseResult.OK;
        }
        var commands = new HashMap<String, BiFunction<String, Object, Boolean>>() {{
            put("help", commandManager::help);
            put("info", commandManager::info);
            put("show", commandManager::show);
            put("add", commandManager::add);
            put("update", commandManager::update);
            put("remove_by_id", commandManager::removeById);
            put("remove_lower", commandManager::removeLower);
            put("clear", commandManager::clear);
            put("execute_script", commandManager::executeScript);
            put("add_if_max", commandManager::addIfMax);
            put("history", commandManager::history);
            put("print_unique_owner", commandManager::printUniqueOwner);
            put("filter_contains_name", commandManager::filterContainsName);
            put("filter_starts_with_name", commandManager::filterStartsWithName);
        }};
        var currentCommand = commands.get(command);
        if (currentCommand == null) {
            ResponseOutputer.appendln("Command '" + command + "' was not found. Try to write 'help' for more info.");
            return ResponseResult.ERROR;
        }

        if (!currentCommand.apply(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
        return ResponseResult.OK;
    }
}