package server.managers;

import common.transfer.Response;
import common.transfer.UserInfo;
import common.util.RandomSequenceGenerator;
import server.DB.DBExecutor;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class AuthorizationManager {
    private AuthorizationManager() {}

    public static Response checkUserExists(UserInfo userInfo) throws SQLException, NoSuchAlgorithmException {
        return new Response(DBExecutor.checkUserExists(userInfo.username(), userInfo.passwd()), null);
    }

    public static Response createUser(UserInfo userInfo) throws SQLException, NoSuchAlgorithmException {
        return new Response(DBExecutor.createUser(userInfo.username(), userInfo.passwd(), generateSalt()), null);
    }

    public static Response updateLastSession(UserInfo userInfo) throws SQLException {
        return new Response(DBExecutor.updateLastSession(userInfo.username()), null);
    }

    private static String generateSalt() {
        return RandomSequenceGenerator.generate();
    }
}
