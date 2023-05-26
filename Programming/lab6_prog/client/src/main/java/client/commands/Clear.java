package client.commands;

import client.network.TCPClient;
import client.utils.console.Console;
import common.exceptions.*;
import common.network.requests.*;
import common.network.responses.*;

import java.io.IOException;
import java.nio.channels.NotYetConnectedException;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author belovlaska
 */
public class Clear extends Command {
    private final Console console;
    private final TCPClient client;

    public Clear(Console console, TCPClient client) {
        super("clear");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            var response = (ClearResponse) client.sendAndReceiveCommand(new ClearRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Collection cleared!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.printError("Wrong amount of elements!");
            console.println("Using: '" + getName() + "'");
        } catch(NotYetConnectedException | IOException e) {
            console.printError("Connection error");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}