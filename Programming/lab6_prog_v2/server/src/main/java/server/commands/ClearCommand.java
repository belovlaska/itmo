package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.handlers.ResponseOutputer;
import server.managers.CollectionManager;


/**
 * Clear the collection
 */
public class ClearCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear","", "clear the collection");
        this.collectionManager = collectionManager;
    }


    /**
     * Clear the collection
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            collectionManager.clearCollection();
            collectionManager.save();
            ResponseOutputer.appendln("Collection was cleared");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}