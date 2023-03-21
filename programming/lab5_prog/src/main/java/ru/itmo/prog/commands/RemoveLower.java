package ru.itmo.prog.commands;

import ru.itmo.prog.controllers.CollectionController;
import ru.itmo.prog.exceptions.IncorrectScriptException;
import ru.itmo.prog.exceptions.InvalidAmountException;
import ru.itmo.prog.exceptions.InvalidValueException;
import ru.itmo.prog.exceptions.InvalidFormException;
import ru.itmo.prog.models.ProductInput;
import ru.itmo.prog.utils.consoleShell.Console;

/**
 * Команда для добавления элемента в коллекцию
 * @author belovlaska
 */
public class RemoveLower extends Command {

    private final Console console;
    private final CollectionController collectionController;

    public RemoveLower(Console console, CollectionController collectionController) {
        super("remove_lower {element}", "удалить из коллекции все элементы, чья цена ниже, чем у заданного");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new InvalidAmountException();
            var product = ((new ProductInput(console)).make());
            collectionController.removeLower(product);
            console.println("Продукты успешно удалены!");
            return true;
        } catch (InvalidAmountException exception) {
            console.printError("Неправильное количество аргументов!");
        } catch (InvalidFormException | InvalidValueException e) {
            console.printError("Поля продукта не валидны! Продукт не создан!");
        } catch (IncorrectScriptException ignored) {}
        return false;
    }
}
