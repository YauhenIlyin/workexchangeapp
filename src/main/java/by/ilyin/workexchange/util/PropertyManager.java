package by.ilyin.workexchange.util;

import by.ilyin.workexchange.exception.WorkExchangeAppException;
import by.ilyin.workexchange.validator.FileValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class for connecting .property files via a resource bundle.
 * <b>instance</b>
 * <b>DATABASE_PROPERTY_FILE_PATH</b>
 * <b>databaseResourceBundle</b>
 *
 * @author IlyinYauhen
 * @version 1.0
 */
public class PropertyManager {

    /**
     * A field containing an instance of the PropertyManager class.
     *
     * @see PropertyManager#PropertyManager()
     */
    private static PropertyManager instance;
    /**
     * Default database .property file relative path constant field.
     */
    private final String DATABASE_PROPERTY_FILE_PATH = "data/database/config.properties";
    /**
     * Field containing ResourceBundle object for accessing database .property file.
     */
    private ResourceBundle databaseResourceBundle;

    private static Logger logger = LogManager.getLogger();

    /**
     * Private constructor to create a singleton PropertyManager object.
     *
     * @throws WorkExchangeAppException
     */
    private PropertyManager() throws WorkExchangeAppException {
        FileValidator fileValidator = new FileValidator();
        if (!fileValidator.validateTxtFile(DATABASE_PROPERTY_FILE_PATH)) {
            String message = "PropertyManager() : database .property file not exists.";
            logger.log(Level.ERROR, message);
            throw new WorkExchangeAppException(message);
        }
        databaseResourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY_FILE_PATH);
        logger.log(Level.INFO, "Property files connected successfully.");
    }

    /**
     * Function to create and get an instance of a singleton PropertyManager class {@link PropertyManager#instance}.
     *
     * @return instance - PropertyManager singleton instance.
     * @throws WorkExchangeAppException
     */
    public static PropertyManager getInstance() throws WorkExchangeAppException {
        if (instance == null) {
            instance = new PropertyManager();
            logger.log(Level.INFO, "Property manager instance successfully created.");
        }
        return instance;
    }

    /**
     * @param propertyName - keyword for property value.
     * @return propertyValue - string value from property file corresponding to the keyword as char[].
     */
    public char[] getDatabasePropertyValue(String propertyName) throws WorkExchangeAppException {
        //Locale locale = new Locale("en", "US"); //todo
        char[] propertyValue = null;
        try {
            propertyValue = databaseResourceBundle.getString(propertyName).toCharArray();
        } catch (MissingResourceException | NullPointerException cause) {
            String message = "PropertyManager.getDatabasePropertyValue : value is not found for keyword | propertyName string is null";
            logger.log(Level.ERROR, message);
            throw new WorkExchangeAppException(message, cause);
        }
        return propertyValue;
    }
}
