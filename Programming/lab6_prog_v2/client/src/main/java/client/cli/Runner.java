package client.cli;

import client.input.ProductInput;
import client.network.TCPClient;
import client.utils.InputSteamer;
import client.utils.console.Console;
import common.domain.Product;
import common.exceptions.*;
import common.interaction.Request;
import common.interaction.ResponseResult;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Runner {
    public enum ExitCode {
        OK,
        ERROR,
        OBJECT,
        SCRIPT,
        EXIT
    }

    private final Console console;
    private final TCPClient client;
    private Scanner userScanner;

    private final Stack<File> scriptStack = new Stack<>();
    private final Stack<Scanner> scannerStack = new Stack<>();

    public Runner(Console console, TCPClient client) {
        this.console = console;
        this.client = client;
        InputSteamer.setScanner(new Scanner(System.in));
        userScanner = InputSteamer.getScanner();
    }

    public void run() {
        while (true) {
            String[] userCommand;
            var input = handleUserInput();
            var exitCode = input.getRight();
            userCommand = input.getLeft();
            try{
                var request = handleRequest(exitCode, userCommand);
                if(request != null) {
                    var response = client.send(request);
                }
                // process response - if exit, set running = false
            } catch (IncorrectInputInScriptException e) {
                console.printError("Script execution aborted!");
                while (!scannerStack.isEmpty()) {
                    userScanner.close();
                    userScanner = scannerStack.pop();
                }
                scriptStack.clear();
                var response = client.send(new Request());
            }
            var response = client.send(new Request(userCommand[0], userCommand[1]));
        }
    }

    private Pair<String[], ExitCode> handleUserInput(){
        String userInput;
        String[] userCommand;
        ExitCode exitCode;
//        var userScanner = InputSteamer.getScanner();
        int rewriteAttempts = 0;
        do {
            try {
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
                case "exit":
                    return ExitCode.EXIT;
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

    private Request handleRequest(ExitCode exitCode, String[] userCommand) throws IncorrectInputInScriptException {
        try {
//            var userScanner = InputSteamer.getScanner();
            if (fileMode() && exitCode == ExitCode.ERROR)
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
                case EXIT:
                    console.println("Client end work");
                    System.exit(0);
            }
        } catch (FileNotFoundException exception) {
            console.printError("The script file was not found!");
        } catch (ScriptRecursionException exception) {
            console.printError("Scripts cannot be called recursively!");
            throw new IncorrectInputInScriptException();
        }
        return null;
    }


    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}
