package server.network;

import common.exceptions.ClosingSocketException;
import common.exceptions.ConnectionErrorException;
import common.exceptions.OpeningServerSocketException;
import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseResult;
import server.App;
import server.handlers.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Runs the server.
 */
public class TCPServer {
    private final int port;
    private final int soTimeout;
    private ServerSocket serverSocket;

    private ProcessingRequests processingRequests;


    public TCPServer(int port, int soTimeout, ProcessingRequests processingRequests) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.processingRequests = processingRequests;
    }

    /**
     * Begins server operation.
     */
    public void run() {
        try{
            openServerSocket();
            boolean processingStatus = true;
            while (processingStatus){
                try (Socket clientSocket = connectToClient()){
                    processingStatus = processingRequests.processClientRequest(clientSocket);
                } catch (ConnectionErrorException | SocketTimeoutException exception){
                    break;
                } catch (IOException exception){
                    App.logger.warn("An error occurred while trying to terminate the connection with the client!");
                }
            }
            stop();
        } catch (OpeningServerSocketException exception){
            App.logger.warn("The server cannot be started!");
        }
    }

    /**
     * Finishes server operation.
     */
    private void stop() {
        try{
            App.logger.info("Shutting down the server...");
            if(serverSocket == null) throw new ClosingSocketException();
            serverSocket.close();
            App.logger.info("The server operation has been successfully completed.");
        } catch (ClosingSocketException exception) {
            App.logger.warn("It is impossible to shut down a server that has not yet started!");
        } catch (IOException exception) {
            App.logger.warn("An error occurred when shutting down the server!");
        }
    }

    /**
     * Open server socket.
     */
    private void openServerSocket() throws OpeningServerSocketException {
        try{
            App.logger.info("Starting the server...");
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(soTimeout);
            App.logger.info("The server has been successfully started.");
        } catch (IllegalArgumentException exception) {
            App.logger.warn("Port '" + port + "' is beyond the limits of possible values!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            App.logger.warn("An error occurred while trying to use the port '" + port + "'!");
            throw new OpeningServerSocketException();
        }
    }

    /**
     * Connecting to client.
     */
    private Socket connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try{
            App.logger.info("Listening port '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            App.logger.info("The connection with the client has been successfully established.");
            return clientSocket;
        } catch (SocketTimeoutException exception) {
            App.logger.warn("Connection timeout exceeded!");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            App.logger.warn("An error occurred while connecting to the client!");
            throw new ConnectionErrorException();
        }
    }



}