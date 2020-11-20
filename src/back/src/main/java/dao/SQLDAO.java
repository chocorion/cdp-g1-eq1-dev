package dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public abstract class SQLDAO<T> {

    protected abstract T getObjectFromResult(ResultSet resultSet) throws SQLException;

    protected List<T> getAllObjectsFromResult(ResultSet resultSet) throws SQLException {
        
        List<T> items = new ArrayList<>();

        while (resultSet.next()) {
            items.add(getObjectFromResult(resultSet));
        }

        resultSet.close();

        return items;
    }

    protected T doInsert(String statement, List<Object> opt) throws SQLException {
        ResultSet generatedKey = SQLDAOFactory.exec(statement, opt);

        if (generatedKey.next())
            return getObjectFromResult(generatedKey);

        throw new SQLException("Can't add this $$Lamba$");
    }
}