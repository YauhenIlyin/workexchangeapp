package by.ilyinyauhen.workexchangeapp.model.dao;

import by.ilyinyauhen.workexchangeapp.model.pool.ConnectionPool;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class EntityTransaction {

    public static Logger logger;
    private Connection connection;

    public void initTransaction(AbstractDao dao, AbstractDao... daoArr) {
        if (connection == null) {
            connection = ConnectionPool.getInstance().takeConnection();
        }
        dao.setConnection(connection);
        for (AbstractDao currentDao : daoArr) {
            currentDao.setConnection(connection);
        }
    }

    public void initSingleOperation(AbstractDao dao) {
        if (connection == null) {
            connection = ConnectionPool.getInstance().takeConnection();
        }
        connection.setAutoCommit(false);
        dao.setConnection(connection);
    }

    public void endTransaction() {
        if (connection != null && !connection.getAutoCommit()) {
            connection.setAutoCommit(true);
        }
        connection = null;
    }

    public void commit() {
        connection.commit();

    }


}
