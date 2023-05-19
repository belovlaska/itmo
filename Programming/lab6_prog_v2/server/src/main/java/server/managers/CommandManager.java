package server.managers;


//import common.exceptions.HistoryIsEmptyException;
import server.commands.Command;
import server.handlers.ResponseOutputer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The CommandManager class is a singleton class that manages all the commands in the CLI
 */
public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 8;

    private final String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private final List<Command> commands;
    private final Command addCommand;
    private final Command addIfMaxCommand;
    private final Command clearCommand;
    private final Command executeScriptCommand;
    private final Command exitCommand;
    private final Command historyCommand;
    private final Command infoCommand;
    private final Command printUniqueOwnerCommand;
    private final Command filterContainsNameCommand;
    private final Command filterStartsWithNameCommand;
    private final Command removeLowerCommand;
    private final Command removeByIdCommand;
    private final Command showCommand;
    private final Command updateCommand;
    private final Command helpCommand;

    public CommandManager(Command addCommand, Command addIfMaxCommand, Command clearCommand,
                          Command executeScriptCommand, Command exitCommand, Command historyCommand,
                          Command infoCommand, Command printUniqueOwnerCommand, Command filterContainsNameCommand,
                          Command filterStartsWithNameCommand, Command removeLowerCommand, Command removeByIdCommand,
                          Command showCommand, Command updateCommand,
                          Command helpCommand) {
        this.addCommand = addCommand;
        this.addIfMaxCommand = addIfMaxCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.historyCommand = historyCommand;
        this.infoCommand = infoCommand;
        this.printUniqueOwnerCommand = printUniqueOwnerCommand;
        this.filterContainsNameCommand = filterContainsNameCommand;
        this.filterStartsWithNameCommand = filterStartsWithNameCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.showCommand = showCommand;
        this.updateCommand = updateCommand;
        this.helpCommand = helpCommand;

        commands = new ArrayList<>(Arrays.asList(addCommand, addIfMaxCommand, clearCommand, executeScriptCommand,
                exitCommand, historyCommand, infoCommand, printUniqueOwnerCommand, filterContainsNameCommand,
                filterStartsWithNameCommand, removeLowerCommand, removeByIdCommand, showCommand,
                updateCommand, helpCommand));
    }


    /**
     * @return List of manager's commands.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Adds command to command history.
     *
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore) {

        for (Command command : commands) {
            if (command.getName().equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                    commandHistory[i] = commandHistory[i - 1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Prints info about the all commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean help(String stringArgument, Object objectArgument) {
        if (helpCommand.execute(stringArgument, objectArgument)) {
            for (Command command : commands) {
                ResponseOutputer.appendTable(command.getName() + " " + command.getUsage(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean info(String stringArgument, Object objectArgument) {
        return infoCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean show(String stringArgument, Object objectArgument) {
        return showCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean add(String stringArgument, Object objectArgument) {
        return addCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean update(String stringArgument, Object objectArgument) {
        return updateCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeById(String stringArgument, Object objectArgument) {
        return removeByIdCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean clear(String stringArgument, Object objectArgument) {
        return clearCommand.execute(stringArgument, objectArgument);
    }



    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean exit(String stringArgument, Object objectArgument) {
        return exitCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean executeScript(String stringArgument, Object objectArgument) {
        return executeScriptCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean addIfMax(String stringArgument, Object objectArgument) {
        return addIfMaxCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean printUniqueOwner(String stringArgument, Object objectArgument) {
        return printUniqueOwnerCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Prints the history of used commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean history(String stringArgument, Object objectArgument) {
        if (historyCommand.execute(stringArgument, objectArgument)) {
//            try {
//                if (commandHistory.length == 0) throw new HistoryIsEmptyException();
//
//                ResponseOutputer.appendln("Last used commands:");
                for (String command : commandHistory) {
                    if (command != null) ResponseOutputer.appendln(" " + command);
                }
                return true;
//            } catch (HistoryIsEmptyException exception) {
//                ResponseOutputer.appendln("No commands have been used yet!");
//            }
        }
        return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean filterContainsName(String stringArgument, Object objectArgument) {
        return filterContainsNameCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean filterStartsWithName(String stringArgument, Object objectArgument) {
        return filterStartsWithNameCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeLower(String stringArgument, Object objectArgument) {
        return removeLowerCommand.execute(stringArgument, objectArgument);
    }
}