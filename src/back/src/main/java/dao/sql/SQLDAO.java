package dao.sql;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public abstract class SQLDAO<T> {

    protected abstract T createObjectFromResult(ResultSet resultSet) throws SQLException;

    protected T queryFirstObject(String statement, List<Object> opt) throws SQLException {

        PreparedStatement preparedStatement = SQLDatabase.prepare(statement,opt);

        String sql = preparedStatement.toString();

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            T item = createObjectFromResult(resultSet);

            resultSet.close();
            preparedStatement.close();

            return item;
        }

        throw new SQLException("Could not query : "+sql);
    }

    protected List<T> queryAllObjects(String statement, List<Object> opt) throws SQLException {

        PreparedStatement preparedStatement = SQLDatabase.prepare(statement,opt);

        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<T> items = new ArrayList<>();

        while (resultSet.next()) {
            items.add(createObjectFromResult(resultSet));
        }

        resultSet.close();
        preparedStatement.close();

        return items;
    }

    protected T queryFirstObject(String statement) throws SQLException {
        return queryFirstObject(statement, null);
    }

    protected List<T> queryAllObjects(String statement) throws SQLException {
        return queryAllObjects(statement, null);
    }

    protected int doInsert(String statement, List<Object> opt) throws SQLException {

        PreparedStatement preparedStatement = SQLDatabase.prepare(statement,opt);

        String sql = preparedStatement.toString();

        preparedStatement.execute();

        ResultSet generatedKey = preparedStatement.getGeneratedKeys();

        if (generatedKey.next()) {
            int id = generatedKey.getInt(1);

            generatedKey.close();
            preparedStatement.close();

            return id;
        }

        throw new SQLException("Could not insert : "+sql);
    }
}