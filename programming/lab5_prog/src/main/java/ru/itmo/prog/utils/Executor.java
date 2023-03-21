package ru.itmo.prog.utils;

import ru.itmo.prog.controllers.CommandController;
import ru.itmo.prog.exceptions.ScriptRecursionException;
import ru.itmo.prog.utils.consoleShell.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Executor {
    public enum ExitCode{
        OK,
        ERROR,
        EXIT;
    }

    private final Console console;
    private final CommandController commandController;
    private final Set<String> scriptStack = new HashSet<>();

    public Executor(Console console, CommandController commandController) {
        this.console = console;
        this.commandController = commandController;
    }

    /**
     * Интерактивный режим
     */
    public void fromConsole(){
        var userScanner = InputSteamer.getScanner();
        try {
            ExitCode exitCode = ExitCode.OK;
            String[] inputCommand = {"", ""};

            while (exitCode != ExitCode.EXIT){
                console.ps1();
                inputCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                inputCommand[1] = inputCommand[1].trim();

                commandController.addToHistory(inputCommand[0]);
                exitCode = apply(inputCommand);
            }
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }

    /**
     * Режим для запуска скрипта.
     * @param argument Аргумент скрипта
     * @return Код завершения.
     */
    public ExitCode fromScript(String argument){
        String[] inputCommand = {"", ""};
        ExitCode exitCode;
        scriptStack.add(argument);

        if (!new File(argument).exists()) {
            argument = "../" + argument;
        }

        try(Scanner scriptScanner = new Scanner(new File(argument))){
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = InputSteamer.getScanner();
            InputSteamer.setScanner(scriptScanner);
            InputSteamer.setFileMode(true);

            do{
                inputCommand = (scriptScanner.nextLine().trim()+" ").split(" ", 2);
                inputCommand[1] = inputCommand[1].trim();

                while (scriptScanner.hasNextLine() && inputCommand[0].isEmpty()) {
                    inputCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    inputCommand[1] = inputCommand[1].trim();
                }

                console.println(console.getPS1() + String.join(" ", inputCommand));
                if(inputCommand[0].equals("execute_script")){
                    if(!scriptStack.add(inputCommand[1])) throw new ScriptRecursionException();
                }
                exitCode = apply(inputCommand);
            } while (exitCode == ExitCode.OK && scriptScanner.hasNextLine());

            InputSteamer.setScanner(tmpScanner);
            InputSteamer.setFileMode(false);

            if (exitCode == ExitCode.ERROR && !(inputCommand[0].equals("execute_script") && !inputCommand[1].isEmpty())) {
                console.println("Проверьте скрипт на корректность введенных данных!");
            }

            return exitCode;
        } catch (FileNotFoundException exception) {
            console.printError("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            console.printError("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            console.printError("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(argument);
        }
        return ExitCode.ERROR;
    }

    /**
     * Apply the command.
     * @param inputCommand Команда для запуска
     * @return Код завершения.
     */
    private ExitCode apply(String[] inputCommand){
        if(inputCommand[0].equals("")) return ExitCode.OK;
        var command = commandController.getCommands().get(inputCommand[0]);

        if (command == null) {
            console.printError("Команда '" + inputCommand[0] + "' не найдена. Наберите 'help' для справки");
            return ExitCode.ERROR;
        }

        if(inputCommand[0].equals("exit")){
            if(command.execute(inputCommand)) return ExitCode.EXIT;
            else return ExitCode.ERROR;
        } else if (inputCommand[0].equals("execute_script")) {
            if(command.execute(inputCommand)) return fromScript(inputCommand[1]);
            else return ExitCode.ERROR;
        }
        else
            if(!command.execute(inputCommand))
                return ExitCode.ERROR;

        return ExitCode.OK;
    }
}
