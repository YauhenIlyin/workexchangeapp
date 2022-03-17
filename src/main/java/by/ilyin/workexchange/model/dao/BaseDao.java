package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.model.entity.BaseEntity;
import by.ilyin.workexchange.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends BaseEntity> { //todo проверить модификаторы доступа

    public void setConnection(Connection connection) throws DaoException;

    void closeStatement(Statement statement) throws DaoException;

    void closeConnection(Connection connection) throws DaoException;

    List<Optional<T>> findAll() throws DaoException;

    Optional<T> findEntityById(K id) throws DaoException;

    boolean addEntity(T t) throws DaoException;

    boolean deleteEntity(T t) throws DaoException;

    boolean deleteEntityById(K id) throws DaoException;

    T updateEntity(T t) throws DaoException;

    T updateEntityById(K id) throws DaoException;
}
