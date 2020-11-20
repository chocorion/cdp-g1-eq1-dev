package dao.sql;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public abstract class SQLDAO<T> {

    protected abstract T createObjectFromResult(ResultSet resultSet) throws SQLException;

    protected T queryFirstObject(String statement, List<Object> opt) throws SQLException {

        PreparedStatement preparedStatement = SQLDatabase.prepare(statement,opt);

        System.out.println(preparedStatement);
        
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            T item = createObjectFromResult(resultSet);

            resultSet.close();
            preparedStatement.close();

            return item;
        }

        throw new SQLException("Can't get $$Lambda$");
    }

    protected List<T> queryAllObjects(String statement, List<Object> opt) throws SQLException {

        PreparedStatement preparedStatement = SQLDatabase.prepare(statement,opt);

        System.out.println(preparedStatement);

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

    protected T doInsert(String statement, List<Object> opt) throws SQLException {

        PreparedStatement preparedStatement = SQLDatabase.prepare(statement,opt);

        System.out.println(preparedStatement);

        ResultSet generatedKey = preparedStatement.getGeneratedKeys();

        if (generatedKey.next()) {
            T item = createObjectFromResult(generatedKey);

            preparedStatement.close();

            return item;
        }

        throw new SQLException("Can't add this $$Lambda$");
    }
}