package server.network;





import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import server.App;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class ReceivingManager {

    private HashSet<SocketChannel> sessions = new HashSet<>();
    private final HashMap<SocketAddress, byte[]> receivedData;

    public ReceivingManager(){
        this.receivedData = new HashMap<>();
    }

    public void setSessions(HashSet<SocketChannel> sessions){
        this.sessions = sessions;
    }
    public Pair<byte[], SocketChannel> read(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int numRead = channel.read(byteBuffer);
            if (numRead == -1) {
                this.sessions.remove(channel);
                App.logger.info("client " + channel.socket().getRemoteSocketAddress() + " disconnected");
                channel.close();
                key.cancel();
                return null;
            }
            var clientSocket = channel.socket();
            var arr = receivedData.get(clientSocket.getRemoteSocketAddress());
            arr = arr == null ? new byte[0] : arr;
            arr = Bytes.concat(arr, Arrays.copyOf(byteBuffer.array(), byteBuffer.array().length - 1));
            // reached the end of the object being sent
            if(byteBuffer.array()[numRead-1] == 1){
                return ImmutablePair.of(arr, clientSocket.getChannel());
            }
        }
        catch (IOException e){
            App.logger.error(e.getMessage());
            // server has crashed, and we got isConnectionPending - true and isOpen - true as well
            // and so we close the connection, because this connection is already closed by the server and create another one
            if(Objects.equals(e.getMessage(), "Connection reset"))
            {
                try{
                    Thread.sleep(3000);
                    channel.close();
                }
                catch (Exception e1){
                }
            }
        }
        return null;
    }
}
