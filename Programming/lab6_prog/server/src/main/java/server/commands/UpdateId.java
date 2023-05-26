//package server.commands;
//
//import server.managers.CollectionManager;
//import server.models.Product;
//import server.models.ProductInput;
//import server.utils.consoleShell.Console;
//import server.exceptions.*;
//
///**
// * Команда для обновления значения элемента коллекции по айди
// * @author belovlaska
// */
//public class UpdateId extends Command {
//
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public UpdateId(Console console, CollectionManager collectionController) {
//        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
//        this.console = console;
//        this.collectionController = collectionController;
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        try {
//            if (args[1].isEmpty()) throw new InvalidAmountException();
//            var id = Integer.parseInt(args[1]);
//            var product = collectionController.getById(id);
//            if(product == null) throw new MustBeNotEmptyException();
//
//            console.println("Введите новые данные продукта:");
//            console.ps2();
//
//            Product newProduct = (new ProductInput(console)).make();
//            product.update(newProduct);
//
//            console.println("Продукт успешно обновлен!");
//            return true;
//        } catch (InvalidAmountException exception) {
//            console.printError("Неправильное количество аргументов!");
//        } catch (InvalidFormException | InvalidValueException e) {
//            console.printError("Поля продукта не валидны! Продукт не обновлен!");
//        } catch (MustBeNotEmptyException exception){
//            console.printError("Продукта с таким ID в коллекции нет!");
//        } catch (IncorrectScriptException ignored) {}
//        return false;
//    }
//}
