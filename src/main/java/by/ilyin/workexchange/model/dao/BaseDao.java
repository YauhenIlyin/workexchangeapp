package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.model.entity.BaseEntity;
import by.ilyin.workexchange.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<K, T extends BaseEntity> {

    List<T> findAll() throws DaoException;

    T findEntityById(K id) throws DaoException;

    boolean addEntity(T t) throws DaoException;

    boolean deleteEntity(T t) throws DaoException;

    boolean deleteEntityById(K id) throws DaoException;

    T updateEntity(T t) throws DaoException;

    default void close(Statement statement) throws DaoException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("BaseDao.interface: default close(Statement x): statement.close() error", e);
        }
    }

    default void close(Connection connection) throws DaoException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DaoException("BaseDao.interface: default close(Connection x): connection.close() error", e);
        }
    }

}