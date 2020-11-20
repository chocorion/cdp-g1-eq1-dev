package dao.sql;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public abstract class SQLDAO<T> {

    protected abstract T createObjectFromResult(ResultSet resultSet) throws SQLException;

    protected T queryFirstObject(String statement, List<Object> opt) throws SQLException {

        ResultSet resultSet = SQLDatabase.query(statement, opt);

        if (resultSet.next()) {
            T item = createObjectFromResult(resultSet);

            resultSet.close();

            return item;
        }

        throw new SQLException("Can't get $$Lambda$");
    }

    protected List<T> queryAllObjects(String statement, List<Object> opt) throws SQLException {

        ResultSet resultSet = SQLDatabase.query(statement, opt);
        
        List<T> items = new ArrayList<>();

        while (resultSet.next()) {
            items.add(createObjectFromResult(resultSet));
        }

        resultSet.close();

        return items;
    }

    protected T queryFirstObject(String statement) throws SQLException {
        return queryFirstObject(statement, null);
    }

    protected List<T> queryAllObjects(String statement) throws SQLException {
        return queryAllObjects(statement, null);
    }

    protected T doInsert(String statement, List<Object> opt) throws SQLException {
        ResultSet generatedKey = SQLDatabase.exec(statement, opt);

        if (generatedKey.next())
            return createObjectFromResult(generatedKey);

        throw new SQLException("Can't add this $$Lambda$");
    }
}