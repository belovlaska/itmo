package client.network;

import client.utils.console.Console;
import common.interaction.Response;
import common.interaction.ResponseResult;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.nio.channels.SocketChannel;

public class RecievingManager {
    private ObjectInputStream inputStream;

    public Response get(Console console, SocketChannel socketChannel){
        try {
            inputStream = new ObjectInputStream(socketChannel.socket().getInputStream());
            var response = (Response) inputStream.readObject();
            if(response.getResponseResult() == ResponseResult.ERROR){
                console.printError("Error while executing command on server!");
            }
            console.print(response.getResponseBody());
            return response;

        } catch (InvalidClassException | NotSerializableException exception) {
            console.printError("An error occurred while sending data to the server!");
            console.printError(exception);
            console.printError(inputStream);
        } catch (ClassNotFoundException exception) {
            console.printError("An error occurred while reading the received data!");
        } catch (IOException exception) {
            console.printError("The connection to the server is broken!");
        }
        return new Response(ResponseResult.ERROR, null);
    }
}
