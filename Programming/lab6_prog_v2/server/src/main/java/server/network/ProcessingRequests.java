package server.network;

import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseResult;
import server.App;
import server.handlers.RequestHandler;

import java.io.*;
import java.net.Socket;

public class ProcessingRequests {
    private final RequestHandler requestHandler;

    public ProcessingRequests(RequestHandler requestHandler){
        this.requestHandler = requestHandler;
    }


    public boolean processClientRequest(Socket clientSocket) {
        Request userRequest = null;
        Response responseToUser;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = requestHandler.handle(userRequest);
                App.logger.info("Request '" + userRequest.getCommandName() + "' has been successfully processed.");
                clientWriter.writeObject(responseToUser);
                clientWriter.flush();
            } while(responseToUser.getResponseResult() != ResponseResult.SERVER_EXIT);
            return false;
        } catch (ClassNotFoundException exception){
            App.logger.warn("An error occurred while reading the received data!");
        }catch (InvalidClassException | NotSerializableException exception) {
            App.logger.warn("An error occurred when sending data to the client!");
        } catch (IOException exception) {
            if (userRequest == null) {
                App.logger.warn("Unexpected disconnection from the client!");
            } else {
                App.logger.info("The client has been successfully disconnected from the server!");
            }
        }
        return true;
    }
}
