//package server.commands;
//
//import server.managers.CollectionManager;
//import server.exceptions.InvalidAmountException;
//import server.utils.consoleShell.Console;
//
///**
// * Команда для вывода уникальных владельцев
// * @author belovlaska
// */
//
//public class PrintUniqueOwner extends Command{
//
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public PrintUniqueOwner(Console console, CollectionManager collectionController) {
//        super("print_unique_owner", "вывести уникальные значения поля owner всех элементов в коллекции");
//        this.console = console;
//        this.collectionController = collectionController;
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        try {
//            if (!args[1].isEmpty()) throw new InvalidAmountException();
//
//            var set = collectionController.uniqueOwners();
//            if(set.isEmpty())
//                console.println("Не найдено не пустых полей owner");
//            else
//                console.println(String.join(" ", set));
//        } catch (InvalidAmountException e) {
//            console.printError("Неправильное количество аргументов!");
//        }
//        return false;
//    }
//}
