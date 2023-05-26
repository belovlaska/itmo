package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.handlers.CommandHandler;
import server.managers.CollectionManager;
import server.managers.DumpManager;
import server.managers.CommandManager;


import server.commands.*;

import common.utility.Commands;
import server.network.ReceivingManager;
import server.network.TCPServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Серверная часть приложения.
 * @author belovlaska
 */
public class App {
    public static final int PORT = 18019;

    public static Logger logger = LogManager.getLogger("ServerLogger");

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Enter filename as argument of command line");
            System.exit(1);
        }

        var dumpManager = new DumpManager(args[0]);
        var repository = new CollectionManager(dumpManager);
        if(!repository.validateAll()) {
            logger.fatal("Invalid products in file!");
            System.exit(2);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(repository::save));

        var commandManager = new CommandManager() {{
            register(Commands.HELP, new Help(this));
//            register(Commands.INFO, new Info(repository));
//            register(Commands.SHOW, new Show(repository));
//            register(Commands.ADD, new Add(repository));
//            register(Commands.UPDATE, new Update(repository));
//            register(Commands.REMOVE_BY_ID, new RemoveById(repository));
//            register(Commands.CLEAR, new Clear(repository));
//            register(Commands.HISTORY, new History(repository));
//            register(Commands.ADD_IF_MAX, new AddIfMax(repository));
//            register(Commands.ADD_IF_MIN, new AddIfMin(repository));
//            register(Commands.SUM_OF_PRICE, new SumOfPrice(repository));
//            register(Commands.FILTER_BY_PRICE, new FilterByPrice(repository));
//            register(Commands.FILTER_CONTAINS_PART_NUMBER, new FilterContainsPartNumber(repository));
        }};

        try {
            ReceivingManager receivingManager = new ReceivingManager();
            var server = new TCPServer( PORT, receivingManager);
            server.setAfterHook(repository::save);
            server.start();
        } catch (Exception e) {
            logger.fatal(e.getMessage());
        }
    }
}
