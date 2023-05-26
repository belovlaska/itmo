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
// * Команда для добавления элемента в коллекцию
// * @author belovlaska
// */
//public class AddElement extends Command {
//
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public AddElement(Console console, CollectionManager collectionController) {
//        super("add {element}", "добавить новый элемент в коллекцию");
//        this.console = console;
//        this.collectionController = collectionController;
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        try {
//            if (!args[1].isEmpty()) throw new InvalidAmountException();
//            collectionController.addToCollection((new ProductInput(console)).make());
//            console.println("Продукт успешно добавлен!");
//            return true;
//        } catch (InvalidAmountException exception) {
//            console.printError("Неправильное количество аргументов!");
//        } catch (InvalidFormException | InvalidValueException e) {
//            console.printError("Поля продукта не валидны! Продукт не создан!");
//        } catch (IncorrectScriptException ignored) {}
//        return false;
//    }
//}
