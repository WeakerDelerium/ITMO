package server.thread;

import common.managers.CommandManager;
import common.parsers.Serializer;
import common.util.Constants;
import server.managers.ServerLogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class RequestReader implements Runnable {
    private final SocketChannel client;
    private final SocketAddress clientAddress;
    private final CommandManager commandManager;

    public RequestReader(SocketChannel client, SocketAddress clientAddress, CommandManager commandManager) {
        this.client = client;
        this.clientAddress = clientAddress;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        Logger LOGGER = ServerLogger.getInstance();

        while (!Thread.interrupted()) {
            try {
                long chunkNum = readChunkNum(this.client);

                if (chunkNum == 0) continue;

                ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFFER_LENGTH);

                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                for (long i = 0; i < chunkNum; i++) {
                    this.client.read(buffer);
                    buffer.flip();
                    if (buffer.limit() - buffer.position() == 0) i--;
                    byteOut.write(buffer.array(), buffer.position(), buffer.limit());
                    buffer.clear();
                }

                Thread requestHandlerThread = new Thread(new RequestHandler(this.client, this.clientAddress, Serializer.deserialize(byteOut.toByteArray()), this.commandManager));
                requestHandlerThread.start();
            } catch (IOException e) {
                LOGGER.info("User (" + this.clientAddress + ") disconnected");
                Thread.currentThread().interrupt();
            } catch (ClassNotFoundException e) {
                LOGGER.severe("Error while casting serialized object");
                Thread.currentThread().interrupt();
            }
        }
    }

    private static Long readChunkNum(SocketChannel client) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Constants.LONG_CAPACITY);

        if (client.read(buffer) == 0) return 0L;

        return buffer.flip().getLong();
    }
}
