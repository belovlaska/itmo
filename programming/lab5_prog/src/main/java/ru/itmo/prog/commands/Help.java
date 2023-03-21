package ru.itmo.prog.commands;

import ru.itmo.prog.controllers.CommandController;
import ru.itmo.prog.utils.consoleShell.Console;

/**
 * Команда для вывода справки о доступных командах
 * @author belovlaska
 */
public class Help extends Command {
    private final Console console;
    private final CommandController commandController;
    public Help(Console console, CommandController commandController) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandController = commandController;
    }

    @Override
    public boolean execute(String[] args) {
    if(!args[1].isEmpty()){
        console.println("Пожалуйста введите команду в правильном формате");
        return false;
    }
        commandController.getCommands().values()
                .forEach(command -> console.printTable(command.getName(), command.getDescription()));
        return true;
    }
}
