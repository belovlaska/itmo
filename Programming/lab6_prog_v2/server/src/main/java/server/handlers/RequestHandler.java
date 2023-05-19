package server.handlers;


import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseResult;
import server.managers.CommandManager;

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
    public Response handle(Request request){
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

        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "info":
                if (!commandManager.info(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "show":
                if (!commandManager.show(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "add":
                if (!commandManager.add(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "update":
                if (!commandManager.update(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "remove_lower":
                if (!commandManager.removeLower(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "execute_script":
                if (!commandManager.executeScript(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "add_if_max":
                if (!commandManager.addIfMax(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "history":
                if (!commandManager.history(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "print_unique_owner":
                if (!commandManager.printUniqueOwner(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "filter_contains_name":
                if (!commandManager.filterContainsName(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            case "filter_starts_with_name":
                if (!commandManager.filterStartsWithName(commandStringArgument, commandObjectArgument)) return ResponseResult.ERROR;
                break;
            default:
                ResponseOutputer.appendln("Command '" + command + "' was not found. Try to write 'help' for more info.");
                return ResponseResult.ERROR;
        }
        return ResponseResult.OK;
    }
}