package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for extracting data from result set
 * @param <T> - extraction data type
 */
public interface Handler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
