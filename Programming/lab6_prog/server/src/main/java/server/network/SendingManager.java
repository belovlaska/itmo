package server.network;

import com.google.common.primitives.Bytes;
import server.App;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class SendingManager {

    private final int PACKET_SIZE = 1024;
    private final int DATA_SIZE = PACKET_SIZE - 1;

    public void send(byte[] data, DataOutputStream out) {
        var logger = App.logger;

        try {
            byte[][] ret = new byte[(int) Math.ceil(data.length / (double) DATA_SIZE)][DATA_SIZE];

            int start = 0;
            for (int i = 0; i < ret.length; i++) {
                ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
                start += DATA_SIZE;
            }

            logger.info("Sending " + ret.length + " chunks...");

            for (int i = 0; i < ret.length; i++) {
                var chunk = ret[i];
                if (i == ret.length - 1) {
                    var lastChunk = Bytes.concat(chunk, new byte[]{1});
                    out.write(lastChunk);
                    logger.info("Last chunk with size " + chunk.length + " sent to server.");
                } else {
                    out.write(Bytes.concat(chunk, new byte[]{0}));
                    logger.info("Chunk with size " + chunk.length + " sent to server.");
                }
            }
        }
        catch (IOException e){
            logger.error(e.getMessage());
            // server has crashed, and we got isConnectionPending - true and isOpen - true as well
            // and so we close the connection, because this connection is already closed by the server and create another one
            if(Objects.equals(e.getMessage(), "Connection reset")) {
                try {
                    Thread.sleep(3000);
                    out.close();
                }
                catch (Exception ignored){
                }
            }
        }
    }
}
