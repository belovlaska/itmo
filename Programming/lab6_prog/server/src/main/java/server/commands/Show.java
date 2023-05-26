//package server.commands;
//
//
//import server.managers.CollectionManager;
//import server.utils.consoleShell.Console;
//
//
///**
// * Command to display all elements of a collection
// * @author belovlaska
// */
//public class Show extends Command {
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public Show(Console console, CollectionManager collectionController) {
//        super("show", "print to standard output all elements of the collection in string representation");
//        this.console = console;
//        this.collectionController = collectionController;
//    }
//
//    @Override
//    public ShowResponseDTO execute(ShowRequestDTO request) {
//        return new ShowResponseDTO(collectionController.sort());
//    }
//}
