package ru.itmo.prog.commands;

import ru.itmo.prog.controllers.CollectionController;
import ru.itmo.prog.utils.consoleShell.Console;

/**
 * Команда для добавления элемента в коллекцию
 * @author belovlaska
 */
public class Clear extends Command {

    private final Console console;
    private final CollectionController collectionController;

    public Clear(Console console, CollectionController collectionController) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        if(!args[1].isEmpty()){
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        collectionController.clearCollection();
        console.println("Коллекция очищена!");
        return true;
    }
}
