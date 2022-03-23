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
        try {
            if (connection == null) {
                connection = ConnectionPool.getInstance().takeConnection();
            }
            connection.setAutoCommit(false);
        } catch (SQLException cause) {
            cause.printStackTrace();//todo
        } catch (ConnectionPoolException cause) {
            cause.printStackTrace();//todo
        }
        dao.setConnection(connection);
        for (T currentDao : daoArr) {
            currentDao.setConnection(connection);
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
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace(); //todo
        }

    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
