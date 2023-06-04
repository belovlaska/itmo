package client.network;

import client.utils.console.Console;
import common.exceptions.ConnectionErrorException;
import common.exceptions.NotInDeclaredLimitsException;
import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseResult;


import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Runs the client.
 */
public class TCPClient {
    private static final Console console = new Console();
    private final String host;
    private final int port;

    private SocketChannel socketChannel;


    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Begins client operation.
     */
    public Response send(Request request) {
        var sendingManager = new SendingManager();
        try {
            connectToServer();
            sendingManager.send(console, request, socketChannel);
            var response = new RecievingManager().get(console, socketChannel);
            sendingManager.stopOutput();
            disconnect();
            return response;
        } catch (NotInDeclaredLimitsException exception) {
            console.printError("The client cannot be started!");
        } catch (ConnectionErrorException exception) {
            console.printError("An error occurred while trying to wait for connection!");
        } catch (IOException exception) {
            console.printError("An error occurred while trying to terminate the connection with the server!");
        }
        return new Response(ResponseResult.ERROR, null);
    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            console.println("The connection to the server has been successfully established.");
            console.println("Waiting for permission to exchange data...");
//            console.println("Permission to exchange data has been received.");
        } catch (IllegalArgumentException exception) {
            console.printError("The server address is entered incorrectly!");
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            console.printError("An error occurred while connecting to the server!");
            throw new ConnectionErrorException();
        }
    }

    private void disconnect() throws IOException {
        socketChannel.close();
    }
}
