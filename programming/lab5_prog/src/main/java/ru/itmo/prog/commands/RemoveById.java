package ru.itmo.prog.commands;

import ru.itmo.prog.controllers.CollectionController;
import ru.itmo.prog.exceptions.*;
import ru.itmo.prog.utils.consoleShell.Console;

/**
 * Команда для добавления элемента в коллекцию
 * @author belovlaska
 */
public class RemoveById extends Command {

    private final Console console;
    private final CollectionController collectionController;

    public RemoveById(Console console, CollectionController collectionController) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new InvalidAmountException();
            var id = Integer.parseInt(args[1]);
            var product = collectionController.getById(id);
            if(product == null) throw new MustBeNotEmptyException();

            collectionController.removeElement(product);
            console.println("Продукт успешно удален!");
            return true;
        } catch (InvalidAmountException exception) {
            console.printError("Неправильное количество аргументов!");
        } catch (MustBeNotEmptyException exception){
            console.printError("Продукта с таким ID в коллекции нет!");
        }
        return false;
    }
}
