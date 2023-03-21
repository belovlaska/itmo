package ru.itmo.prog;

import ru.itmo.prog.commands.*;
import ru.itmo.prog.controllers.CollectionController;
import ru.itmo.prog.controllers.CommandController;
import ru.itmo.prog.controllers.DumpController;
import ru.itmo.prog.models.Product;
import ru.itmo.prog.utils.Executor;
import ru.itmo.prog.utils.InputSteamer;
import ru.itmo.prog.utils.consoleShell.Console;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding","UTF-8");

        InputSteamer.setScanner(new Scanner(System.in));
        var console = new Console();

        if(args.length == 0){
            console.printError("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        var dumpController = new DumpController(args[0], console);
        var collectionController = new CollectionController(dumpController);

        Product.updateNextId(collectionController);
        collectionController.validateAll(console);

        var commandController = new CommandController(){
            {
                register("help", new Help(console, this));
                register("info", new Info(console, collectionController));
                register("show", new Show(console, collectionController));
                register("add", new AddElement(console, collectionController));
                register("update", new UpdateId(console, collectionController));
                register("remove_by_id", new RemoveById(console, collectionController));
                register("clear", new Clear(console, collectionController));
                register("save", new Save(console, collectionController));
                register("execute_script", new ExecuteScript(console));
                register("exit", new Exit(console));
                register("history", new History(console, this));
                register("add_if_max", new AddIfMax(console, collectionController));
                register("remove_lower", new RemoveLower(console, collectionController));
                register("filter_contains_name", new FilterContainsName(console, collectionController));
                register("filter_starts_with_name", new FilterStartsWithName(console, collectionController));
                register("print_unique_owner", new PrintUniqueOwner(console, collectionController));
            }
        };

        new Executor(console, commandController).fromConsole();
    }
}