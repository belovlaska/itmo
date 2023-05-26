//package server.commands;
//
//import server.managers.CollectionManager;
//import server.exceptions.IncorrectScriptException;
//import server.exceptions.InvalidAmountException;
//import server.exceptions.InvalidValueException;
//import server.exceptions.InvalidFormException;
//import server.models.ProductInput;
//import server.utils.consoleShell.Console;
//
///**
// * Команда для удаления из коллекции всех элементов, чья цена ниже, чем у заданного
// * @author belovlaska
// */
//public class RemoveLower extends Command {
//
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public RemoveLower(Console console, CollectionManager collectionController) {
//        super("remove_lower {element}", "удалить из коллекции все элементы, чья цена ниже, чем у заданного");
//        this.console = console;
//        this.collectionController = collectionController;
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        try {
//            if (!args[1].isEmpty()) throw new InvalidAmountException();
//            var product = ((new ProductInput(console)).make());
//            collectionController.removeLower(product);
//            console.println("Продукты успешно удалены!");
//            return true;
//        } catch (InvalidAmountException exception) {
//            console.printError("Неправильное количество аргументов!");
//        } catch (InvalidFormException | InvalidValueException e) {
//            console.printError("Поля продукта не валидны! Продукт не создан!");
//        } catch (IncorrectScriptException ignored) {}
//        return false;
//    }
//}
