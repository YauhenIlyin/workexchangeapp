package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.entity.BaseEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractDao<T extends BaseEntity> {

    private static Logger logger = LogManager.getLogger();
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeStatement(Statement statement) throws DaoException {
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

    @Override
    public void closeConnection(Connection connection) throws DaoException {
        UserDao.super.close(connection);
    }

}
