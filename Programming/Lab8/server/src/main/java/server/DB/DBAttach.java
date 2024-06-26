package server.DB;

import common.util.PropertiesReader;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBAttach {
    private static DBAttach dbAttach = null;

    private Connection connection;

    private DBAttach() {}

    public void connect() throws ClassNotFoundException, SQLException, ConnectException {
        Properties properties = getProperties();

        if (properties == null) throw new ConnectException("config file wasn't found");

        String jdbcURL = properties.getProperty("jdbcURL");
        String username = properties.getProperty("username");
        String passwd = properties.getProperty("passwd");

        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(jdbcURL, username, passwd);
    }

    public void disconnect() throws SQLException {
        this.connection.close();
    }

    private Properties getProperties() {
        try {
            return PropertiesReader.getProperties("server_config.properties");
        } catch (IOException fileNotExist) {
            return null;
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public static synchronized DBAttach getInstance() {
        if (dbAttach == null) dbAttach = new DBAttach();
        return dbAttach;
    }
}
