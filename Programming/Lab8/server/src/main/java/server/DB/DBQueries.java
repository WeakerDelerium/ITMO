package server.DB;

import java.sql.*;

public class DBQueries {
    private static final Connection connect = DBAttach.getInstance().getConnection();

    public static PreparedStatement GET_COLLECTION() throws SQLException {
        return connect.prepareStatement(
                "SELECT users.name as username, route.id, route.name, crd.x AS crd_x, crd.y AS crd_y, route.creation_date, f.x AS from_x, f.y AS from_y, f.z AS from_z, f.name AS from_name, t.x AS to_x, t.y AS to_y, t.name AS to_name, route.distance FROM route " +
                        "JOIN coordinates AS crd ON route.coordinates_id = crd.id " +
                        "JOIN location_from AS f ON route.location_from_id = f.id " +
                        "JOIN location_to AS t ON route.location_to_id = t.id " +
                        "JOIN collection ON collection.route_id = route.id " +
                        "JOIN users ON collection.user_id = users.id");
    }

    public static PreparedStatement ADD_ROUTE() throws SQLException {
        return connect.prepareCall("SELECT add_route(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public static CallableStatement UPDATE_ROUTE() throws SQLException {
        return connect.prepareCall("CALL update_route(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public static PreparedStatement CHECK_USER_EXISTS() throws SQLException {
        return connect.prepareStatement("SELECT EXISTS (SELECT 1 FROM users WHERE name = ?)");
    }

    public static PreparedStatement CHECK_USER_RECORD_EXIST() throws SQLException {
        return connect.prepareStatement("SELECT EXISTS (SELECT 1 FROM users WHERE name = ? AND passwd = ?)");
    }

    public static PreparedStatement CREATE_USER() throws SQLException {
        return connect.prepareStatement("INSERT INTO users(name, passwd, salt) VALUES (?, ?, ?)");
    }

    public static PreparedStatement UPDATE_LAST_SESSION() throws SQLException {
        return connect.prepareStatement("UPDATE users SET last_session = CURRENT_TIMESTAMP WHERE name = ?");
    }

    public static PreparedStatement GET_USER_SALT() throws SQLException {
        return connect.prepareStatement("SELECT salt FROM users WHERE name = ?");
    }

    public static PreparedStatement GET_ROUTE_ARGS_BY_ID() throws SQLException {
        return connect.prepareStatement(
                "SELECT route.id, route.name, crd.x AS crd_x, crd.y AS crd_y, route.creation_date, f.x AS from_x, f.y AS from_y, f.z AS from_z, f.name AS from_name, t.x AS to_x, t.y AS to_y, t.name AS to_name, route.distance FROM route " +
                        "JOIN coordinates AS crd ON route.coordinates_id = crd.id " +
                        "JOIN location_from AS f ON route.location_from_id = f.id " +
                        "JOIN location_to AS t ON route.location_to_id = t.id " +
                        "WHERE route.id = ?");
    }

    public static PreparedStatement GET_USER_ROUTES_IDs() throws SQLException {
        return connect.prepareStatement(
                "SELECT route_id FROM collection " +
                        "JOIN users ON users.id = user_id " +
                        "WHERE users.name = ?");
    }

    public static PreparedStatement REMOVE_BY_ROUTE_ID() throws SQLException {
        return connect.prepareStatement("DELETE FROM route WHERE id = ?");
    }

    public static PreparedStatement CLEAR_COLLECTION() throws SQLException {
        return connect.prepareStatement("DELETE FROM route CASCADE");
    }

    public static PreparedStatement CLEAR_USER_COLLECTION() throws SQLException {
        return connect.prepareStatement(
                "DELETE FROM route CASCADE WHERE id IN " +
                        "(SELECT route_id FROM collection " +
                        "JOIN users ON users.id = user_id " +
                        "WHERE users.name = ?)");
    }

    public static PreparedStatement CHECK_USER_ACCESS() throws SQLException {
        return connect.prepareStatement(
                "SELECT EXISTS " +
                        "(SELECT 1 FROM collection " +
                        "JOIN users ON users.id = user_id " +
                        "WHERE users.name = ? AND route_id = ?)");
    }
}
