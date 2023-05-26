//package server.commands;
//
//import server.managers.CollectionManager;
//import server.exceptions.InvalidAmountException;
//import server.utils.consoleShell.Console;
//
//
///**
// * Команда для вывода элементов, значения поля name которых начинается с заданной подстроки
// * @author belovlaska
// */
//public class FilterStartsWithName extends Command {
//
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public FilterStartsWithName(Console console, CollectionManager collectionController) {
//        super("filter_starts_with_name <name>", "вывести элементы, значения поля name которых начинается с заданной подстроки");
//        this.console = console;
//        this.collectionController = collectionController;
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        try {
//            if (args[1].isEmpty()) throw new InvalidAmountException();
//
//            var list = collectionController.listStartsWithName(args[1]);
//            if(list.isEmpty())
//                console.println("Продуктов, у которых поле name начинается с данной подстроки не обнаружено!");
//            else
//                list.forEach(console::println);
//        } catch (InvalidAmountException e) {
//            console.printError("Неправильное количество аргументов!");
//        }
//        return false;
//    }
//
//}
