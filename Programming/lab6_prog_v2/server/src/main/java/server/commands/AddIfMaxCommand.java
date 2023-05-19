package server.commands;


import common.domain.Product;
import common.exceptions.WrongAmountOfElementsException;

import server.handlers.ResponseOutputer;
import server.managers.CollectionManager;


/**
 * Command 'add_if_max'. Adds a new element to collection if it's more than the maximum one.
 */
public class AddIfMaxCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        super("add_if_max" ,"{element}", "add a new element to the collection if its value is greater than the value of the largest element in this collection");
        this.collectionManager = collectionManager;
    }

    /**
     * If the collection is empty, the product is added to the collection.
     * Otherwise, the product is added to the collection only if its coordinates are highest
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            Product product = (Product) objectArgument;


            if(collectionManager.getCollection().size() == 0){
                collectionManager.addToCollection(product);
                collectionManager.save();
                ResponseOutputer.appendln("Product was added successfully");
                return true;
            }


            else if(collectionManager.greaterThanAll(product)){
                collectionManager.addToCollection(product);
                collectionManager.save();
                ResponseOutputer.appendln("Product was added successfully");
            } else {
                ResponseOutputer.appendError("Organization annual turnover is not enough to add");
            }
            return true;
        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("The object passed by the client is incorrect!");
        }
        return false;
    }
}