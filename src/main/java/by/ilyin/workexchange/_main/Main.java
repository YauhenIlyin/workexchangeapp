package by.ilyin.workexchange._main;

import by.ilyin.workexchange.exception.ConnectionPoolException;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;

public class Main {
    private final static String EMAIL_REGEX = "^[^@]+\\@[^@]+\\.[a-zA-Z]{2,}$";

    //"work_exchange_app_system_login",
    //"work_exchange_app_system_pass");
    public static void main(String args[]) throws SQLException, ConnectionPoolException {
        /*
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/work_exchange_db",
                "root",
                "rootpass");
        CallableStatement callableStatement = connection.prepareCall("{call testp(?,?)}");
        callableStatement.setLong(1, 2l);
        callableStatement.registerOutParameter(2, Types.BOOLEAN);
        callableStatement.execute();
        boolean result = callableStatement.getBoolean(2); //todo 1 или 2
        System.out.println(result);
        callableStatement.close();
        connection.close();
         */
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/work_exchange_db",
                "root",
                "rootpass");
        CallableStatement callableStatement = connection.prepareCall("{call isFreeAccountLoginProcedure(?,?)}");
        callableStatement.setString(1, "fsdfsdfdfsd");
        callableStatement.registerOutParameter(2, Types.BOOLEAN);
        callableStatement.execute();
        boolean result = callableStatement.getBoolean(2); //todo 1 или 2
        System.out.println(result);
        callableStatement.close();
        connection.close();
    }
}
