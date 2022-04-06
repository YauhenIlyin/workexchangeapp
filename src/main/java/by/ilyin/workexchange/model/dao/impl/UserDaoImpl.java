package by.ilyin.workexchange.model.dao.impl;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.dao.AbstractDao;
import by.ilyin.workexchange.model.dao.UserDao;
import by.ilyin.workexchange.model.entity.User;
import by.ilyin.workexchange.model.evidence.AccountStatus;
import by.ilyin.workexchange.model.evidence.UserRole;
import by.ilyin.workexchange.model.evidence.dbnames.DatabaseColumnNames;
import by.ilyin.workexchange.util.SecurityDataCleaner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
            """; //todo при выборе нескольких id генерировать expression склеивая в конце ?, ?, ?, ?, в стринг билдере
    private static final String PS_SQL_EXPRESSION_FIND_USER_BY_LOGIN = """
            SELECT id, first_name, last_name, registration_date, 
            last_activity_date, e_mail, mobile_number, 
            (SELECT role_description FROM user_role WHERE user_role.id = users.user_role_id ), 
            (SELECT account_status_description FROM account_status WHERE account_status.id = users.account_status_id)
            FROM users
            WHERE login = ?;         
            """; //todo при выборе нескольких id генерировать expression склеивая в конце ?, ?, ?, ?, в стринг билдере
    private static final String PS_SQL_EXPRESSION_ADD_USER_ACCOUNT = """
            INSERT INTO users(first_name, last_name, registration_date,
            last_activity_date, e_mail, mobile_number, login, user_role_id, account_status_id)
            VALUES (
            ?,?,?,?,?,?,?,
            (SELECT user_role.id FROM user_role WHERE user_role.role_description = ?),
            (SELECT account_status.id FROM account_status WHERE account_status_description = ?)
            );
            """;
    private static final String CS_SQL_EXPRESSION_IS_FREE_ACCOUNT_LOGIN = "{call isFreeAccountLoginProcedure(?,?)}";
    private static final String CS_SQL_EXPRESSION_UPDATE_ACCOUNT_PASS_BY_USER_LOGIN = "{call updateAccountPassByUserLoginProcedure(?,?)}";
    private static final String CS_SQL_EXPRESSION_GET_ACTIVATION_CODE_BY_USER_LOGIN = "{call saveAndGetActivationCodeByLoginProcedure(?,?)}";
    private static final String CS_SQL_EXPRESSION_GET_USER_ID_BY_ACTIVATION_CODE = "{call getUserIdByActivationCodeProcedure(?,?)}";
    private static final String CS_SQL_EXPRESSION_ACTIVATE_ACCOUNT_BY_ID = "{call activateAccountByID(?)}";

    private static final Logger logger = LogManager.getLogger();
    //todo ставить false и отключать блок if в каждом методе закрывающий свой Statement

    @Override
    public List<Optional<User>> findAllEntities() throws DaoException {
        ArrayList<Optional<User>> userList;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            preparedStatement = super.connection.prepareStatement(PS_SQL_EXPRESSION_FIND_ALL_USERS);
            resultSet = preparedStatement.executeQuery();
            userList = buildUserListFromResultSet(resultSet);
        } catch (SQLException cause) {
            throw new DaoException(cause); //todo нужно ли дополнительное сообщение
        } finally {
            super.closeStatement(preparedStatement);
        }
        return userList;
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException { //todo optional
        Optional<User> optionalUser;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            preparedStatement = super.connection.prepareStatement(PS_SQL_EXPRESSION_FIND_USER_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            optionalUser = buildUserListFromResultSet(resultSet).get(0);
        } catch (SQLException cause) {
            throw new DaoException(cause);
        } finally {
            super.closeStatement(preparedStatement);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findEntityByLogin(char[] login) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        Optional<User> optionalUser;
        StringBuilder loginSB = new StringBuilder(login.length);
        loginSB.append(login);
        try {
            preparedStatement = super.connection.prepareStatement(PS_SQL_EXPRESSION_FIND_USER_BY_ID);
            preparedStatement.setString(1, loginSB.toString());
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            optionalUser = buildUserListFromResultSet(resultSet).get(0);
        } catch (SQLException cause) {
            throw new DaoException(cause);
        } finally {
            super.closeStatement(preparedStatement);
            SecurityDataCleaner.cleanStringBuilders(loginSB);
        }
        return optionalUser;
    }

    //todo почистить код
    public boolean isFreeAccountLogin(char[] login) throws DaoException {
        CallableStatement callableStatement = null;
        boolean isFree;
        StringBuilder loginSB = new StringBuilder(login.length);
        loginSB.append(login);
        System.out.println("login: " + loginSB.toString()); //todo
        try {
            callableStatement = super.connection.prepareCall(CS_SQL_EXPRESSION_IS_FREE_ACCOUNT_LOGIN);
            callableStatement.setString(1, loginSB.toString());
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.execute();
            isFree = callableStatement.getBoolean(2);
            System.out.println(isFree);
            return isFree;
        } catch (SQLException cause) {
            throw new DaoException(cause);
        } finally {
            super.closeStatement(callableStatement);
            SecurityDataCleaner.cleanStringBuilders(loginSB);
        }
    }

    //todo почему бы не хранить в StringBuilder  вместо чар
    @Override
    public void updateAccountPasswordByLogin(char[] login, char[] password) throws DaoException {
        CallableStatement callableStatement = null;
        StringBuilder loginSB = new StringBuilder(login.length);
        loginSB.append(login);
        StringBuilder passwordSB = new StringBuilder(password.length);
        passwordSB.append(passwordSB);
        try {
            callableStatement = super.connection.prepareCall(CS_SQL_EXPRESSION_UPDATE_ACCOUNT_PASS_BY_USER_LOGIN);
            callableStatement.setString(1, loginSB.toString());
            callableStatement.setString(2, passwordSB.toString());
            callableStatement.execute();
        } catch (SQLException cause) {
            throw new DaoException(cause);
        } finally {
            super.closeStatement(callableStatement);
            SecurityDataCleaner.cleanStringBuilders(loginSB, passwordSB);
        }
    }

    @Override
    public void updateAccountPasswordById(long id, char[] password) throws DaoException {
    }

    public boolean addAccountWithoutPassword(User user, char[] login) throws DaoException {
        boolean result;
        if (user != null && login != null && login.length > 0) {
            PreparedStatement preparedStatement = null;
            StringBuilder loginSB;
            loginSB = new StringBuilder(login.length);
            try {
                preparedStatement = super.connection.prepareStatement(PS_SQL_EXPRESSION_ADD_USER_ACCOUNT);
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
                loginSB.append(login);
                preparedStatement.setString(7, loginSB.toString());
                if (user.getRole() == null) {
                    user.setRole(BASE_USER_ROLE_DESCRIPTION);
                }
                preparedStatement.setString(8, user.getRole());
                if (user.getAccountStatus() == null) {
                    user.setAccountStatus(BASE_USER_ACCOUNT_STATUS_DESCRIPTION);
                }
                preparedStatement.setString(9, user.getAccountStatus());
                logger.debug("all user parameters set in PreparedStatement");
                preparedStatement.executeUpdate();
                logger.debug("successful executeQuery(). User account without password added to database");
                result = true;
            } catch (SQLException cause) {
                throw new DaoException(cause);
            } finally {
                super.closeStatement(preparedStatement); //todo в finally
                SecurityDataCleaner.cleanStringBuilders(loginSB);
            }
        } else {
            logger.log(Level.WARN, "Login, user is empty or such login is occupied.");
            result = false;
        }
        return result;
    }

    public boolean activateAccountById(long userId) throws DaoException {
        CallableStatement callableStatement = null;
        try {
            callableStatement = super.connection.prepareCall(CS_SQL_EXPRESSION_ACTIVATE_ACCOUNT_BY_ID);
            callableStatement.setLong(1, userId);
            return callableStatement.execute();
        } catch (SQLException cause) {
            throw new DaoException(cause);
        } finally {
            super.closeStatement(callableStatement);
        }
    }

    public Optional<Long> findAccountIdByActivationCode(String activationCode) throws DaoException {
        try {
            System.out.println("length: " + activationCode.length() + " activationCode: " + activationCode);
            CallableStatement callableStatement = super.connection.prepareCall(CS_SQL_EXPRESSION_GET_USER_ID_BY_ACTIVATION_CODE);
            callableStatement.setString(1, activationCode);
            callableStatement.registerOutParameter(2, Types.BIGINT);
            System.out.println("helloooooo");
            callableStatement.execute();
            Optional<Long> optionalUserId = Optional.ofNullable(callableStatement.getLong(2));
            return optionalUserId;
        } catch (SQLException cause) {
            throw new DaoException(cause);
        }

    }

    public String getActivationCodeByUserLogin(char[] login) throws DaoException {
        StringBuilder loginSB = new StringBuilder(login.length);
        loginSB.append(login);
        String activationCode;
        try {
            CallableStatement callableStatement = super.connection.prepareCall(CS_SQL_EXPRESSION_GET_ACTIVATION_CODE_BY_USER_LOGIN);
            callableStatement.setString(1, loginSB.toString());
            System.out.println("111111");
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            System.out.println("222222");
            callableStatement.execute();
            System.out.println("33333");
            activationCode = callableStatement.getString(2);
            System.out.println("44444");
        } catch (SQLException cause) {
            throw new DaoException(cause);
        } finally {
            SecurityDataCleaner.cleanStringBuilders(loginSB);
        }
        return activationCode;
    }

    @Override
    public boolean checkAuthAccountLoginPass(char[] login, char[] password, long userId) {
        StringBuilder loginSB = new StringBuilder(login.length);
        loginSB.append(login);
        StringBuilder passSB = new StringBuilder(password.length);
        passSB.append(password);

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
    public User updateEntity(User user) throws DaoException {
        return null;
    }

    private ArrayList<Optional<User>> buildUserListFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Optional<User>> optionalUserList = new ArrayList<>();
        boolean isWorking = true;
        User user = null;
        while (isWorking) {
            if (resultSet.next()) {
                user = new User();
                user.createInnerBuilder().setId(resultSet.getInt(DatabaseColumnNames.USERS_ID)).setFirstName(resultSet.getString(DatabaseColumnNames.USERS_FIRST_NAME)).setLastName(resultSet.getString(DatabaseColumnNames.USERS_LAST_NAME)).setRegistrationDate(LocalDateTime.parse(resultSet.getString(DatabaseColumnNames.USERS_REGISTRATION_DATE), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).setLastActivityDate(LocalDateTime.parse(resultSet.getString(DatabaseColumnNames.USERS_LAST_ACTIVITY_DATE), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).setEmail(resultSet.getString(DatabaseColumnNames.USERS_E_MAIL)).setMobileNumber(resultSet.getString(DatabaseColumnNames.USERS_MOBILE_NUMBER)).setRole(resultSet.getString(DatabaseColumnNames.USER_ROLE_ROLE_DESCRIPTION)).setAccountStatus(resultSet.getString(DatabaseColumnNames.ACCOUNT_STATUS_DESCRIPTION));
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
