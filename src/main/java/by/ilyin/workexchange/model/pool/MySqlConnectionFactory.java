package by.ilyin.workexchange.model.pool;

import by.ilyin.workexchange.exception.ConnectionPoolException;
import by.ilyin.workexchange.util.SecurityDataCleaner;
import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionFactory { //todo убрать паблик

    private static Logger logger = LogManager.getLogger();
    private static MySqlConnectionFactory instance;
    private final String PROPERTY_KEY_WORD_DB_LOGIN = "db.login";
    private final String PROPERTY_KEY_WORD_DB_PASSWORD = "db.password";
    private final String PROPERTY_KEY_WORD_DB_ADDRESS = "db.address";
    private final String PROPERTY_KEY_WORD_DB_PORT = "db.port";
    private final String PROPERTY_KEY_WORD_DB_NAME = "db.name";
    private DatabasePropertyManager databasePropertyManager;
    //todo сделать так,чтобы после заполнения пула char[]  очищались,
    //todo а при обновлении снова инициализировались
    private char[] dbAddress;
    private char[] dbPort;
    private char[] dbName;
    private char[] dbLogin;
    private char[] dbPassword;

    private MySqlConnectionFactory() throws ConnectionPoolException {
        try {
            databasePropertyManager = DatabasePropertyManager.getInstance();
            dbAddress = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_ADDRESS);
            dbPort = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_PORT);
            dbName = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_NAME);
            dbLogin = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_LOGIN);
            dbPassword = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_DB_PASSWORD);
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            logger.debug("DriverManager.registerDriver(driver) register successful");
        } catch (SQLException cause) {
            String message = "Driver not found... SQLException";
            logger.debug(message);
            throw new ConnectionPoolException(message, cause);
        }
    }

    public static MySqlConnectionFactory getInstance() throws ConnectionPoolException {
        if (instance == null) {
            logger.debug("getInstance() start of instance initialization");
            instance = new MySqlConnectionFactory();
            logger.debug("getInstance() instance initialization complete");
        }
        return instance;
    }

    Connection getProxyConnection() throws ConnectionPoolException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(dbAddress).append(dbPort).append('/').append(dbName);
        StringBuilder sbLogin = new StringBuilder();
        sbLogin.append(dbLogin);
        StringBuilder sbPassword = new StringBuilder();
        sbPassword.append(dbPassword);
        ProxyConnection proxyConnection;
        System.out.println(sbLogin.toString() + " " + sbPassword + " " + sbUrl);
        try {
            logger.debug("getProxyConnection() start proxyConnection initialisation");
            Connection connection = DriverManager.getConnection(sbUrl.toString(), sbLogin.toString(), sbPassword.toString());
            proxyConnection = new ProxyConnection(connection);
            logger.debug("getProxyConnection() proxyConnection initialisation is successful");
        } catch (SQLException cause) {
            cause.printStackTrace();
            logger.debug("getProxyConnection() SQLException in proxyConnection initialisation");
            throw new ConnectionPoolException("MySqlConnectionFactory: createAndGetProxyConnection: DriverManager.getConnection() SQlException", cause);
        } finally {
            SecurityDataCleaner.cleanStringBuilders(sbLogin, sbPassword, sbUrl);
        }
        System.out.println(123);
        return proxyConnection;
    }

    public void destroyMySqlConnectionFactory() {
        SecurityDataCleaner.cleanCharArrays(dbAddress, dbLogin, dbName, dbPassword, dbPort);
        instance = null;
    }
}
