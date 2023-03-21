package ru.itmo.prog.commands;

import ru.itmo.prog.controllers.CollectionController;
import ru.itmo.prog.exceptions.InvalidAmountException;
import ru.itmo.prog.utils.consoleShell.Console;



/**
 * Команда для добавления элемента в коллекцию
 * @author belovlaska
 */
public class FilterContainsName extends Command {

    private final Console console;
    private final CollectionController collectionController;

    public FilterContainsName(Console console, CollectionController collectionController) {
        super("filter_contains_name <name>", "вывести элементы, значения поля name которых содержит заданную подстроку");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new InvalidAmountException();

            var list = collectionController.listContainsName(args[1]);
            if(list.isEmpty())
                console.println("Продуктов, у которых поле name содержит данную подстроку не обнаружено!");
            else
                list.forEach(console::println);
        } catch (InvalidAmountException e) {
            console.printError("Неправильное количество аргументов!");
        }
        return false;
    }

}
