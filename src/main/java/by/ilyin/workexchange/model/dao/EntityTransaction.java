package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.model.pool.ConnectionPool;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {

    public static Logger logger; //todo
    private Connection connection;

    public void initTransaction(AbstractDao dao, AbstractDao... daoArr) {
        if (connection == null) {
            connection = ConnectionPool.getInstance().takeConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();//todo
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
        dao.setConnection(connection);
    }

    public void endTransaction() {
        try {
            if (connection != null && !connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException throwables) {
            //todo
        }
        connection = null;
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace(); //todo
        }

    }

}
