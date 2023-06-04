package server.network;

import common.interaction.Request;
import common.interaction.Response;
import server.App;
import server.handlers.RequestHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class RecievingManager {
    private final RequestHandler requestHandler;

    public RecievingManager(RequestHandler requestHandler){
        this.requestHandler = requestHandler;
    }

    public Response get(Socket socket){
        try{
            ObjectInputStream clientReader = new ObjectInputStream(socket.getInputStream());
            var userRequest = (Request) clientReader.readObject();
            var response =  requestHandler.handle(userRequest);
            App.logger.info("Request '" + userRequest.getCommandName() + "' has been successfully processed.");
            return response;
        } catch (IOException exception){
            App.logger.warn("Client is disconnected!");
        } catch (ClassNotFoundException e) {
            App.logger.warn("An error occurred while reading the received data!");
        }
        return null;
    }

}
