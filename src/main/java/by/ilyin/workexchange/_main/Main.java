package by.ilyin.workexchange._main;

import by.ilyin.workexchange.exception.ConnectionPoolException;
import by.ilyin.workexchange.model.dao.impl.UserDaoImpl;
import by.ilyin.workexchange.model.pool.ConnectionPool;
import by.ilyin.workexchange.validator.FileValidator;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.sql.*;
import java.util.ResourceBundle;

public class Main {

    public static void main(String args[]) throws SQLException, ConnectionPoolException {
        /*
        FileValidator fileValidator = new FileValidator();
        if (!fileValidator.validateTxtFile("./src/main/resources/data/config.properties")) {
            String message = "Database .property file not exists.";
            throw new ConnectionPoolException(message);
        }
         */
        ResourceBundle databaseResourceBundle = ResourceBundle.getBundle("data/config");
    }
}
