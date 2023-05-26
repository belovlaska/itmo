//package server.commands;
//
//import server.managers.CollectionManager;
//import server.utils.consoleShell.Console;
//
///**
// * Команда для сохранения коллекции в файл
// * @author belovlaska
// */
//public class Save extends Command {
//
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public Save(Console console, CollectionManager collectionController) {
//        super("save", "сохранить коллекцию в файл");
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
//        collectionController.saveCollection();
//        return true;
//    }
//}
