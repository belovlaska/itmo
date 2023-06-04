package server.network;

import common.interaction.Response;
import server.App;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendingManager {
    public void send(Socket socket, Response response){
        try {
            ObjectOutputStream clientWriter = new ObjectOutputStream(socket.getOutputStream());
            clientWriter.writeObject(response);
            clientWriter.flush();
        } catch (IOException exception) {
            App.logger.warn("Client is disconnected!");
        }
    }
}
