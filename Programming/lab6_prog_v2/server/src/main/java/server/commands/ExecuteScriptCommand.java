package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.handlers.ResponseOutputer;


/**
 * Execute a script
 */
public class ExecuteScriptCommand extends AbstractCommand {

    public ExecuteScriptCommand() {
        super("execute_script", "<file_name>", "read and execute the script from the specified file.");
    }

    /**
     * Execute a script
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ResponseOutputer.appendln("Execute script '" + stringArgument + "'...");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}