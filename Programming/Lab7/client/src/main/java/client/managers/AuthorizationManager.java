package client.managers;

import common.exception.MatchingUsernamesException;
import common.exception.UserAuthorizationException;
import common.exception.UserNotFoundException;
import common.exception.WrongPasswordException;
import common.transfer.Request;
import common.transfer.RequestTask;
import common.transfer.Response;
import common.transfer.UserInfo;
import common.ui.Console;
import common.ui.UserInput;
import common.util.PropertiesReader;
import common.util.RandomSequenceGenerator;
import common.util.SHA224Hashing;

import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class AuthorizationManager {
    private static final Console console = Console.getInstance();

    private AuthorizationManager() {}

    public static UserInfo authorize(User user, UserInput userInput)
            throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        if (userInput.readAccountExistenceSelect().equalsIgnoreCase("Y")) return logIn(user, userInput);
        else return signUp(user, userInput);
    }

    private static UserInfo readSignUpData(User user, UserInput userInput)
            throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        String username = userInput.readUsername();

        try {
            if (checkUserExists(user, username, null)) throw new MatchingUsernamesException(username);
        } catch (UserAuthorizationException e) {
            console.printError(e.getMessage());
            return readSignUpData(user, userInput);
        }

        String passwd = SHA224Hashing.hashOf(userInput.readPasswd() + generatePepper());

        console.println();
        return new UserInfo(username, passwd);
    }

    private static UserInfo readLogInData(User user, UserInput userInput)
            throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        String username = userInput.readUsername();
        String passwd = SHA224Hashing.hashOf(userInput.readPasswd() + generatePepper());

        try {
            if (!checkUserExists(user, username, null)) throw new UserNotFoundException(username);
            if (!checkUserExists(user, username, passwd)) throw new WrongPasswordException();
        } catch (UserAuthorizationException e) {
            console.printError(e.getMessage() + "\n");
            return readLogInData(user, userInput);
        }

        console.println();
        return new UserInfo(username, passwd);
    }

    private static UserInfo signUp(User user, UserInput userInput)
            throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        UserInfo userInfo = readSignUpData(user, userInput);

        user.writeObject(new Request(RequestTask.SIGN_UP, null, userInfo));

        Response response = user.readObject();
        if (!response.ok()) throw (RuntimeException) response.data();

        return userInfo;
    }

    private static UserInfo logIn(User user, UserInput userInput)
            throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        UserInfo userInfo = readLogInData(user, userInput);

        user.writeObject(new Request(RequestTask.LOG_IN, null, userInfo));

        Response response = user.readObject();
        if (!response.ok()) throw (RuntimeException) response.data();

        return userInfo;
    }

    private static Boolean checkUserExists(User user, String username, String passwd)
            throws IOException, ClassNotFoundException {
        user.writeObject(new Request(RequestTask.CHECK_USER, null, new UserInfo(username, passwd)));

        Response response = user.readObject();

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
