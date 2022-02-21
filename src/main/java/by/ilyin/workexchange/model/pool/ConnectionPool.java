package by.ilyin.workexchange.model.pool;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.util.PropertyManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static ConnectionPool instance;
    private static boolean isInitialise = false;
    private static Lock lock = new ReentrantLock();
    private final String PROPERTY_KEY_WORD_CONNECTION_POOL_SIZE = "db.connection_pool_size";
    private final String PROPERTY_KEY_WORD_MIN_CONNECTION_POOL_SIZE = "db.min_connection_pool_size";
    private final int MIN_CONNECTION_POOL_SIZE;
    private int connectionPoolSize;
    private BlockingQueue<Connection> freeConnectionsQueue;
    private BlockingQueue<Connection> busyConnectionsQueue;

    private ConnectionPool() { //todo приостановка, пересчет конекшенов и досоздание новых, если какие-то отвалились в процессе
        PropertyManager propertyManager = PropertyManager.getInstance();
        char[] connectionPoolSizeArr = propertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_CONNECTION_POOL_SIZE);
        StringBuilder sbConnectionCount = new StringBuilder();
        sbConnectionCount.append(connectionPoolSizeArr);
        connectionPoolSize = Integer.parseInt(sbConnectionCount.toString());
        char[] minConnectionPoolSizeArr = propertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_MIN_CONNECTION_POOL_SIZE);
        StringBuilder sbMinConnectionCount = new StringBuilder();
        sbMinConnectionCount.append(minConnectionPoolSizeArr);
        MIN_CONNECTION_POOL_SIZE = Integer.parseInt(sbMinConnectionCount.toString());
        freeConnectionsQueue = new LinkedBlockingQueue<>(connectionPoolSize);
        busyConnectionsQueue = new LinkedBlockingQueue<>(connectionPoolSize);
        MySqlConnectionFactory mySqlConnectionFactory;
        try {
            mySqlConnectionFactory = MySqlConnectionFactory.getInstance();
        } catch (DaoException e) {
            e.printStackTrace();//todo
            throw new RuntimeException("ConnectionPool.class: constructor: MySqlConnectionFactory.getInstance() error. can't create instance..");
        }
        Connection connection;
        for (int currentConnectionCount = 0; currentConnectionCount < this.connectionPoolSize; ++currentConnectionCount) {
            connection = null;
            try {
                connection = mySqlConnectionFactory.getProxyConnection();
            } catch (DaoException e) {
                e.printStackTrace();//todo
                --this.connectionPoolSize; //todo
            }
            if (connection != null) {
                freeConnectionsQueue.add(connection);
            }
        }
        if (this.connectionPoolSize < MIN_CONNECTION_POOL_SIZE) { //todo
            throw new RuntimeException("ConnectionPool.class: constructor(): connection creating error. less than 4"); //todo
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (!isInitialise) {
                    instance = new ConnectionPool();
                    isInitialise = true;
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection takeConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = (ProxyConnection) freeConnectionsQueue.take();
            busyConnectionsQueue.put(proxyConnection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //todo
        }
        return proxyConnection;
    }

    public boolean relieveConnection(Connection connection) {
        if (connection != null && connection.getClass() == ProxyConnection.class) {
            busyConnectionsQueue.remove(connection);
            try {
                freeConnectionsQueue.put(connection);
                return true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    public void destroyConnectionPool() throws DaoException {
        if (freeConnectionsQueue.size() == connectionPoolSize) {
            for (int connectionCount = 0; connectionCount < connectionPoolSize; ++connectionCount) {
                try {
                    ProxyConnection proxyConnection = (ProxyConnection) freeConnectionsQueue.take();
                    proxyConnection.reallyClose();
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();//todo
                }
            }
            deregisterDriver();
            instance = null;
        } else {
            throw new DaoException("ConnectionPool.class: destroyConnectionPool(): not all connections is free");//todo
        }
    }

    private void deregisterDriver() throws DaoException {
        Iterator<Driver> driverIterator = DriverManager.getDrivers().asIterator();
        Driver driverContainer;
        while (driverIterator.hasNext()) {
            driverContainer = driverIterator.next();
            try {
                DriverManager.deregisterDriver(driverContainer);
            } catch (SQLException e) {
                throw new DaoException("ConnectionPool.class: deregisterDriver(): DriverManager.deregisterDriver() error", e);
            }
        }
    }

}
