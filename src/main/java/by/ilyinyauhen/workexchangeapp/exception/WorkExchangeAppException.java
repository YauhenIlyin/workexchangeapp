package by.ilyinyauhen.workexchangeapp.exception;

public class WorkExchangeAppException extends Exception {

    public WorkExchangeAppException(String message) {
        super(message);
    }

    public WorkExchangeAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkExchangeAppException(Throwable cause) {
        super(cause);
    }

}
