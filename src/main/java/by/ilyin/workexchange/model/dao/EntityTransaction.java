package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.exception.ConnectionPoolException;
import by.ilyin.workexchange.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction<T extends AbstractDao> {

    private Connection connection;

    private static final Logger logger = LogManager.getLogger();

    public void initTransaction(T dao, T... daoArr) {
        logger.debug("start initTransaction()");
        try {
            if (connection == null) {
                connection = ConnectionPool.getInstance().takeConnection();
            }
            logger.debug("connection entered transaction");
            connection.setAutoCommit(false);
        } catch (SQLException cause) {
            cause.printStackTrace();//todo
        } catch (ConnectionPoolException cause) {
            cause.printStackTrace();//todo
        }
        dao.setConnection(connection);
        for (T currentDao : daoArr) {
            currentDao.setConnection(connection);
            logger.debug("connection set in dao: " + currentDao.getClass().getSimpleName());
        }
    }

    public void endTransaction() {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            connection.close();
            connection = null;
        } catch (SQLException cause) {
            //todo
        }
        logger.debug("end transaction");
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace(); //todo
        }
        logger.debug("commit transaction");
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("rollback transaction");
    }

}
