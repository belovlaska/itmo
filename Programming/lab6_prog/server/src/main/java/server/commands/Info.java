//package server.commands;
//
//import server.managers.CollectionManager;
//import server.utils.consoleShell.Console;
//
//import java.time.LocalDateTime;
//
///**
// * Команда для вывода информации о коллекции
// * @author belovlaska
// */
//public class Info extends Command{
//    private final Console console;
//    private final CollectionManager collectionController;
//
//    public Info(Console console, CollectionManager collectionController) {
//        super("info", "вывести информацию о коллекции");
//        this.console = console;
//        this.collectionController = collectionController;
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        if(!args[1].isEmpty()){
//            console.println("Please enter the command in the correct format");
//            return false;
//        }
//            LocalDateTime lastInitTime = collectionController.getLastInitTime();
//            String lastInitTimeStr = (lastInitTime == null) ? "инициализации в данной сессии еще не происходило" :
//                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
//            LocalDateTime lastSaveTime = collectionController.getLastSaveTime();
//            String lastSaveTimeStr = (lastSaveTime == null) ? "Сохранения в данной сессии еще не происходило" :
//                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
//            console.println("информация о коллекции:");
//            console.println("Тип коллекции: " + collectionController.getType());
//            console.println("Размер коллекции: " + collectionController.getSize());
//            console.println("Дата и время последней инициализации: " + lastInitTimeStr);
//            console.println("Дата и время последнего сохранения: " + lastSaveTimeStr);
//            return true;
//    }
//}
