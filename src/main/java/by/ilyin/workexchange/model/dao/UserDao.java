package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.entity.User;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    //todo add methods from impl

    public List<User> findAll() throws DaoException;

    public Optional<User> findEntityById(Long id) throws DaoException;

    public boolean isFreeAccountLogin(char[] login) throws DaoException;

    public boolean registerNewAccount(User user, char[] login, char[] password) throws DaoException;

    public boolean addEntity(User user) throws DaoException;

    public boolean deleteEntity(User user) throws DaoException;

    public boolean deleteEntityById(Long id) throws DaoException;

    public boolean activateAccount(User user) throws DaoException;

    public boolean activateAccountById(Long id) throws DaoException;

    public User updateEntity(User user) throws DaoException;

    public void closeStatement(Statement statement) throws DaoException;

    public void setConnection(Connection connection) throws DaoException;

    public void closeConnection(Connection connection) throws DaoException;

}
