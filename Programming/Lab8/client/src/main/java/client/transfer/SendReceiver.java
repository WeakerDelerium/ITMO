package client.transfer;

import client.managers.User;
import common.transfer.Request;
import common.transfer.Response;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class SendReceiver {
    private static SendReceiver sendReceiver = null;

    private final ReentrantLock lock;

    private SendReceiver() {
        lock = new ReentrantLock();
    }

    public Response sendAndReceive(Request request) throws IOException, ClassNotFoundException {
        lock.lock();
        try {
            User user = User.getInstance();

            user.writeObject(request);

            return user.readObject();
        } finally {
            lock.unlock();
        }
    }

    public static SendReceiver getInstance() {
        if (sendReceiver == null) sendReceiver = new SendReceiver();
        return sendReceiver;
    }
}
