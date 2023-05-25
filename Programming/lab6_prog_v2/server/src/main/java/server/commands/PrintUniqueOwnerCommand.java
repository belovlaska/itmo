
package server.commands;

import common.domain.Product;
import common.exceptions.WrongAmountOfElementsException;
import server.handlers.ResponseOutputer;
import server.managers.CollectionManager;

/**
 * Remove an organization from the collection
 */
public class PrintUniqueOwnerCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public PrintUniqueOwnerCommand(CollectionManager collectionManager) {
        super("print_unique_owner", "", "print list of unique names of owners");
        this.collectionManager = collectionManager;
    }



    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            var set = collectionManager.uniqueOwners();
            if(set.isEmpty()){
                ResponseOutputer.appendln("There are no not null owners");
            }
            else{
                StringBuilder out = new StringBuilder();
                for(String el : set){
                    out.append(el).append("\n");
                }
                ResponseOutputer.appendln(out);
            }

        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
