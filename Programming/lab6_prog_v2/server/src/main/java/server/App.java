package server;

import server.commands.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.handlers.RequestHandler;
import server.managers.CollectionManager;
import server.managers.CommandManager;
import server.managers.DumpManager;
import server.network.RecievingManager;
import server.network.TCPServer;

public class App {

    public static final int PORT = 1821;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static Logger logger = LogManager.getLogger("ServerLogger");




    public static void main(String[] args) {
        String filename = "data.json";
        if(args.length == 1) filename = args[0];
        DumpManager dumpManager = new DumpManager(filename);
        CollectionManager collectionManager = new CollectionManager(dumpManager);
        CommandManager commandManager = new CommandManager(
                new AddCommand(collectionManager),
                new AddIfMaxCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(),
                new HistoryCommand(),
                new InfoCommand(collectionManager),
                new PrintUniqueOwnerCommand(collectionManager),
                new FilterContainsNameCommand(collectionManager),
                new FilterStartsWithNameCommand(collectionManager),
                new RemoveLowerCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ShowCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new HelpCommand()
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        TCPServer server = new TCPServer(PORT, CONNECTION_TIMEOUT, new RecievingManager(requestHandler));
        server.run();
    }
}