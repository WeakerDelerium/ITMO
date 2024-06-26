package server.managers;

import java.util.logging.Logger;
import server.Main;

public class ServerLogger {
    private static Logger LOGGER = null;

    public static synchronized Logger getInstance(){
        if(LOGGER == null) LOGGER = Logger.getLogger(Main.class.getName());
        return LOGGER;
    }
}
