package by.ilyinyauhen.workexchangeapp.model.dao;

import by.ilyinyauhen.workexchangeapp.model.entity.BaseEntity;
import by.ilyinyauhen.workexchangeapp.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<K, T extends BaseEntity> {

    List<T> findAll() throws DaoException;

    T findEntityById(K id) throws DaoException;

    boolean deleteEntity(T t) throws DaoException;

    boolean deleteEntityById(K id) throws DaoException;

    boolean createEntity(T t) throws DaoException;

    boolean activateAccount(T t) throws DaoException;

    boolean activateAccountById(K id) throws DaoException;

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

    enum CrudOperationName {
        INSERT,
        SELECT,
        UPDATE,
        DELETE
    }

}
