package client.managers;

import client.transfer.SendReceiver;
import common.transfer.Request;
import common.transfer.RequestTask;
import common.transfer.Response;
import common.transfer.UserInfo;
import common.util.PropertiesReader;
import common.util.RandomSequenceGenerator;
import common.util.SHA224Hashing;

import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class AuthorizationManager {
    private static final User user = User.getInstance();

    private AuthorizationManager() {}

    public enum State {
        USER_NOT_FOUND,
        ALREADY_REGISTER,
        WRONG_PASSWD,
        WRONG_USERNAME_FORMAT,
        WRONG_PASSWD_FORMAT,
        NOT_AVAILABLE,
        OK
    }

    public static State authorize(ScreenCastHelper.Display display, String username, String passwd)
            throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        return switch (display) {
            case LOG_IN -> logIn(username, passwd);
            case SIGN_UP -> signUp(username, passwd);
            default -> State.NOT_AVAILABLE;
        };
    }

    private static State logIn(String username, String passwd)
            throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        if (!checkUserExists(username, null)) return State.USER_NOT_FOUND;

        passwd = SHA224Hashing.hashOf(passwd + generatePepper());

        if (!checkUserExists(username, passwd)) return State.WRONG_PASSWD;

        return servAccept(username, passwd, RequestTask.LOG_IN);
    }

    private static State signUp(String username, String passwd)
            throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        if (checkUserExists(username, null)) return State.ALREADY_REGISTER;
        if (username.isEmpty()) return State.WRONG_USERNAME_FORMAT;
        if (passwd.length() < 8) return State.WRONG_PASSWD_FORMAT;

        passwd = SHA224Hashing.hashOf(passwd + generatePepper());

        return servAccept(username, passwd, RequestTask.SIGN_UP);
    }

    private static State servAccept(String username, String passwd, RequestTask task)
            throws IOException, ClassNotFoundException {
        UserInfo userInfo = new UserInfo(username, passwd);

        Request request = new Request(task, null, userInfo);
        Response response = SendReceiver.getInstance().sendAndReceive(request);

        if (!response.ok()) return State.NOT_AVAILABLE;
        user.setUserInfo(userInfo);

        return State.OK;
    }

    private static Boolean checkUserExists(String username, String passwd)
            throws IOException, ClassNotFoundException {
        Request request = new Request(RequestTask.CHECK_USER, null, new UserInfo(username, passwd));
        Response response = SendReceiver.getInstance().sendAndReceive(request);

        return response.ok();
    }

    private static String generatePepper() {
        try {
            return PropertiesReader.getProperties("user_config.properties").getProperty("pepper");
        } catch (IOException fileNotExist) {
            String pepper = RandomSequenceGenerator.generate();

            Properties properties = new Properties();

            properties.setProperty("pepper", pepper);

            try {
                properties.store(new FileWriter("user_config.properties"), "User hashing data");
            } catch (IOException e) {
                return null;
            }

            return pepper;
        }
    }
}
