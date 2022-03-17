package by.ilyin.workexchange.model.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class AbstractDao {

    private static final Logger logger = LogManager.getLogger();
    protected Connection connection; //todo и тогда делаем прайват

    public void setConnection(Connection connection) {  //todo транзакция  с ним на одном уровне, мб package-private
        this.connection = connection;
    }

    public void closeStatement(Statement statement) {
        if (statement == null) {
            logger.log(Level.WARN, "Statement closing isn't possible. Statement is null.");
        } else {
            try {
                statement.close();
            } catch (SQLException cause) {
                logger.log(Level.ERROR, "Can't close the statement.", cause);
            }
        }
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException cause) {
            logger.log(Level.ERROR, "Can't close the connection.", cause);
        }
    }

}
