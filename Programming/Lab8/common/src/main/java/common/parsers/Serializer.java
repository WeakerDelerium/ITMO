package common.parsers;

import java.io.*;
import java.nio.ByteBuffer;

public class Serializer {
    public static <T extends Serializable> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);
        out.flush();
        return byteOut.toByteArray();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialize(byte[] serializedObject)
            throws IOException, ClassNotFoundException, ClassCastException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(serializedObject));
        return (T) in.readObject();
    }
}
