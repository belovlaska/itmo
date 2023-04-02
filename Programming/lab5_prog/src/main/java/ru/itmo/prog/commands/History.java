package ru.itmo.prog.commands;

import ru.itmo.prog.controllers.CommandController;
import ru.itmo.prog.utils.consoleShell.Console;

/**
 * Команда для вывода последних 8 команд
 * @author belovlaska
 */
public class History extends Command {
    private final Console console;
    private final CommandController commandController;
    public History(Console console, CommandController commandController) {
        super("history", "вывести последние 8 команд");
        this.console = console;
        this.commandController = commandController;
    }

    @Override
    public boolean execute(String[] args) {
        if(!args[1].isEmpty()){
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        console.println(String.join("\n", commandController.get8CommandHistory()));
        return true;
    }
}
