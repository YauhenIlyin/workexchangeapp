package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.model.entity.BaseEntity;
import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.entity.User;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends BaseEntity> {

    List<T> findAll() throws DaoException;

    Optional<T> findEntityById(K id) throws DaoException;

    boolean addEntity(T t) throws DaoException;

    boolean deleteEntity(T t) throws DaoException;

    boolean deleteEntityById(K id) throws DaoException;

    T updateEntity(T t) throws DaoException;

    void closeStatement(Statement statement) throws DaoException;

    void closeConnection(Connection connection) throws DaoException;

}
