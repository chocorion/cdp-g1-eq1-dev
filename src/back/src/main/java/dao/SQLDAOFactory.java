package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDAOFactory implements DAOFactory {
    private static final String dbUsername = "user";
    private static final String dbPassword = "changeMeLater";
    private static final String dbName = "cdp";
    private static final int dbPort = 3307;

    private static Connection connection;

    private static void openConnection() {

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:" + dbPort + "/" + dbName,
                    dbUsername,
                    dbPassword
            );
        } catch (SQLException exception) {
            throw new Error(exception);
        }
    }

    public static Connection getConnection() {
        if (connection == null)
            openConnection();

        return connection;
    }
}
