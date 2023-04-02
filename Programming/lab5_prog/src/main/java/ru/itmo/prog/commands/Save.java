package ru.itmo.prog.commands;

import ru.itmo.prog.controllers.CollectionController;
import ru.itmo.prog.utils.consoleShell.Console;

/**
 * Команда для сохранения коллекции в файл
 * @author belovlaska
 */
public class Save extends Command {

    private final Console console;
    private final CollectionController collectionController;

    public Save(Console console, CollectionController collectionController) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        if(!args[1].isEmpty()){
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        collectionController.saveCollection();
        return true;
    }
}
