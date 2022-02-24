package by.ilyin.workexchange.model.pool;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.exception.WorkExchangeAppException;
import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MySqlConnectionFactory {

    private static Logger logger = LogManager.getLogger();
    private static MySqlConnectionFactory instance;
    private final String PROPERTY_KEY_WORD_DB_LOGIN = "db.login";
    private final String PROPERTY_KEY_WORD_DB_PASSWORD = "db.password";
    private final String PROPERTY_KEY_WORD_DB_ADDRESS = "db.address";
    private final String PROPERTY_KEY_WORD_DB_PORT = "db.port";
    private final String PROPERTY_KEY_WORD_DB_NAME = "db.name";
    private DatabasePropertyManager databasePropertyManager;
    private char[] dbAddress;
    private char[] dbPort;
    private char[] dbName;
    private char[] dbLogin;
    private char[] dbPassword;

    private MySqlConnectionFactory() throws DaoException {
        try {
            databasePropertyManager = DatabasePropertyManager.getInstance();
            dbAddress = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_ADDRESS);
            dbPort = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_PORT);
            dbName = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_NAME);
            dbLogin = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_LOGIN);
            dbPassword = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_PASSWORD);
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException cause) {
            throw new DaoException("Driver not found... SQLException", cause);
        }
    }

    public static MySqlConnectionFactory getInstance() throws DaoException {
        if (instance == null) {
            instance = new MySqlConnectionFactory();
        }
        return instance;
    }

    Connection getProxyConnection() throws DaoException {
        //todo clear String Builders
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(dbAddress).append(dbPort).append('/').append(dbName);
        StringBuilder sbLogin = new StringBuilder();
        sbLogin.append(dbLogin);
        StringBuilder sbPassword = new StringBuilder();
        sbPassword.append(dbPassword);
        ProxyConnection proxyConnection;
        try {
            proxyConnection = (ProxyConnection) DriverManager.getConnection(sbUrl.toString(), sbLogin.toString(), sbPassword.toString());
        } catch (SQLException cause) {
            throw new DaoException("MySqlConnectionFactory: createAndGetProxyConnection: DriverManager.getConnection() SQlException", cause);
        }
        return proxyConnection;
    }
}
