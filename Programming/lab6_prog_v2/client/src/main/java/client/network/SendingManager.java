package client.network;

import client.utils.console.Console;
import common.interaction.Request;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;

public class SendingManager {
    private ObjectOutputStream outputStream;

    public void send(Console console, Request request, SocketChannel socketChannel){
        try {
            outputStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            outputStream.writeObject(request);
        }catch (IOException exception){
            console.printError("The connection to the server is broken!");
        }
    }

    public void stopOutput() throws IOException{
        outputStream.close();
    }
}
