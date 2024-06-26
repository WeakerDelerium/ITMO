package client.thread;

import client.transfer.SendReceiver;
import client.managers.User;

import common.transfer.Request;
import common.transfer.RequestTask;
import common.transfer.Response;

import java.util.LinkedList;
import java.util.concurrent.Callable;

public class CollectionUpdater implements Callable<LinkedList<Object>> {
    @Override
    public LinkedList<Object> call() throws Exception {
        User user = User.getInstance();

        Request request = new Request(RequestTask.UPDATE_COLLECTION, null, user.getUserInfo());
        Response response = SendReceiver.getInstance().sendAndReceive(request);

        if (!response.ok()) throw (RuntimeException) response.data();

        return (LinkedList<Object>) response.data();
    }
}
