
package server.commands;

import common.domain.Product;
import common.exceptions.WrongAmountOfElementsException;
import server.handlers.ResponseOutputer;
import server.managers.CollectionManager;

/**
 * Remove an organization from the collection
 */
public class FilterStartsWithNameCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public FilterStartsWithNameCommand(CollectionManager collectionManager) {
        super("filter_starts_with_name", "<name>", "print list of element which field name starts with introduced string");
        this.collectionManager = collectionManager;
    }



    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            String name = stringArgument;
            var list = collectionManager.listStartsWithName(name);
            if(list.isEmpty()){
                ResponseOutputer.appendln("There are no products which names starts with this substring");
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
