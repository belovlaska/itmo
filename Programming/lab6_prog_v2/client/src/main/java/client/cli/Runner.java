package client.cli;


import client.input.ProductInput;
import client.utils.InputSteamer;
import client.utils.console.Console;
import common.domain.Product;
import common.exceptions.*;
import common.interaction.ResponseResult;
import common.interaction.Request;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Класс, запускающий введенные пользователем команды.
 *
 * @author belovlaska
 */
public class Runner {


    public enum ExitCode {
        OK,
        ERROR,
        OBJECT,
        SCRIPT
    }

    private static Console console = new Console();
    private Scanner userScanner;


    private final Stack<File> scriptStack = new Stack<>();
    private final Stack<Scanner> scannerStack = new Stack<>();


    public Runner(Scanner userScanner) {
        this.userScanner = userScanner;
        InputSteamer.setScanner(userScanner);
    }


    /**
     * Receives user input.
     *
     * @param serverResponseResult Last server's response code.
     * @return New request to server.
     */
    public Request handle(ResponseResult serverResponseResult) {
        String[] userCommand;
        try {
            var input = handleUserInput(serverResponseResult);
            var exitCode = input.getRight();
            userCommand = input.getLeft();

            var request = handleRequest(serverResponseResult, exitCode, userCommand);
            if (request != null) {
                return request;
            }
        } catch (IncorrectInputInScriptException exception) {
            console.printError("Script execution aborted!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request();
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    private Request handleRequest(ResponseResult serverResponseResult, ExitCode exitCode, String[] userCommand) throws IncorrectInputInScriptException {
        try {
            if (fileMode() && (serverResponseResult == ResponseResult.ERROR || exitCode == ExitCode.ERROR))
                throw new IncorrectInputInScriptException();
            switch (exitCode) {
                case OBJECT:
                    try {
                        Product product = generateProduct();
                        return new Request(userCommand[0], userCommand[1], product);
                    } catch (InvalidValueException e) {
                        console.printError("Invalid value!");
                    } catch (InvalidFormException e) {
                        console.printError("Invalid form!");
                    }
                case SCRIPT:
                    File scriptFile = new File(userCommand[1]);
                    if (!scriptFile.exists()) throw new FileNotFoundException();
                    if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                        throw new ScriptRecursionException();
                    scannerStack.push(userScanner);
                    scriptStack.push(scriptFile);
                    userScanner = new Scanner(scriptFile);
                    console.println("Execute script '" + scriptFile.getName() + "'...");
                    break;
            }
        } catch (FileNotFoundException exception) {
            console.printError("The script file was not found!");
        } catch (ScriptRecursionException exception) {
            console.printError("Scripts cannot be called recursively!");
            throw new IncorrectInputInScriptException();
        }
        return null;
    }

    private Pair<String[], ExitCode> handleUserInput(ResponseResult serverResponseResult) throws IncorrectInputInScriptException {
        String userInput;
        String[] userCommand;
        ExitCode exitCode;
        int rewriteAttempts = 0;
        do {
            try {
                if (fileMode() && (serverResponseResult == ResponseResult.ERROR))
                    throw new IncorrectInputInScriptException();
                while (fileMode() && !userScanner.hasNextLine()) {
                    userScanner.close();
                    userScanner = scannerStack.pop();
                    console.println("Going back to the script '" + scriptStack.pop().getName() + "'...");
                }
                InputSteamer.setScanner(userScanner);
                if (fileMode()) {
                    InputSteamer.setFileMode(true);
                    userInput = userScanner.nextLine();
                    if (!userInput.isEmpty()) {
                        console.ps1();
                        console.println(userInput);
                    }
                } else {
                    InputSteamer.setFileMode(false);
                    console.ps1();
                    userInput = userScanner.nextLine();
                }
                userCommand = (userInput.trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
            } catch (NoSuchElementException | IllegalStateException exception) {
                console.printError("An error occurred while entering the command!");
                userCommand = new String[]{"", ""};
                rewriteAttempts++;
                int maxRewriteAttempts = 3;
                if (rewriteAttempts >= maxRewriteAttempts) {
                    console.printError("Exceeded the number of input attempts!");
                    System.exit(0);
                }
            }
            exitCode = processCommand(userCommand[0], userCommand[1]);
        } while (exitCode == ExitCode.ERROR && !fileMode() || userCommand[0].isEmpty());
        return new ImmutablePair<>(userCommand, exitCode);
    }

    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private ExitCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return ExitCode.ERROR;
                case "add":
                case "add_if_max":
                case "remove_lower":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ExitCode.OBJECT;
                case "clear":
                case "exit":
                case "help":
                case "history":
                case "info":
                case "print_unique_owner":
                case "show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ExitCode.SCRIPT;
                case "filter_contains_name":
                case "filter_starts_with_name":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<name>");
                    break;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID> {element}");
                    return ExitCode.OBJECT;
                default:
                    console.printError("Command '" + command + "' was not found. Type 'help' for help.");
                    return ExitCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            console.println("Using: '" + command + "'");
            return ExitCode.ERROR;
        }
        return ExitCode.OK;
    }


    private Product generateProduct() throws IncorrectInputInScriptException, InvalidValueException, InvalidFormException {
        ProductInput productInput = new ProductInput(console);
        return productInput.make();
    }


    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}