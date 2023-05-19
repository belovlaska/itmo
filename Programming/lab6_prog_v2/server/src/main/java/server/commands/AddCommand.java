package server.commands;


import common.domain.Product;
import common.exceptions.WrongAmountOfElementsException;

import server.handlers.ResponseOutputer;
import server.managers.CollectionManager;



/**
 * The class is responsible for adding an organization to the collection
 */
public class AddCommand extends AbstractCommand{

    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add" ,"{element}", "add a new element to the collection");
        this.collectionManager = collectionManager;
    }



    /**
     * The function adds a product to the collection
     *
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            Product product = (Product) objectArgument;
            collectionManager.addToCollection(product);
            collectionManager.save();
            ResponseOutputer.appendln("Product was added successfully!");
            return true;
        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("The object passed by the client is incorrect!");
        }
        return false;
    }
}