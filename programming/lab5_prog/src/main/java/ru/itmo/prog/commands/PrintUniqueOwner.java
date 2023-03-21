package ru.itmo.prog.commands;

import ru.itmo.prog.controllers.CollectionController;
import ru.itmo.prog.exceptions.InvalidAmountException;
import ru.itmo.prog.utils.consoleShell.Console;

public class PrintUniqueOwner extends Command{

    private final Console console;
    private final CollectionController collectionController;

    public PrintUniqueOwner(Console console, CollectionController collectionController) {
        super("print_unique_owner", "вывести уникальные значения поля owner всех элементов в коллекции");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new InvalidAmountException();

            var set = collectionController.uniqueOwners();
            if(set.isEmpty())
                console.println("Не найдено не пустых полей owner");
            else
                console.println(String.join(" ", set));
        } catch (InvalidAmountException e) {
            console.printError("Неправильное количество аргументов!");
        }
        return false;
    }
}
