package dao.sql;

import java.sql.*;
import java.util.List;

public class SQLDatabase {
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

    public static PreparedStatement prepare(String statement, List<Object> items) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

        for(int i = 1; items != null && i <= items.size(); i++) {
            preparedStatement.setObject(i, items.get(i-1));
        }

        return preparedStatement;
    }

    public static PreparedStatement prepare(String statement) throws SQLException {
        return prepare(statement, null);
    }

    public static void exec(String statement, List<Object> items) throws SQLException {
        prepare(statement, items).execute();
    }

    public static void exec(String statement) throws SQLException {
        exec(statement, null);
    }
}
