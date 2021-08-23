package by.ilyinyauhen.workexchangeapp.model.pool;

import by.ilyinyauhen.workexchangeapp.exception.DaoException;
import by.ilyinyauhen.workexchangeapp.util.PropertyManager;
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

class MySqlConnectionFactory {

    private final String PROPERTY_KEY_WORD_DB_LOGIN = "db.login";
    private final String PROPERTY_KEY_WORD_DB_PASSWORD = "db.password";
    private final String PROPERTY_KEY_WORD_DB_ADDRESS = "db.address";
    private final String PROPERTY_KEY_WORD_DB_PORT = "db.port";
    private final String PROPERTY_KEY_WORD_DB_NAME = "db.name";
    private PropertyManager propertyManager;
    private static AtomicBoolean isInitialise = new AtomicBoolean(false);
    private static MySqlConnectionFactory instance;

    private MySqlConnectionFactory() throws DaoException {
        propertyManager = PropertyManager.getInstance();
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            new DaoException("MySqlConnectionFactory.class: constructor: Driver not found... SQLException", e);
        }
    }

    public static MySqlConnectionFactory getInstance() throws DaoException {
        while (instance == null) {
            if (isInitialise.compareAndSet(false, true)) {
                instance = new MySqlConnectionFactory();
            }
        }
        return instance;
    }

    Connection getProxyConnection() throws DaoException {
        char[] dbAddress = propertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_ADDRESS);
        char[] dbPort = propertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_PORT);
        char[] dbName = propertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_NAME);
        StringBuilder sbUrl = new StringBuilder(45);
        sbUrl.append(dbAddress).append(dbPort).append('/').append(dbName);
        char[] dbLogin = propertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_LOGIN);
        StringBuilder sbLogin = new StringBuilder(10);
        sbLogin.append(dbLogin);
        char[] dbPassword = propertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_PASSWORD);
        StringBuilder sbPassword = new StringBuilder(20);
        sbPassword.append(dbPassword);
        ProxyConnection proxyConnection;
        try {
            proxyConnection = (ProxyConnection) DriverManager.getConnection(sbUrl.toString(), sbLogin.toString(), sbPassword.toString());
        } catch (SQLException e) {
            throw new DaoException("MySqlConnectionFactory: createAndGetProxyConnection: DriverManager.getConnection() SQlException", e);
        }
        return proxyConnection;
    }
}
