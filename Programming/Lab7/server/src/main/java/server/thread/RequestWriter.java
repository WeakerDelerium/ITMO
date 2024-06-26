package server.thread;

import common.parsers.Serializer;
import common.transfer.Response;
import common.util.Constants;
import server.managers.ServerLogger;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class RequestWriter implements Runnable {
    private final SocketChannel client;
    private final SocketAddress clientAddress;
    private final Response response;

    public RequestWriter(SocketChannel client, SocketAddress clientAddress, Response response) {
        this.client = client;
        this.clientAddress = clientAddress;
        this.response = response;
    }

    @Override
    public void run() {
        Logger LOGGER = ServerLogger.getInstance();

        try {
            byte[] serializedResponse = Serializer.serialize(response);

            writeChunkNum(client, serializedResponse);
            client.write(ByteBuffer.wrap(serializedResponse));
        } catch (SocketException e) {
            LOGGER.severe("User (" + this.clientAddress + ") disconnected");
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            LOGGER.severe("Error while casting serialized object");
            Thread.currentThread().interrupt();
        }
    }

    private static void writeChunkNum(SocketChannel client, byte[] serializedObject) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Constants.LONG_CAPACITY);

        buffer.putLong((long) (serializedObject.length + Constants.BUFFER_LENGTH - 1) / Constants.BUFFER_LENGTH);
        buffer.flip();

        client.write(buffer);
    }
}
