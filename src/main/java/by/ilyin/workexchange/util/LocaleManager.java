package by.ilyin.workexchange.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocaleManager {

    private static final Logger logger = LogManager.getLogger();
    private static LocaleManager instance;
    public static final String LOCALE_ROOT_FILE_NAME = "locale.text"; //locale. это папка
    public static final String LOCALE_NAME_EN = "en";
    public static final String LOCALE_COUNTRY_NAME_EN = "US";

    private static ResourceBundle EN_BUNDLE;

    private LocaleManager() {
        try {
            Locale enLocale = new Locale(LOCALE_NAME_EN, LOCALE_COUNTRY_NAME_EN);
            EN_BUNDLE = ResourceBundle.getBundle(LOCALE_ROOT_FILE_NAME, enLocale);
        } catch (MissingResourceException cause) {
            String message = "Locale resource files could not be found";
            logger.error(message);
            throw new RuntimeException();
        }
    }

    public static LocaleManager getInstance() {
        if (instance == null) {
            instance = new LocaleManager();
        }
        return instance;
    }

    public static ResourceBundle getEnBundle() {
        return EN_BUNDLE;
    }


}
