package ru.itmo.prog.commands;

import ru.itmo.prog.utils.consoleShell.Console;

/**
 * Команда для исполнения скрипта
 * @author belovlaska
 */
public class ExecuteScript extends Command {

    private final Console console;
    public ExecuteScript(Console console) {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    @Override
    public boolean execute(String[] args) {
        if(args[1].isEmpty()){
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        console.println("Выполнение скрипта '" + args[1] + "':");
        return true;
    }
}
