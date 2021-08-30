package by.ilyinyauhen.workexchangeapp.model.dao.impl;


import by.ilyinyauhen.workexchangeapp.exception.DaoException;
import by.ilyinyauhen.workexchangeapp.model.dao.UserDao;
import by.ilyinyauhen.workexchangeapp.model.entity.User;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String USERS_TABLE_NAME = "users";
    private static final String SQL_EXPRESSION_FIND_ALL_USERS = """
            SELECT users.id, users.first_name, users.last_name, users.registration_date, 
            users.last_activity_date, users.e_mail, users.mobile_number, 
            (SELECT user_role.role_description FROM user_role WHERE user_role.id = users.user_role_id ), 
            (SELECT account_status.account_status_description FROM account_status WHERE account_status.id = users.account_status_id)
            FROM users
            """;

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public User findEntityById(Long id) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteEntity(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteEntityById(Long id) throws DaoException {
        return false;
    }

    public boolean createEntity(User user, char[] login, char[] password) throws DaoException {
        return false;
    }

    @Override
    public boolean activateAccount(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean activateAccountById(Long id) throws DaoException {
        return false;
    }

    @Override
    public User updateEntity(User user) throws DaoException {
        return null;
    }

    @Override
    public void close(Statement statement) throws DaoException {
        UserDao.super.close(statement);
    }

    @Override
    public void close(Connection connection) throws DaoException {
        UserDao.super.close(connection);
    }


}
