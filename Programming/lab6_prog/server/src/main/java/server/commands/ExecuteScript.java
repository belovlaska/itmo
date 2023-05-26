//package server.commands;
//
//import server.utils.consoleShell.Console;
//
///**
// * Команда для исполнения скрипта
// * @author belovlaska
// */
//public class ExecuteScript extends Command {
//
//
//    String strMaxDepth;
//    Integer maxDepth = 3;
//    private final Console console;
//    public ExecuteScript(Console console) {
//        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
//        this.console = console;
//    }
//
//
//    @Override
//    public boolean execute(String[] args) {
//        if(args[1].isEmpty()){
//            console.println("Please enter the command in the correct format");
//            return false;
//        }
//        console.println("Выполнение скрипта '" + args[1] + "':");
//        return true;
//    }
//}
