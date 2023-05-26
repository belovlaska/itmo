//package server.commands;
//
//import server.managers.CollectionManager;
//import server.exceptions.InvalidAmountException;
//import server.utils.consoleShell.Console;
//
//
///**
// * Команда для вывода элементов, значения поля name которых содержит заданную подстроку
// * @author belovlaska
// */
//public class FilterContainsName extends Command {
//
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public FilterContainsName(Console console, CollectionManager collectionController) {
//        super("filter_contains_name <name>", "вывести элементы, значения поля name которых содержит заданную подстроку");
//        this.console = console;
//        this.collectionController = collectionController;
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        try {
//            if (args[1].isEmpty()) throw new InvalidAmountException();
//
//            var list = collectionController.listContainsName(args[1]);
//            if(list.isEmpty())
//                console.println("Продуктов, у которых поле name содержит данную подстроку не обнаружено!");
//            else
//                list.forEach(console::println);
//        } catch (InvalidAmountException e) {
//            console.printError("Неправильное количество аргументов!");
//        }
//        return false;
//    }
//
//}
