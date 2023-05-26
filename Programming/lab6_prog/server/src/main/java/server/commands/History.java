//package server.commands;
//
//import server.managers.CommandManager;
//import server.utils.consoleShell.Console;
//
///**
// * Команда для вывода последних 8 команд
// * @author belovlaska
// */
//public class History extends Command {
//    private final Console console;
//    private final CommandManager commandController;
//    public History(Console console, CommandManager commandController) {
//        super("history", "вывести последние 8 команд");
//        this.console = console;
//        this.commandController = commandController;
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        if(!args[1].isEmpty()){
//            console.println("Please enter the command in the correct format");
//            return false;
//        }
//        console.println(String.join("\n", commandController.get8CommandHistory()));
//        return true;
//    }
//}
