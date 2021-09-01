package by.ilyinyauhen.workexchangeapp.model.dao.impl;


import by.ilyinyauhen.workexchangeapp.exception.DaoException;
import by.ilyinyauhen.workexchangeapp.model.dao.AbstractDao;
import by.ilyinyauhen.workexchangeapp.model.dao.UserDao;
import by.ilyinyauhen.workexchangeapp.model.entity.User;
import by.ilyinyauhen.workexchangeapp.model.evidence.databasenames.DatabaseColumnNames;
import by.ilyinyauhen.workexchangeapp.model.pool.ConnectionPool;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final String BASE_USER_ROLE_DESCRIPTION = "simple_user";
    private static final String BASE_USER_ACCOUNT_STATUS_DESCRIPTION = "waiting_activation";
    private static final String PS_SQL_EXPRESSION_FIND_ALL_USERS = """
            SELECT id, first_name, last_name, registration_date, 
            last_activity_date, e_mail, mobile_number, 
            (SELECT role_description FROM user_role WHERE user_role.id = users.user_role_id ), 
            (SELECT account_status_description FROM account_status WHERE account_status.id = users.account_status_id)
            FROM users;
            """; //todo
    private static final String PS_SQL_EXPRESSION_FIND_USER_BY_ID = """
            SELECT id, first_name, last_name, registration_date, 
            last_activity_date, e_mail, mobile_number, 
            (SELECT role_description FROM user_role WHERE user_role.id = users.user_role_id ), 
            (SELECT account_status_description FROM account_status WHERE account_status.id = users.account_status_id)
            FROM users
            WHERE id = ?;         
            """;
    private static final String PS_SQL_EXPRESSION_CREATE_USER = """
            INSERT INTO users(first_name, last_name, registration_date,
            last_activity_date, e_mail, mobile_number, user_role_id, account_status_id)
            VALUES (
            ?,?,?,?,?,?,
            (SELECT user_role.id FROM user_role WHERE user_role.role_description = ""),
            (SELECT account_status.account_status_description FROM account_status WHERE account_status.id = users.account_status_id)
            );
            """; //todo

    @Override
    public List<User> findAll() throws DaoException {
        ArrayList<User> userList = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().takeConnection();
        PreparedStatement statement = connection.prepareStatement(PS_SQL_EXPRESSION_FIND_ALL_USERS);
        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            User user = new User();

            user.createInnerBuilder()
                    .setId(resultSet.getInt(DatabaseColumnNames.USERS_ID))
                    .setFirstName(resultSet.getString(DatabaseColumnNames.USERS_FIRST_NAME))
                    .setLastName(resultSet.getString(DatabaseColumnNames.USERS_LAST_NAME))
                    .setRegistrationDate()

            ;
        }

        return null;
    }

    @Override
    public User findEntityById(Long id) throws DaoException {
        return null;
    }

    public boolean validateAccountLogin(char[] login) {
        return false;
    }

    public boolean createEntity(User user, char[] login, char[] password) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteEntity(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteEntityById(Long id) throws DaoException {
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
