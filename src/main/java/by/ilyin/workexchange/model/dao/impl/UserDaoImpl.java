package by.ilyin.workexchange.model.dao.impl;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.dao.AbstractDao;
import by.ilyin.workexchange.model.dao.UserDao;
import by.ilyin.workexchange.model.entity.User;
import by.ilyin.workexchange.model.evidence.AccountStatus;
import by.ilyin.workexchange.model.evidence.UserRole;
import by.ilyin.workexchange.model.evidence.dbnames.DatabaseColumnNames;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final String BASE_USER_ROLE_DESCRIPTION = UserRole.SIMPLE_USER;
    private static final String BASE_USER_ACCOUNT_STATUS_DESCRIPTION = AccountStatus.WAITING_ACTIVATION;
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
    private static final String PS_SQL_EXPRESSION_ADD_USER = """
            INSERT INTO users(first_name, last_name, registration_date,
            last_activity_date, e_mail, mobile_number, login, user_role_id, account_status_id)
            VALUES (
            ?,?,?,?,?,?,?,
            (SELECT user_role.id FROM user_role WHERE user_role.role_description = ?),
            (SELECT account_status.account_status_id FROM account_status WHERE account_status_description = ?)
            );
            """; //todo
    private static final String PS_SQL_EXPRESSION_VALIDATE_NEW_LOGIN = """
            SELECT count(*) FROM users WHERE users.login = ?;
            """;

    @Override
    public List<User> findAll() { //todo optional ?
        ArrayList<User> userList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(PS_SQL_EXPRESSION_FIND_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            userList = buildUserListFromResultSet(resultSet);
        } catch (SQLException throwables) { //todo
            throwables.printStackTrace();
        }
        return userList;
    }

    @Override
    public User findEntityById(Long id) throws DaoException { //todo optional
        ArrayList<User> userList = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(PS_SQL_EXPRESSION_FIND_USER_BY_ID);
            statement.setLong(1, id);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            userList = buildUserListFromResultSet(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList.get(0); //todo optional

    }

    public boolean validateAccountLogin(char[] login) {
        ResultSet resultSet;
        int numberOfMatches = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(PS_SQL_EXPRESSION_VALIDATE_NEW_LOGIN);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            numberOfMatches = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace(); //todo
        }
        return numberOfMatches == 0;
    }


    public boolean registerNewAccount(User user, char[] login, char[] password) throws DaoException {
        if (validateAccountLogin(login)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(PS_SQL_EXPRESSION_ADD_USER);
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                if (user.getRegistrationDateTime() == null) {
                    user.setRegistrationDateTime(LocalDateTime.now());
                }
                preparedStatement.setString(3, user.getRegistrationDateTime().toString());
                if (user.getLastActivityDateTime() == null) {
                    user.setLastActivityDateTime(LocalDateTime.now());
                }
                preparedStatement.setString(4, user.getLastActivityDateTime().toString());
                preparedStatement.setString(5, user.getEmail());
                preparedStatement.setString(6, user.getMobileNumber());
                StringBuilder sb = new StringBuilder();
                sb.append(login);
                preparedStatement.setString(7, sb.toString());
                if (user.getRole() == null) {
                    user.setRole(BASE_USER_ROLE_DESCRIPTION);
                }
                preparedStatement.setString(8, user.getRole());
                if (user.getAccountStatus() == null) {
                    user.setAccountStatus(BASE_USER_ACCOUNT_STATUS_DESCRIPTION);
                }
                preparedStatement.setString(9, user.getAccountStatus());
                preparedStatement.executeQuery();
            } catch (SQLException e) {
                throw new DaoException(" 123", e);//todo
            }
            //todo email sending and validation
            return true;
        }
        return false;
    }

    @Override
    public boolean addEntity(User user) throws DaoException {
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

    public boolean activateAccount(User user) throws DaoException {
        return false;
    }

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

    private ArrayList<User> buildUserListFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.createInnerBuilder()
                    .setId(resultSet.getInt(DatabaseColumnNames.USERS_ID))
                    .setFirstName(resultSet.getString(DatabaseColumnNames.USERS_FIRST_NAME))
                    .setLastName(resultSet.getString(DatabaseColumnNames.USERS_LAST_NAME))
                    .setRegistrationDate(LocalDateTime.parse(resultSet.getString(DatabaseColumnNames.USERS_REGISTRATION_DATE), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .setLastActivityDate(LocalDateTime.parse(resultSet.getString(DatabaseColumnNames.USERS_LAST_ACTIVITY_DATE), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .setEmail(resultSet.getString(DatabaseColumnNames.USERS_E_MAIL))
                    .setMobileNumber(resultSet.getString(DatabaseColumnNames.USERS_MOBILE_NUMBER))
                    .setRole(resultSet.getString(DatabaseColumnNames.ROLE_DESCRIPTION))
                    .setAccountStatus(resultSet.getString(DatabaseColumnNames.ACCOUNT_STATUS_DESCRIPTOR));
            userList.add(user);
        }
        return userList;
    }


}
