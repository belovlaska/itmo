package client.cli;

import client.App;
import client.commands.*;

import client.network.TCPClient;
import client.utils.InputSteamer;
import client.utils.console.Console;
import common.exceptions.ScriptRecursionException;
import common.utility.Commands;

import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Класс, запускающий введенные пользователем команды.
 * @author belovlaska
 */
public class Runner {
    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }

    private final Console console;
    private final TCPClient client;
    private final Map<String, Command> commands;

    private final Logger logger = App.logger;
    private final Set<String> scriptStack = new HashSet<>();

    public Runner(TCPClient client, Console console) {
        InputSteamer.setScanner(new Scanner(System.in));
        this.client = client;
        this.console = console;
        this.commands = new HashMap<>() {{
            put(Commands.HELP, new Help(console, client));
//            put(Commands.INFO, new Info(console, client));
//            put(Commands.SHOW, new Show(console, client));
            put(Commands.ADD, new Add(console, client));
//            put(Commands.UPDATE, new Update(console, client));
//            put(Commands.REMOVE_BY_ID, new RemoveById(console, client));
            put(Commands.CLEAR, new Clear(console, client));
            put(Commands.HISTORY, new History(console, client));
            put(Commands.EXECUTE_SCRIPT, new ExecuteScript(console));
            put(Commands.EXIT, new Exit(console));
            put(Commands.ADD_IF_MAX, new AddIfMax(console, client));
//            put(Commands.ADD_IF_MIN, new AddIfMin(console, client));
//            put(Commands.SUM_OF_PRICE, new SumOfPrice(console, client));
//            put(Commands.FILTER_BY_PRICE, new FilterByPrice(console, client));
//            put(Commands.FILTER_CONTAINS_PART_NUMBER, new FilterContainsPartNumber(console, client));
        }};
    }

    boolean flag = false;
    Integer depth = 0;

    /**
     * Интерактивный режим
     */
    public void interactiveMode() {
        var userScanner = InputSteamer.getScanner();
        try {
            ExitCode commandStatus;
            String[] userCommand = {"", ""};

            do {
                console.ps1();
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandStatus = launchCommand(userCommand);
            } while (commandStatus != ExitCode.EXIT);

        } catch (NoSuchElementException exception) {
            console.printError("User input not found!");
        } catch (IllegalStateException exception) {
            console.printError("Unexpected error!");
        }
    }

    /**
     * Режим для запуска скрипта.
     * @param argument Аргумент скрипта
     * @return Код завершения.
     */
    public ExitCode scriptMode(String argument) {

        Integer maxDepth = 3;
        String[] userCommand = {"", ""};
        ExitCode commandStatus;
        scriptStack.add(argument);
        if (!new File(argument).exists()) {
            argument = "../" + argument;
        }
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = InputSteamer.getScanner();
            InputSteamer.setScanner(scriptScanner);
            InputSteamer.setFileMode(true);

            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                console.println(console.getPS1() + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    if(!scriptStack.add(userCommand[1]))
                    {
                        if(flag == false) {
                            InputSteamer.setScanner(tmpScanner);
                            InputSteamer.setFileMode(false);
                            maxDepth = setMaxDepth();
                            InputSteamer.setScanner(scriptScanner);
                            InputSteamer.setFileMode(true);
                            flag = true;
                        }
                        depth ++;
                    }
                    if(depth > maxDepth) throw new ScriptRecursionException();
                }
                commandStatus = launchCommand(userCommand);
            } while (commandStatus == ExitCode.OK && scriptScanner.hasNextLine());

            InputSteamer.setScanner(tmpScanner);
            InputSteamer.setFileMode(false);
            flag = false;
            depth = 0;

            if (commandStatus == ExitCode.ERROR && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                console.println("Check your script!");
            }

            return commandStatus;

        } catch (FileNotFoundException exception) {
            console.printError("File not found!");
        } catch (NoSuchElementException exception) {
            console.printError("File is empty!");
        } catch (ScriptRecursionException exception) {
            console.printError("Maximum recursion depth exceeded!");
        } catch (IllegalStateException exception) {
            console.printError("Unexpected error!");
            System.exit(0);
        } finally {
            scriptStack.remove(argument);
        }
        return ExitCode.ERROR;
    }

    /**
     * Запускает команду.
     * @param userCommand Команда для запуска
     * @return Код завершения.
     */
    private ExitCode launchCommand(String[] userCommand) {
        if (userCommand[0].equals("")) return ExitCode.OK;
        var command = commands.get(userCommand[0]);

        if (command == null) {
            console.printError("Command '" + userCommand[0] + "' not found. Enter 'help' for help");
            return ExitCode.ERROR;
        }

        switch (userCommand[0]) {
            case "exit" -> {
                if (!commands.get("exit").apply(userCommand)) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            case "execute_script" -> {
                if (!commands.get("execute_script").apply(userCommand)) return ExitCode.ERROR;
                else return scriptMode(userCommand[1]);
            }
            default -> { if (!command.apply(userCommand)) return ExitCode.ERROR; }
        }

        return ExitCode.OK;



    }


            public Integer setMaxDepth(){
                console.println("Enter recursion max depth");
                var strMaxDepth = InputSteamer.getScanner().nextLine().trim();
                return Integer.parseInt(strMaxDepth);
            }
}
