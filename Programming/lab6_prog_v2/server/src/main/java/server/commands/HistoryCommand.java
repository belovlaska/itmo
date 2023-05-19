package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.handlers.ResponseOutputer;


/**
 * This class is used to print the list of the last commands
 */
public class HistoryCommand extends AbstractCommand{

    public HistoryCommand() {
        super("history","", "show the list of the last commands");
    }

    /**
     * Prints the usage of the command
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return true;
    }
}