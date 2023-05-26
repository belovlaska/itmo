package client.network;

import client.cli.Runner;
import client.utils.console.Console;
import common.exceptions.ConnectionErrorException;
import common.exceptions.NotInDeclaredLimitsException;


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
    private final int reconnectionTimeout;
    private int reconnectionAttempts;
    private final int maxReconnectionAttempts;

    private SocketChannel socketChannel;

    private ProcessingRequests processingRequests;

    public TCPClient(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, ProcessingRequests processingRequests) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.processingRequests = processingRequests;
    }

    /**
     * Begins client operation.
     */
    public void run() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToServer();
                    processingStatus = processingRequests.processRequestToServer(this::reconnect);
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        console.printError("Exceeded the number of connection attempts!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        console.printError("Connection waiting time'" + reconnectionTimeout +
                                "' is beyond the limits of possible values!");
                        console.println("Reconnection will be performed immediately.");
                    } catch (Exception timeoutException) {
                        console.printError("An error occurred while trying to wait for connection!");
                        console.println("Reconnection will be performed immediately.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            console.println("The client's work has been successfully completed.");
        } catch (NotInDeclaredLimitsException exception) {
            console.printError("The client cannot be started!");
        } catch (IOException exception) {
            console.printError("An error occurred while trying to terminate the connection with the server!");
        }
    }

    private void reconnect(boolean isExit) {
        try {
            reconnectionAttempts++;
            connectToServer();
        } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
            if (isExit)
                console.println("The command will not be registered on the server.");
            else console.println("Try to repeat the command later.");
        }
    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1) console.println("Reconnecting to the server...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            console.println("The connection to the server has been successfully established.");
            console.println("Waiting for permission to exchange data...");
            processingRequests.setServerWriter(new ObjectOutputStream(socketChannel.socket().getOutputStream()));
            processingRequests.setServerReader(new ObjectInputStream(socketChannel.socket().getInputStream()));
            console.println("Permission to exchange data has been received.");
        } catch (IllegalArgumentException exception) {
            console.printError("The server address is entered incorrectly!");
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            console.printError("An error occurred while connecting to the server!");
            throw new ConnectionErrorException();
        }
    }
}
