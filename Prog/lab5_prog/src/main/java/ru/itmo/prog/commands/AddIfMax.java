package ru.itmo.prog.commands;

import ru.itmo.prog.controllers.CollectionController;
import ru.itmo.prog.exceptions.IncorrectScriptException;
import ru.itmo.prog.exceptions.InvalidAmountException;
import ru.itmo.prog.exceptions.InvalidValueException;
import ru.itmo.prog.exceptions.InvalidFormException;
import ru.itmo.prog.models.ProductInput;
import ru.itmo.prog.utils.consoleShell.Console;

/**
 * Команда для добавления элемента в коллекцию, если его цена превышает максимальную цену этой коллекции
 * @author belovlaska
 */
public class AddIfMax extends Command {

    private final Console console;
    private final CollectionController collectionController;

    public AddIfMax(Console console, CollectionController collectionController) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его цена превышает максимальную цену этой коллекции");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new InvalidAmountException();
            var product = ((new ProductInput(console)).make());

            if(collectionController.greaterThanAll(product)) {
                collectionController.addToCollection(product);
                console.println("Продукт успешно добавлен!");
            }
            else
                console.println("Продукт не добавлен, цена не максимальная");
            return true;
        } catch (InvalidAmountException exception) {
            console.printError("Неправильное количество аргументов!");
        } catch (InvalidFormException | InvalidValueException e) {
            console.printError("Поля продукта не валидны! Продукт не создан!");
        } catch (IncorrectScriptException ignored) {}
        return false;
    }
}
