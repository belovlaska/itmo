package server.network;



import common.network.requests.Request;
import common.network.responses.NoSuchCommandResponse;
import common.network.responses.Response;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import server.App;
import server.handlers.CommandHandler;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import static server.App.logger;


public class TCPServer {
    private final int port;
    private HashSet<SocketChannel> sessions;
    private ReceivingManager receivingManager;
    private SendingManager sendingManager;
    private CommandHandler commandHandler;
    private Runnable afterHook;

    private Selector selector;

    public TCPServer(int port, ReceivingManager receivingManager) {
        this.port = port;
        this.receivingManager = receivingManager;
        this.sendingManager = new SendingManager();
        this.sessions = new HashSet<>();
    }

    public HashSet<SocketChannel> getSessions() {
        return sessions;
    }

    public void stop() throws IOException {
        for (var se: sessions) {
            se.close();
        }
    }
    public void start() {
        try {
            this.selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            var socketAddress = new InetSocketAddress("localhost", port);
            serverSocketChannel.bind(socketAddress, port);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server started...");
            while (true) {
                // blocking, wait for events
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (!key.isValid()) continue;
                    if (key.isAcceptable()) accept(key);
                    else if (key.isReadable()) {
                        var result = receivingManager.read(key);
                        if(result == null)
                            continue;
                        var res = result.getLeft();
                        int p = 1023;
                        if (res == null)
                            continue;
                        for (int i = res.length - 1; i > -1; i--) {
                            if (res[i] != 0) {
                                p = i;
                                break;
                            }
                        }
                        var cutres = Arrays.copyOfRange(res, 0, p+1);
                        Request request;

                        SocketChannel channel = (SocketChannel) key.channel();
                        try {
                            request = SerializationUtils.deserialize(cutres);
                            logger.info("Processing " + request + " from " + channel.socket().getRemoteSocketAddress());
                        } catch (SerializationException e) {
                            logger.error("Сan't deserialize request object.", e);
                            continue;
                        }


                        Response response = null;
                        try {
                            response = commandHandler.handle(request);
                            if (afterHook != null) afterHook.run();
                        } catch (Exception e) {
                            logger.error("Executing command exception : " + e.toString(), e);
                        }
                        if (response == null) response = new NoSuchCommandResponse(request.getName());

                        var data = SerializationUtils.serialize(response);
                        sendingManager.send(data, new DataOutputStream(channel.socket().getOutputStream()));
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            channel.register(this.selector, SelectionKey.OP_READ);
            this.sessions.add(channel);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setAfterHook(Runnable afterHook) {
        this.afterHook = afterHook;
    }

}