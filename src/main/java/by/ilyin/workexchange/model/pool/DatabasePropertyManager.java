package by.ilyin.workexchange.model.pool;

import by.ilyin.workexchange.exception.ConnectionPoolException;
import by.ilyin.workexchange.validator.FileValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class for connecting database .property files via a resource bundle.
 * <b>instance</b>
 * <b>DATABASE_PROPERTY_FILE_PATH</b>
 * <b>databaseResourceBundle</b>
 *
 * @author IlyinYauhen
 * @version 1.0
 */
public class DatabasePropertyManager { //todo убрать public
    /**
     * A field containing an instance of the DatabasePropertyManager class.
     *
     * @see DatabasePropertyManager#DatabasePropertyManager().
     */
    private static DatabasePropertyManager instance;
    /**
     * Default database .property file relative path constant field.
     */
    private final String DATABASE_PROPERTY_FILE_PATH = "./src/main/resources/data/config.properties";
    private final String DATABASE_PROPERTY_BUNDLE_FILE_PATH = "data/config";
    /**
     * Field containing ResourceBundle object for accessing database .property file.
     */
    private ResourceBundle databaseResourceBundle;

    private static Logger logger = LogManager.getLogger();

    /**
     * Private constructor to create a singleton DatabasePropertyManager object.
     *
     * @throws ConnectionPoolException
     */
    private DatabasePropertyManager() throws ConnectionPoolException {
        FileValidator fileValidator = new FileValidator();
        //todo поправить пути
        if (!fileValidator.validateTxtFile("E:\\programming\\epam_web\\workexchangeapp\\src\\main\\resources\\data\\config.properties")) {
            String message = "Database .property file not exists.";
            logger.log(Level.ERROR, message);
            throw new RuntimeException(message);
        }
        databaseResourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY_BUNDLE_FILE_PATH);
        logger.log(Level.INFO, "Database property file connected successfully.");
    }

    /**
     * Function to create and get an instance of a singleton DatabasePropertyManager class {@link DatabasePropertyManager#instance}.
     *
     * @return instance - DatabasePropertyManager singleton instance.
     * @throws ConnectionPoolException
     */
    public static DatabasePropertyManager getInstance() throws ConnectionPoolException {
        if (instance == null) {
            logger.debug("getInstance() start initialization an instance");
            instance = new DatabasePropertyManager();
            logger.log(Level.INFO, "DatabasePropertyManager instance successfully created.");
        }
        return instance;
    }

    /**
     * @param propertyName - keyword for property value.
     * @return propertyValue - string value from property file corresponding to the keyword as char[].
     * @throws ConnectionPoolException
     */
    public char[] getDatabasePropertyValue(String propertyName) throws ConnectionPoolException {
        //Locale locale = new Locale("en", "US"); //todo
        char[] propertyValue = null;
        try {
            propertyValue = databaseResourceBundle.getString(propertyName).toCharArray();
        } catch (MissingResourceException | NullPointerException cause) {
            String message = "Value is not found for keyword or propertyName string is null";
            logger.log(Level.ERROR, message);
            throw new ConnectionPoolException(message, cause);
        }
        return propertyValue;
    }
}
