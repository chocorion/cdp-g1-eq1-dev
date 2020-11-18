package dao;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public static ResultSet query(String statement, List<Object> items) throws SQLException {
        Connection conn = SQLDAOFactory.getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement(statement);

        for(int i = 1; items != null && i < items.size(); i++) {
            preparedStatement.setObject(i, items.get(i));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();

        return resultSet;
    }

    public static ResultSet query(String statement) throws SQLException {
        return query(statement, null);
    }

    public static void exec(String statement, List<Object> items) throws SQLException {
        query(statement, items);
    }

    public static void exec(String statement) throws SQLException {
        exec(statement, null);
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
