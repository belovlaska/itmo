package client.commands;

import client.input.ProductInput;
import client.network.TCPClient;
import client.utils.console.Console;
import common.exceptions.APIException;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.InvalidFormException;
import common.exceptions.InvalidValueException;
import common.exceptions.WrongAmountOfElementsException;
import common.exceptions.*;
import common.network.requests.ClearRequest;
import common.network.requests.HistoryRequest;
import common.network.responses.ClearResponse;
import common.network.responses.HistoryResponse;


import java.io.IOException;
import java.nio.channels.NotYetConnectedException;

/**
 * Команда 'history'. Выводит последние 8 команд.
 * @author belovlaska
 */
public class History extends Command {
    private final Console console;
    private final TCPClient client;

    public History(Console console, TCPClient client) {
        super("history");
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

            var response = (HistoryResponse) client.sendAndReceiveCommand(new HistoryRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.printError("Wrong amount of elements!!");
            console.println("Using: '" + getName() + "'");
        } catch(NotYetConnectedException | IOException e) {
            console.printError("Connection error");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}

