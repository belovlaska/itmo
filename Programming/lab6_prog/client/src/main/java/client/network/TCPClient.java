package client.network;

import client.App;
import com.google.common.primitives.Bytes;
import common.network.requests.Request;
import common.network.responses.Response;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class TCPClient {
    private final int PACKET_SIZE = 1024;
    private final int DATA_SIZE = PACKET_SIZE - 1;
    private final InetSocketAddress addr;
    private SocketChannel client;
    private final Logger logger = App.logger;

    public TCPClient(InetSocketAddress addr) throws IOException {
        this.addr = addr;
        this.client = SocketChannel.open();
        var port = 18018;
        var socketAddress = new InetSocketAddress("localhost", port);
        client.bind(socketAddress);
        client.configureBlocking(false);
        client.connect(addr);
        logger.info("SocketChannel connected with " + addr);
    }

    public Response sendAndReceiveCommand(Request request) throws IOException {
        var data = SerializationUtils.serialize(request);
        var responseBytes = sendAndReceiveData(data);

        Response response = SerializationUtils.deserialize(responseBytes);
        logger.info("Get response by server:  " + response);
        return response;
    }

    private void sendData(byte[] data) throws IOException {
        byte[][] ret = new byte[(int)Math.ceil(data.length / (double)DATA_SIZE)][DATA_SIZE];

        int start = 0;
        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }

        logger.info("Sending " + ret.length + " chunks...");

        for(int i = 0; i < ret.length; i++) {
            var chunk = ret[i];
            if (i == ret.length - 1) {
                var lastChunk = Bytes.concat(chunk, new byte[]{1});
                client.write(ByteBuffer.wrap(lastChunk));
                logger.info("Last chunk with " + lastChunk.length + " size sent to server.");
            } else {
                var answer = Bytes.concat(chunk, new byte[]{0});
                client.write(ByteBuffer.wrap(answer));
                logger.info("Chunk with " + answer.length + " size sent to server.");
            }
        }

        logger.info("Sending finished.");
    }

    private byte[] receiveData() throws IOException {
        var received = false;
        var result = new byte[0];

        while(!received) {
            var data = receiveData(PACKET_SIZE);
            logger.info("Received \"" + new String(data) + "\"");
            logger.info("Last byte: " + data[data.length - 1]);

            if (data[data.length - 1] == 1) {
                received = true;
                logger.info("Finished receiving data");
            }
            result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));
        }

        return result;
    }

    private byte[] receiveData(int bufferSize) throws IOException {
        var buffer = ByteBuffer.allocate(bufferSize);
        client.read(buffer);
        return buffer.array();
    }

    private byte[] sendAndReceiveData(byte[] data) throws IOException {
        sendData(data);
        return receiveData();
    }
}
