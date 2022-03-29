package by.ilyin.workexchange.model.pool;

import by.ilyin.workexchange.exception.ConnectionPoolException;
import by.ilyin.workexchange.util.SecurityDataCleaner;
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
    //todo переделать, упростить, отрефакторить, пересмотреть
    private static ConnectionPool instance;
    private static AtomicBoolean isInitialise = new AtomicBoolean(false);
    private static Lock initializationLock = new ReentrantLock();
    private static Lock serviceLock = new ReentrantLock();
    private final String PROPERTY_KEY_WORD_CONNECTION_POOL_SIZE = "db.connection_pool_size";
    private final String PROPERTY_KEY_WORD_MIN_CONNECTION_POOL_SIZE = "db.min_connection_pool_size";
    private final String PROPERTY_KEY_WORD_SPARE_CONNECTION_POOL_SIZE = "db.spare_connection_pool_size";
    private int minConnectionPoolSize;
    private int connectionPoolSize;
    private int spareConnectionPoolSize;
    private int ATTEMPT_WAIT_BE_RELEASED_CONNECTIONS = 40;
    private int SLEEP_TIME_WAIT_BE_RELEASED_CONNECTIONS_IN_ONE_ATTEMPT = 250;
    private static final int DEFAULT_MAX_NUMBER_OF_INITIALIZATIONS_AT_TIME = 3;
    private static final int DEFAULT_CONNECTION_POOL_SIZE = 14;
    private static final int DEFAULT_MIN_CONNECTION_POOL_SIZE = 8;
    private static final int DEFAULT_SPARE_CONNECTION_POOL_SIZE = 8;
    private static final int DEFAULT_MIN_EFFECTIVE_CONNECTION_POOL_SIZE = 4;

    private BlockingQueue<Connection> freeConnectionsQueue;
    private BlockingQueue<Connection> busyConnectionsQueue;

    private static AtomicBoolean isConnectionPoolInService = new AtomicBoolean(false);

    private static Logger logger = LogManager.getLogger();

    private ConnectionPool() throws ConnectionPoolException { //todo приостановка, пересчет конекшенов и досоздание новых, если какие-то отвалились в процессе
        if (instance != null) {
            throw new RuntimeException("Reflection API not allowed. Cannot create second instance.");
        }
        initializeConnectionPoolSizeParameters();
        if (this.connectionPoolSize < minConnectionPoolSize) { //todo
            throw new RuntimeException("ConnectionPool.class: constructor(): connection creating error. less than 4"); //todo
        }
        freeConnectionsQueue = new LinkedBlockingQueue<>(connectionPoolSize);
        busyConnectionsQueue = new LinkedBlockingQueue<>(connectionPoolSize);
        initialiseConnectionQueue(freeConnectionsQueue, connectionPoolSize);
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (!isInitialise.get()) {
            initializationLock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInitialise.set(true);
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
        Connection connection = null;
        try {
            connection = freeConnectionsQueue.take();
            busyConnectionsQueue.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //todo
        }
        return connection;
    }

    boolean relieveConnection(ProxyConnection connection) {
        if (connection != null && connection.getClass() == ProxyConnection.class) {
            busyConnectionsQueue.remove(connection);
            try {
                freeConnectionsQueue.put(connection);
                return true;
            } catch (InterruptedException cause) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    public void destroyConnectionPool() throws ConnectionPoolException {
        if (isConnectionPoolInService.get()) {
            serviceLock.lock();
        }
        if (freeConnectionsQueue.size() == connectionPoolSize) {
            closeClearConnectionsInQueue(freeConnectionsQueue);
            deregisterDriver();
            instance = null;
        } else {
            throw new ConnectionPoolException("ConnectionPool.class: destroyConnectionPool(): not all connections is free");//todo
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (instance != null) {
            throw new CloneNotSupportedException("Cloning not allowed. Cannot create second instance.");
        }
        return super.clone();
    }

    private void closeClearConnectionsInQueue(BlockingQueue<Connection> connectionQueue) {
        if (connectionQueue != null) {
            int queueSize = connectionQueue.size();
            for (int connectionCount = 0; connectionCount < queueSize; ++connectionCount) {
                try {
                    ProxyConnection proxyConnection = (ProxyConnection) freeConnectionsQueue.take();
                    proxyConnection.reallyClose();
                } catch (InterruptedException | SQLException e) {
                    logger.log(Level.WARN, "Error while closing connection.");
                }
            }
        }
    }

    private void deregisterDriver() throws ConnectionPoolException {
        Iterator<Driver> driverIterator = DriverManager.getDrivers().asIterator();
        Driver driverContainer;
        while (driverIterator.hasNext()) {
            driverContainer = driverIterator.next();
            try {
                DriverManager.deregisterDriver(driverContainer);
            } catch (SQLException e) {
                throw new ConnectionPoolException("ConnectionPool.class: deregisterDriver(): DriverManager.deregisterDriver() error", e);
            }
        }
    }


    private BlockingQueue<Connection> initialiseConnectionQueue(BlockingQueue<Connection> currentConnectionsQueue, int size) throws ConnectionPoolException {
        if (currentConnectionsQueue == null) {
            currentConnectionsQueue = new LinkedBlockingQueue<>(size);
        }
        MySqlConnectionFactory mySqlConnectionFactory;
        mySqlConnectionFactory = MySqlConnectionFactory.getInstance();
        Connection connection;
        int initialisationCounter = 0;
        while (initialisationCounter < DEFAULT_MAX_NUMBER_OF_INITIALIZATIONS_AT_TIME) {
            int currentMaxSize = size - currentConnectionsQueue.size();
            for (int currentConnectionCount = 0; currentConnectionCount < currentMaxSize; ++currentConnectionCount) {
                connection = null;
                try {
                    connection = mySqlConnectionFactory.getProxyConnection();
                } catch (ConnectionPoolException cause) {
                    logger.log(Level.WARN, "Connection number " + (currentConnectionCount + 1) + " is not created.");
                    --currentMaxSize;
                }
                if (connection != null) {
                    freeConnectionsQueue.add(connection);
                }
            }
            if (currentConnectionsQueue.size() == size) {
                break;
            }
        }
        if (currentConnectionsQueue.size() < DEFAULT_MIN_EFFECTIVE_CONNECTION_POOL_SIZE) {
            String message = "Unable to initialize connection pool. The queue has the wrong number of connections.";
            logger.log(Level.ERROR, message);
            throw new RuntimeException(message);
        }
        return currentConnectionsQueue;
    }

    //todo sbConnectionCount  почему стринг билдер
    private void initializeConnectionPoolSizeParameters() throws ConnectionPoolException { //todo возможно потимизировать код без sb или вынести дублирующийся код в метод
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

        if (connectionPoolSize < minConnectionPoolSize || minConnectionPoolSize < DEFAULT_MIN_EFFECTIVE_CONNECTION_POOL_SIZE || spareConnectionPoolSize < DEFAULT_MIN_EFFECTIVE_CONNECTION_POOL_SIZE) {
            logger.log(Level.WARN, "Received incorrect connection pool sizes from the config.properties file. Default values used.");
            connectionPoolSize = DEFAULT_CONNECTION_POOL_SIZE;
            minConnectionPoolSize = DEFAULT_MIN_CONNECTION_POOL_SIZE;
            spareConnectionPoolSize = DEFAULT_SPARE_CONNECTION_POOL_SIZE;
        }
        SecurityDataCleaner.cleanStringBuilders(sbConnectionCount, sbMinConnectionCount, sbSpareConnectionCount);
    }

    private void clearMainConnectionQueue() throws ConnectionPoolException {
        BlockingQueue<Connection> spareFreeConnectionsQueue = new LinkedBlockingQueue<>(spareConnectionPoolSize);
        BlockingQueue<Connection> spareBusyConnectionsQueue = new LinkedBlockingQueue<>(spareConnectionPoolSize);
        initializeConnectionPoolSizeParameters();
        initialiseConnectionQueue(spareFreeConnectionsQueue, spareConnectionPoolSize);
        spareBusyConnectionsQueue = new LinkedBlockingQueue<Connection>(spareConnectionPoolSize);
        BlockingQueue<Connection> freeQueueContainer = freeConnectionsQueue;
        BlockingQueue<Connection> busyQueueContainer = busyConnectionsQueue;
        int currentAttemptWaitBeReleasedConnections = ATTEMPT_WAIT_BE_RELEASED_CONNECTIONS;
        isConnectionPoolInService.set(true);
        while (busyConnectionsQueue.size() > 0 && currentAttemptWaitBeReleasedConnections > 0) {
            try {
                --currentAttemptWaitBeReleasedConnections;
                Thread.sleep(SLEEP_TIME_WAIT_BE_RELEASED_CONNECTIONS_IN_ONE_ATTEMPT); // todo ждем освобождения коннекшенов(ожидаем окончания сложных операций) ,можно ли так
            } catch (InterruptedException cause) {
                logger.log(Level.WARN, "sleep() get InterruptedException.");
            }
        }
        freeConnectionsQueue = spareFreeConnectionsQueue;
        busyConnectionsQueue = spareBusyConnectionsQueue;
        isConnectionPoolInService.set(false);
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
        //todo  недоделано, возможно есть ошибки.
        //todo  возвращение пересозданного пула
    }

}
