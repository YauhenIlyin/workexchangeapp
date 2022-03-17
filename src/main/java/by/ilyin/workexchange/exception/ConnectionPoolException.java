package by.ilyin.workexchange.exception;

public class ConnectionPoolException extends Exception {

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Throwable throwable) {
        super(throwable);
    }

    public ConnectionPoolException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
