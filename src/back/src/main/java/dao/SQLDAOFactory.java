package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDAOFactory extends DAOFactory {
    private static final String dbUsername = "cdp";
    private static final String dbPassword = "cdp";
    private static final String dbName = "cdp";
    private static final int dbPort = 3306;

    private static Connection connection;

    private static void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                    "jdbc:mysql://db:" + dbPort + "/" + dbName + "?user=" +
                    dbUsername + "&password=" + dbPassword
            );
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new Error(exception);
        }
    }

    public static Connection getConnection() {
        if (connection == null)
            openConnection();

        return connection;
    }

    @Override
    public ProjectDAO getProjectDAO() {
        return SQLProjectDAO.getInstance();
    }

    @Override
    public TestDAO getTestDAO() {
        return SQLTestDAO.getInstance();
    }
}
