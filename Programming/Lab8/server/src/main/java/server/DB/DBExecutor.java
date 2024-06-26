package server.DB;

import common.util.SHA224Hashing;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DBExecutor {
    public static ResultSet getCollection() throws SQLException {
        PreparedStatement statement = DBQueries.GET_COLLECTION();

        return statement.executeQuery();
    }

    public static Boolean checkUserExists(String username, String passwd) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement statement = (passwd != null) ? DBQueries.CHECK_USER_RECORD_EXIST() : DBQueries.CHECK_USER_EXISTS();

        statement.setString(1, username);
        if (passwd != null) statement.setString(2, SHA224Hashing.hashOf(passwd + getSalt(username)));

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getBoolean("exists");
    }

    public static Boolean createUser(String username, String passwd, String salt) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement statement = DBQueries.CREATE_USER();

        statement.setString(1, username);
        statement.setString(2, SHA224Hashing.hashOf(passwd + salt));
        statement.setString(3, salt);

        statement.executeUpdate();

        return true;
    }

    public static Boolean updateLastSession(String username) throws SQLException {
        PreparedStatement statement = DBQueries.UPDATE_LAST_SESSION();

        statement.setString(1, username);

        statement.executeUpdate();

        return true;
    }

    private static String getSalt(String username) throws SQLException {
        PreparedStatement statement = DBQueries.GET_USER_SALT();

        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getString("salt");
    }

    public static int addRoute(String name, long coordinates_x, double coordinates_y,
                                  int from_x, int from_y, long from_z, String from_name,
                                  double to_x, int to_y, String to_name, long distance, String username) throws SQLException {
        PreparedStatement statement = DBQueries.ADD_ROUTE();

        statement.setString(1, name);
        statement.setLong(2, coordinates_x);
        statement.setDouble(3, coordinates_y);
        statement.setInt(4, from_x);
        statement.setInt(5, from_y);
        statement.setLong(6, from_z);
        statement.setString(7, from_name);
        statement.setDouble(8, to_x);
        statement.setInt(9, to_y);
        statement.setString(10, to_name);
        statement.setLong(11, distance);
        statement.setString(12, username);

        statement.executeQuery();

        ResultSet resultSet = statement.getResultSet();
        resultSet.next();

        return resultSet.getInt("add_route");
    }

    public static void updateRoute(int id, String name, long coordinates_x, double coordinates_y,
                                   int from_x, int from_y, long from_z, String from_name,
                                   double to_x, int to_y, String to_name, long distance, String username)
            throws SQLException {
        CallableStatement statement = DBQueries.UPDATE_ROUTE();

        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setLong(3, coordinates_x);
        statement.setDouble(4, coordinates_y);
        statement.setInt(5, from_x);
        statement.setInt(6, from_y);
        statement.setLong(7, from_z);
        statement.setString(8, from_name);
        statement.setDouble(9, to_x);
        statement.setInt(10, to_y);
        statement.setString(11, to_name);
        statement.setLong(12, distance);
        statement.setString(13, username);

        statement.execute();
    }

    public static ResultSet getRouteById(int routeId) throws SQLException {
        PreparedStatement statement = DBQueries.GET_ROUTE_ARGS_BY_ID();

        statement.setInt(1, routeId);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet;
    }

    public static ResultSet getUserRoutesIDs(String username) throws SQLException {
        PreparedStatement statement = DBQueries.GET_USER_ROUTES_IDs();

        statement.setString(1, username);

        return statement.executeQuery();
    }

    public static void removeByRouteId(Integer routeId) throws SQLException {
        PreparedStatement statement = DBQueries.REMOVE_BY_ROUTE_ID();

        statement.setInt(1, routeId);

        statement.executeUpdate();
    }

    public static void clearCollection() throws SQLException {
        PreparedStatement statement = DBQueries.CLEAR_COLLECTION();

        statement.executeUpdate();
    }

    public static void clearUserCollection(String username) throws SQLException {
        PreparedStatement statement = DBQueries.CLEAR_USER_COLLECTION();

        statement.setString(1, username);

        statement.executeUpdate();
    }

    public static Boolean checkUserAccess(String username, Integer routeId) throws SQLException {
        PreparedStatement statement = DBQueries.CHECK_USER_ACCESS();

        statement.setString(1, username);
        statement.setInt(2, routeId);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getBoolean("exists");
    }
}
