//package server.commands;
//
//import server.managers.CollectionManager;
//import server.utils.consoleShell.Console;
//
///**
// * Команда для очистки коллекции
// * @author belovlaska
// */
//public class Clear extends Command {
//
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public Clear(Console console, CollectionManager collectionController) {
//        super("clear", "очистить коллекцию");
//        this.console = console;
//        this.collectionController = collectionController;
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        if(!args[1].isEmpty()){
//            console.println("Please enter the command in the correct format");
//            return false;
//        }
//        collectionController.clearCollection();
//        console.println("Коллекция очищена!");
//        return true;
//    }
//}
