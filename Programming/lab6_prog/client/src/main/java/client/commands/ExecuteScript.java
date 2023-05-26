package client.commands;


import client.utils.console.Console;

/**
 * Команда 'execute_script'. Выполнить скрипт из файла.
 * @author belovlaska
 */
public class ExecuteScript extends Command {
    private final Console console;

    public ExecuteScript(Console console) {
        super("execute_script <file_name>");
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (arguments[1].isEmpty()) {
            console.println("Using: '" + getName() + "'");
            return false;
        }

        console.println("Executing script '" + arguments[1] + "'...");
        return true;
    }
}
