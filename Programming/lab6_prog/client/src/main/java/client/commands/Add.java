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
import common.network.requests.AddRequest;
import common.network.responses.AddResponse;

import java.io.IOException;
import java.nio.channels.NotYetConnectedException;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 * @author belovlaska
 */
public class Add extends Command {
    private final Console console;
    private final TCPClient client;

    public Add(Console console, TCPClient client) {
        super("add {element}");
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
            console.println("* Making new product:");

            var newProduct = (new ProductInput(console)).make();
            var response = (AddResponse) client.sendAndReceiveCommand(new AddRequest(newProduct));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("New product with id=" + response.newId + " successfully added!");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Wrong amount of elements!");
            console.println("Using: '" + getName() + "'");
        } catch(NotYetConnectedException | IOException e) {
            console.printError("Connection error");
        } catch (InvalidFormException exception) {
            console.printError("Invalid fields! Product has not added!");
        } catch (APIException e) {
            console.printError(e.getMessage());
        } catch (InvalidValueException e){
            console.printError("Invalid value!");
        } catch (IncorrectInputInScriptException ignored) {}
        return false;
    }
}
