package by.ilyin.workexchange.util;

import java.util.ResourceBundle;

public class PropertyManager {

    private static PropertyManager instance;
    private final String DATABASE_PROPERTY_FILE_PATH = "data/database/config.properties";
    private ResourceBundle databaseResourceBundle;


    private PropertyManager() {
        databaseResourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY_FILE_PATH);
    }

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    public char[] getDatabasePropertyValue(String propertyName) {
        //Locale locale = new Locale("en", "US"); //todo
        char[] propertyValue = databaseResourceBundle.getString(propertyName).toCharArray();
        return propertyValue;
    }
}
