package server.commands;


import common.domain.Product;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotFoundException;
import common.exceptions.WrongAmountOfElementsException;
import server.handlers.ResponseOutputer;
import server.managers.CollectionManager;

/**
 * The class is used to update the value of the collection element whose id is equal to the given one
 */
public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update" ,"<id> {element}", "update the value of the collection element whose id is equal to the given one");
        this.collectionManager = collectionManager;
    }

    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            Product product = (Product) objectArgument;
            int id = Integer.parseInt(stringArgument);
            if(collectionManager.getById(id) == null) throw new NotFoundException();
            collectionManager.getById(id).update(product);
            collectionManager.save();

            ResponseOutputer.appendln("Product was updated successfully");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (NotFoundException exception) {
            ResponseOutputer.appendError("There is no product with this ID in the collection!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("The object passed by the client is incorrect!");
        }
        return false;
    }
}
