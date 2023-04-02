package ru.itmo.prog.commands;


import ru.itmo.prog.controllers.CollectionController;
import ru.itmo.prog.utils.consoleShell.Console;


    /**
     * Команда для вывода всех элементов коллекции
     * @author belovlaska
     */
    public class Show extends Command {
        private final Console console;
        private final CollectionController collectionController;

        public Show(Console console, CollectionController collectionController) {
            super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
            this.console = console;
            this.collectionController = collectionController;
        }

        @Override
        public boolean execute(String[] args) {
            if(!args[1].isEmpty()){
                console.println("Пожалуйста введите команду в правильном формате");
                return false;
            }
            console.println(collectionController);
            return true;
    }

}
