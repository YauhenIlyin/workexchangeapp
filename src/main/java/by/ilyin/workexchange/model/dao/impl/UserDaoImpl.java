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
import java.util.Optional;

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

    private static final String CS_SQL_EXPRESSION_IS_FREE_ACCOUNT_LOGIN = "{call isFreeAccountLogin(?)}";
    private static final String CS_SQL_EXPRESSION_ADD_CREATED_ACCOUNT_PASS_BY_USER_LOGIN = "{call addCreatedAccountPassByUserLogin(?,?)}";

    private PreparedStatement findAllUsersPS;
    private PreparedStatement findUserByIdPS;
    private PreparedStatement addUserPS;
    private CallableStatement isFreeAccountLoginCS;
    private CallableStatement addPassByUserLoginCS;

    @Override
    public List<Optional<User>> findAll() throws DaoException { //todo optional ?
        ArrayList<Optional<User>> userList;
        try {
            if (findAllUsersPS == null) {
                findAllUsersPS = connection.prepareStatement(PS_SQL_EXPRESSION_FIND_ALL_USERS);
            }
            ResultSet resultSet = findAllUsersPS.executeQuery();
            userList = buildUserListFromResultSet(resultSet);
        } catch (SQLException cause) {
            throw new DaoException(cause); //todo нужно ли дополнительное сообщение
        }
        return userList;
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException { //todo optional
        Optional<User> optionalUser;
        try {
            ResultSet resultSet;
            if (findUserByIdPS == null) {
                findUserByIdPS = connection.prepareStatement(PS_SQL_EXPRESSION_FIND_USER_BY_ID);
            }
            findUserByIdPS.setLong(1, id);
            findUserByIdPS.executeQuery();
            resultSet = findUserByIdPS.getResultSet();
            optionalUser = buildUserListFromResultSet(resultSet).get(0);
        } catch (SQLException cause) {
            throw new DaoException(cause);
        }
        return optionalUser;
    }

    /*
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
     */
    //todo почистить код
    public boolean isFreeAccountLogin(char[] login) throws DaoException { //todo учесть случай, когда пустые ячейки в массиве
        StringBuilder sb = new StringBuilder(login.length);
        sb.append(login);
        try {
            if (isFreeAccountLoginCS == null) {
                isFreeAccountLoginCS = connection.prepareCall(CS_SQL_EXPRESSION_IS_FREE_ACCOUNT_LOGIN);
            }
            isFreeAccountLoginCS.setString(1, sb.toString());
            isFreeAccountLoginCS.registerOutParameter(1, Types.BOOLEAN);
            isFreeAccountLoginCS.execute();
            return isFreeAccountLoginCS.getBoolean(1); //todo 1 или 2
        } catch (SQLException cause) {
            throw new DaoException(cause);
        }
    }

    public boolean addUserAccount(User user, char[] login) throws DaoException {
        if (user != null && login != null && login.length > 0 && isFreeAccountLogin(login)) {
            try {
                if (addUserPS == null) {
                    addUserPS = connection.prepareStatement(PS_SQL_EXPRESSION_ADD_USER);
                }

                addUserPS.setString(1, user.getFirstName());
                addUserPS.setString(2, user.getLastName());
                if (user.getRegistrationDateTime() == null) {
                    user.setRegistrationDateTime(LocalDateTime.now());
                }
                addUserPS.setString(3, user.getRegistrationDateTime().toString());
                if (user.getLastActivityDateTime() == null) {
                    user.setLastActivityDateTime(LocalDateTime.now());
                }
                addUserPS.setString(4, user.getLastActivityDateTime().toString());
                addUserPS.setString(5, user.getEmail());
                addUserPS.setString(6, user.getMobileNumber());
                StringBuilder sb = new StringBuilder(login.length);
                sb.append(login);
                addUserPS.setString(7, sb.toString());
                if (user.getRole() == null) {
                    user.setRole(BASE_USER_ROLE_DESCRIPTION);
                }
                addUserPS.setString(8, user.getRole());
                if (user.getAccountStatus() == null) {
                    user.setAccountStatus(BASE_USER_ACCOUNT_STATUS_DESCRIPTION);
                }
                addUserPS.setString(9, user.getAccountStatus());
                addUserPS.executeQuery();
                return true;
            } catch (SQLException cause) {
                throw new DaoException(cause);
            }
        } else {
            return false;
        }
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
    public void closeStatement(Statement statement) {
        super.closeStatement(findAllUsersPS);
        super.closeStatement(findUserByIdPS);
        super.closeStatement(addUserPS);
        super.closeStatement(isFreeAccountLoginCS);
        super.closeStatement(addPassByUserLoginCS);
    }

    @Override
    public void closeConnection(Connection connection) {
        super.closeConnection(connection);
    }

    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    private ArrayList<Optional<User>> buildUserListFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Optional<User>> optionalUserList = new ArrayList<>();
        boolean isWorking = true;
        User user = null;
        while (isWorking) {
            if (resultSet.next()) {
                user = new User();
                user.createInnerBuilder()
                        .setId(resultSet.getInt(DatabaseColumnNames.USERS_ID))
                        .setFirstName(resultSet.getString(DatabaseColumnNames.USERS_FIRST_NAME))
                        .setLastName(resultSet.getString(DatabaseColumnNames.USERS_LAST_NAME))
                        .setRegistrationDate(LocalDateTime.parse(resultSet.getString(DatabaseColumnNames.USERS_REGISTRATION_DATE), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .setLastActivityDate(LocalDateTime.parse(resultSet.getString(DatabaseColumnNames.USERS_LAST_ACTIVITY_DATE), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .setEmail(resultSet.getString(DatabaseColumnNames.USERS_E_MAIL))
                        .setMobileNumber(resultSet.getString(DatabaseColumnNames.USERS_MOBILE_NUMBER))
                        .setRole(resultSet.getString(DatabaseColumnNames.USER_ROLE_ROLE_DESCRIPTION))
                        .setAccountStatus(resultSet.getString(DatabaseColumnNames.ACCOUNT_STATUS_DESCRIPTION));
                optionalUserList.add(Optional.of(user));
            } else {
                isWorking = false;
            }
        }
        if (user == null) {
            optionalUserList.add(Optional.ofNullable(user));
        }
        return optionalUserList;
    }


}
