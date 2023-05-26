package client.commands;



import client.input.ProductInput;
import common.exceptions.APIException;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.InvalidFormException;
import common.exceptions.WrongAmountOfElementsException;
import common.exceptions.*;
import client.network.TCPClient;
import client.utils.console.Console;
import common.network.requests.AddIfMaxRequest;
import common.network.responses.AddIfMaxResponse;

import java.io.IOException;
import java.nio.channels.NotYetConnectedException;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его цена выше максимальной.
 * @author belovlaska
 */
public class AddIfMax extends Command {
    private final Console console;
    private final TCPClient client;

    public AddIfMax(Console console, TCPClient client) {
        super("add_if_max {element}");
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
            console.println("* Making new product (add_if_max):");

            var newProduct = (new ProductInput(console)).make();
            var response = (AddIfMaxResponse) client.sendAndReceiveCommand(new AddIfMaxRequest(newProduct));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            if (!response.isAdded) {
                console.println("Product has not added, coordinates " + newProduct.getCoordinates() + "are not max");
                return true;
            }

            console.println("New product with id=" + response.newId + " successfully added!");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Wrong amount of elements!");
            console.println("Using: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Invalid fields! Product has not added!");
        } catch(NotYetConnectedException | IOException e) {
            console.printError("Connection error");
        } catch (APIException e) {
            console.printError(e.getMessage());
        } catch (InvalidValueException e){
            console.printError("Invalid value!");
        } catch (IncorrectInputInScriptException ignored) {}
        return false;
    }
}
