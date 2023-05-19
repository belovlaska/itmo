
package server.commands;

import common.domain.Product;
import common.exceptions.WrongAmountOfElementsException;
import server.handlers.ResponseOutputer;
import server.managers.CollectionManager;

/**
 * Remove an organization from the collection
 */
public class FilterContainsNameCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public FilterContainsNameCommand(CollectionManager collectionManager) {
        super("filter_contains_name", "<name>", "print list of element which field name contains introduced string");
        this.collectionManager = collectionManager;
    }



    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            String name = stringArgument;
            var list = collectionManager.listContainsName(name);
            if(list.isEmpty()){
                ResponseOutputer.appendln("There are no products which names contains this substring");
            }
            else{
                StringBuilder out = new StringBuilder();
                for(Product el : list){
                out.append(el.toString()).append("\n");
                }
                ResponseOutputer.appendln(out);
            }

        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
