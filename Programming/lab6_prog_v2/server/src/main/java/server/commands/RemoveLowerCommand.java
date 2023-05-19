package server.commands;

import common.domain.Product;
import common.exceptions.WrongAmountOfElementsException;
import server.handlers.ResponseOutputer;
import server.managers.CollectionManager;

/**
 * Команда для удаления из коллекции всех элементов, чья цена ниже, чем у заданного
 * @author belovlaska
 */
public class RemoveLowerCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("remove_lower", "{element}", "remove elements from collection which have less coordinates than introduced");
        this.collectionManager = collectionManager;
    }

    /**
     * Remove an organization from the collection
     * @return the response of right execution
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            Product product = (Product) objectArgument;
            collectionManager.removeLower(product);
            collectionManager.save();
            ResponseOutputer.appendln("Products was removed successfully");
            return true;

        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");

        }
        return false;
    }
}