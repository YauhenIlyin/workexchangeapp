package by.ilyinyauhen.workexchangeapp.util;

import by.ilyinyauhen.workexchangeapp.validator.FileValidator;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class PropertyManager {

    private static AtomicBoolean isInitialise = new AtomicBoolean(false);
    private static PropertyManager instance;
    private final String DATABASE_PROPERTY_FILE_PATH = "data/database/config.properties";
    private ResourceBundle databaseResourceBundle;


    private PropertyManager() {
        if (!FileValidator.validateTxtFile(DATABASE_PROPERTY_FILE_PATH)) {
            throw new RuntimeException("PropertyManager.class: constructor: database property file not exists or empty");
        }
        databaseResourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY_FILE_PATH);
    }

    public static PropertyManager getInstance() {
        while (instance == null) {
            if (isInitialise.compareAndSet(false, true)) {
                instance = new PropertyManager();
            }
        }
        return instance;
    }

    public char[] getDatabasePropertyValue(String propertyName) {
        //Locale locale = new Locale("en", "US"); //todo
        char[] propertyValue = databaseResourceBundle.getString(propertyName).toCharArray();
        return propertyValue;
    }
}
