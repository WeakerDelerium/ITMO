package client.managers;

import common.parsers.Serializer;
import common.transfer.UserInfo;
import common.util.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class User {
    private static User user = null;

    private String address;
    private Integer port;
    private SocketChannel channel;

    private UserInfo userInfo;

    private User() {}

    private Long readChunkNum() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Constants.LONG_CAPACITY);

        this.channel.read(buffer);

        return buffer.flip().getLong();
    }

    private void writeChunkNum(byte[] serializedObject) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Constants.LONG_CAPACITY);

        buffer.putLong((long) (serializedObject.length + Constants.BUFFER_LENGTH - 1) / Constants.BUFFER_LENGTH);
        buffer.flip();

        this.channel.write(buffer);
    }

    public <T extends Serializable> T readObject() throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFFER_LENGTH);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        long chunkNum = readChunkNum();

        for (long i = 0; i < chunkNum; i++) {
            this.channel.read(buffer);
            buffer.flip();
            byteOut.write(buffer.array(), buffer.position(), buffer.limit());
            buffer.clear();
        }

        return Serializer.deserialize(byteOut.toByteArray());
    }

    public <T extends Serializable> void writeObject(T obj) throws IOException {
        byte[] serialisedObject = Serializer.serialize(obj);

        writeChunkNum(serialisedObject);
        this.channel.write(ByteBuffer.wrap(serialisedObject));
    }

    public void connect(String address, Integer port) throws IOException {
        this.address = address;
        this.port = port;
        this.channel = SocketChannel.open();
        this.channel.connect(new InetSocketAddress(address, port));
    }

    public Integer getPort() {
        return this.port;
    }

    public String getAddress() {
        return this.address;
    }

    public SocketChannel getChannel() {
        return this.channel;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public static User getInstance() {
        if (user == null) user = new User();
        return user;
    }
}
