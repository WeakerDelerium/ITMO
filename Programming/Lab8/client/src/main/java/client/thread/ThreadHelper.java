package client.thread;

public class ThreadHelper {
    public static Thread toDaemonThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    }
}
