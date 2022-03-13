package by.ilyin.workexchange.model.pool;

import by.ilyin.workexchange.exception.DaoException;
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
class DatabasePropertyManager {
    /**
     * A field containing an instance of the DatabasePropertyManager class.
     *
     * @see DatabasePropertyManager#DatabasePropertyManager().
     */
    private static DatabasePropertyManager instance;
    /**
     * Default database .property file relative path constant field.
     */
    private final String DATABASE_PROPERTY_FILE_PATH = "data/config.properties";
    /**
     * Field containing ResourceBundle object for accessing database .property file.
     */
    private ResourceBundle databaseResourceBundle;

    private static Logger logger = LogManager.getLogger();

    /**
     * Private constructor to create a singleton DatabasePropertyManager object.
     *
     * @throws DaoException
     */
    private DatabasePropertyManager() throws DaoException {
        FileValidator fileValidator = new FileValidator();
        if (!fileValidator.validateTxtFile(DATABASE_PROPERTY_FILE_PATH)) {
            String message = "Database .property file not exists.";
            logger.log(Level.ERROR, message);
            throw new DaoException(message);
        }
        databaseResourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY_FILE_PATH);
        logger.log(Level.INFO, "Database property file connected successfully.");
    }

    /**
     * Function to create and get an instance of a singleton DatabasePropertyManager class {@link DatabasePropertyManager#instance}.
     *
     * @return instance - DatabasePropertyManager singleton instance.
     * @throws DaoException
     */
    public static DatabasePropertyManager getInstance() throws DaoException {
        if (instance == null) {
            instance = new DatabasePropertyManager();
            logger.log(Level.INFO, "DatabasePropertyManager instance successfully created.");
        }
        return instance;
    }

    /**
     * @param propertyName - keyword for property value.
     * @return propertyValue - string value from property file corresponding to the keyword as char[].
     * @throws DaoException
     */
    public char[] getDatabasePropertyValue(String propertyName) throws DaoException {
        //Locale locale = new Locale("en", "US"); //todo
        char[] propertyValue = null;
        try {
            propertyValue = databaseResourceBundle.getString(propertyName).toCharArray();
        } catch (MissingResourceException | NullPointerException cause) {
            String message = "Value is not found for keyword or propertyName string is null";
            logger.log(Level.ERROR, message);
            throw new DaoException(message, cause);
        }
        return propertyValue;
    }
}