package ru.itmo.prog.commands;

import ru.itmo.prog.utils.consoleShell.Console;

/**
 * Команда для завершения работы программы
 * @author belovlaska
 */
public class Exit extends Command {

    private final Console console;
    public Exit(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }

    @Override
    public boolean execute(String[] args) {
        if(!args[1].isEmpty()){
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        console.println("Завершение выполнения");
        return true;
    }
}
