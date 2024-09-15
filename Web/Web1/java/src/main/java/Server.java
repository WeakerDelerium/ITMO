import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger(Server.class.getName());

        logger.setUseParentHandlers(false);
        logger.addHandler(new FileHandler("serv.log", true));
        logger.setLevel(Level.ALL);

        var fcgiInterface = new FCGIInterface();

        logger.info("Server started");

        while (fcgiInterface.FCGIaccept() >= 0) {
            logger.info("Server get request");

            Properties requestParams = FCGIInterface.request.params;

            if (requestParams.getProperty("REQUEST_METHOD").equals("GET")) {
                try {
                    HashMap<String, Double> reqData = getRequestArgs(requestParams.getProperty("QUERY_STRING"));

                    double x = reqData.get("x");
                    double y = reqData.get("y");
                    double r = reqData.get("r");

                    boolean hit = (x <= 0 && y > 0) && (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)) ||
                            (x <= 0 && y <= 0) && (-x - r / 2 <= y) ||
                            (x > 0 && y <= 0) && (x <= r / 2 && y <= r);

                    sendResponse("HTTP/1.1 200 OK", "{\"hit\": \"%s\"}".formatted(hit ? 'Y' : 'N'));
                } catch (NumberFormatException e) {
                    logger.warning("Invalid json");
                }

                logger.info("Request has been processed");
            }
            else logger.warning("Request method <> GET --> skip");
        }
    }

    private static HashMap<String, Double> getRequestArgs(String queryParams) throws NumberFormatException {
        return Arrays.stream(queryParams.split("&"))
                .map(arg -> arg.split("="))
                .collect(HashMap::new, (map, kv) -> map.put(kv[0], Double.parseDouble(kv[1])), HashMap::putAll);
    }

    private static void sendResponse(String statusCode, String responseParams) {
        System.out.printf("%s\nContent-Type: application/json\nContent-Length: %d\n\n%s",
                statusCode, responseParams.getBytes(StandardCharsets.UTF_8).length, responseParams);
    }
}
