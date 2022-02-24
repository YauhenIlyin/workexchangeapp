package by.ilyin.workexchange.model.pool;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.exception.WorkExchangeAppException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static ConnectionPool instance;
    private static boolean isInitialise = false;
    private static Lock initializationLock = new ReentrantLock();
    private static Lock serviceLock = new ReentrantLock();
    private final String PROPERTY_KEY_WORD_CONNECTION_POOL_SIZE = "db.connection_pool_size";
    private final String PROPERTY_KEY_WORD_MIN_CONNECTION_POOL_SIZE = "db.min_connection_pool_size";
    private final String PROPERTY_KEY_WORD_SPARE_CONNECTION_POOL_SIZE = "db.spare_connection_pool_size";
    private int minConnectionPoolSize;
    private int connectionPoolSize;
    private int spareConnectionPoolSize;
    private static final int DEFAULT_CONNECTION_POOL_SIZE = 14;
    private static final int DEFAULT_MIN_CONNECTION_POOL_SIZE = 8;
    private static final int DEFAULT_SPARE_CONNECTION_POOL_SIZE = 8;
    private static final int MIN_EFFECTIVE_CONNECTION_POOL = 8;

    private BlockingQueue<Connection> freeConnectionsQueue;
    private BlockingQueue<Connection> busyConnectionsQueue;

    private BlockingQueue<Connection> spareFreeConnectionsQueue;
    private BlockingQueue<Connection> spareBusyConnectionsQueue;
    private static AtomicBoolean isConnectionPoolInService = new AtomicBoolean(false);

    private static Logger logger = LogManager.getLogger();

    private ConnectionPool() throws DaoException { //todo приостановка, пересчет конекшенов и досоздание новых, если какие-то отвалились в процессе
        initializeConnectionPoolSizeParameters();

        freeConnectionsQueue = new LinkedBlockingQueue<>(connectionPoolSize);
        busyConnectionsQueue = new LinkedBlockingQueue<>(connectionPoolSize);

        if (this.connectionPoolSize < minConnectionPoolSize) { //todo
            throw new RuntimeException("ConnectionPool.class: constructor(): connection creating error. less than 4"); //todo
        }
    }

    private void initializeConnectionPoolSizeParameters() throws DaoException {
        DatabasePropertyManager databasePropertyManager = DatabasePropertyManager.getInstance();
        char[] connectionPoolSizeArr = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_CONNECTION_POOL_SIZE);
        StringBuilder sbConnectionCount = new StringBuilder();
        sbConnectionCount.append(connectionPoolSizeArr);
        connectionPoolSize = Integer.parseInt(sbConnectionCount.toString());

        char[] minConnectionPoolSizeArr = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_MIN_CONNECTION_POOL_SIZE);
        StringBuilder sbMinConnectionCount = new StringBuilder();
        sbMinConnectionCount.append(minConnectionPoolSizeArr);
        minConnectionPoolSize = Integer.parseInt(sbMinConnectionCount.toString());

        char[] spareConnectionPoolSizeArr = databasePropertyManager.getDatabasePropertyValue(PROPERTY_KEY_WORD_SPARE_CONNECTION_POOL_SIZE);
        StringBuilder sbSpareConnectionCount = new StringBuilder();
        sbSpareConnectionCount.append(spareConnectionPoolSizeArr);
        spareConnectionPoolSize = Integer.parseInt(sbConnectionCount.toString());

        if (connectionPoolSize < minConnectionPoolSize || minConnectionPoolSize < MIN_EFFECTIVE_CONNECTION_POOL || spareConnectionPoolSize < MIN_EFFECTIVE_CONNECTION_POOL) {
            logger.log(Level.WARN, "Received incorrect connection pool sizes from the config.properties file. Default values used.");
            connectionPoolSize = DEFAULT_CONNECTION_POOL_SIZE;
            minConnectionPoolSize = DEFAULT_MIN_CONNECTION_POOL_SIZE;
            spareConnectionPoolSize = DEFAULT_SPARE_CONNECTION_POOL_SIZE;
        }
    }

    private void initialiseConnectionQueue(BlockingQueue currentConnectionsQueue, int size) {
        currentConnectionsQueue = new LinkedBlockingQueue<>(connectionPoolSize);
        MySqlConnectionFactory mySqlConnectionFactory;
        try {
            mySqlConnectionFactory = MySqlConnectionFactory.getInstance();
        } catch (DaoException e) {
            e.printStackTrace();//todo
            throw new RuntimeException("Can't create MySqlConnectionFactory instance..");
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
    }


    public void closeAndUpdateConnectionsInPool() {
        isConnectionPoolInService.set(true);
        Thread.sleep(500);
        boolean isAllConnectionsFree = false;


        MySqlConnectionFactory mySqlConnectionFactory = null;
        Connection connection;
        connection = mySqlConnectionFactory.getProxyConnection();
        int numberCreationAttempts = 5;
        int attemptNumber = 0;
        while (attemptNumber < numberCreationAttempts) {
            if (freeConnectionsQueue.size() < minConnectionPoolSize) {
                int lostConnectionCount = minConnectionPoolSize - freeConnectionsQueue.size();
                while (lostConnectionCount > 0) {

                }
            }
            ++attemptNumber;
        }

    }

    public static ConnectionPool getInstance() throws WorkExchangeAppException {
        if (instance == null) {
            initializationLock.lock();
            try {
                if (!isInitialise) {
                    instance = new ConnectionPool();
                    isInitialise = true;
                }
            } finally {
                initializationLock.unlock();
            }
        }
        return instance;
    }

    public Connection takeConnection() {
        if (isConnectionPoolInService.get()) {
            serviceLock.lock();
        }
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
