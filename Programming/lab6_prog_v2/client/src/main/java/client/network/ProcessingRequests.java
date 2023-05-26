package client.network;

import client.cli.Runner;
import client.utils.console.Console;
import common.exceptions.ConnectionErrorException;
import common.exceptions.NotInDeclaredLimitsException;
import common.interaction.Request;
import common.interaction.Response;

import java.io.*;
import java.util.function.Consumer;

public class ProcessingRequests {
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;
    private final Runner runner;

    public ProcessingRequests(Runner runner){
        this.runner = runner;
    }

    public boolean processRequestToServer(Consumer<Boolean> reconnectHook) {
        var console = new Console();
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = serverResponse != null ? runner.handle(serverResponse.getResponseResult()) :
                        runner.handle(null);
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                console.print(serverResponse.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
                console.printError("An error occurred while sending data to the server!");
                console.printError(exception);
                console.printError(serverReader);
            } catch (ClassNotFoundException exception) {
                console.printError("An error occurred while reading the received data!");
            } catch (IOException exception) {
                console.printError("The connection to the server is broken!");
                reconnectHook.accept(requestToServer.getCommandName().equals("exit"));
            }
        } while (!requestToServer.getCommandName().equals("exit"));
        return false;
    }

    public void setServerWriter(ObjectOutputStream serverWriter) {
        this.serverWriter = serverWriter;
    }

    public void setServerReader(ObjectInputStream serverReader) {
        this.serverReader = serverReader;
    }
}
