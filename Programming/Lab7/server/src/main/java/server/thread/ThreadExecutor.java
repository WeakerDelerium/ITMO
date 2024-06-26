package server.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadExecutor {
    private static Executor executor = null;

    public static Executor getInstance() {
        if (executor == null) executor = Executors.newCachedThreadPool();
        return executor;
    }
}
