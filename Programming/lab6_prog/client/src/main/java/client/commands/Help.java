package client.commands;


import client.network.TCPClient;
import client.utils.console.Console;
import common.network.requests.HelpRequest;
import common.network.responses.HelpResponse;

import java.io.IOException;
import java.nio.channels.NotYetConnectedException;

/**
 * Команда 'help'. Выводит справку по доступным командам
 * @author belovlaska
 */
public class Help extends Command {
    private final Console console;
    private final TCPClient client;

    public Help(Console console, TCPClient client) {
        super("help");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.printError("Wrong amount of elements!");
            console.println("Using: '" + getName() + "'");
            return false;
        }

        try {
            var response = (HelpResponse) client.sendAndReceiveCommand(new HelpRequest());
            console.print(response.helpMessage);
            return true;
        } catch(NotYetConnectedException | IOException e) {
            e.getMessage();
            e.printStackTrace();
            //console.printError("Connection error ");
        }
        return false;
    }
}
