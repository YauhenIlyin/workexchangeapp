package by.ilyinyauhen.workexchangeapp._main;

import java.sql.*;

public class Main {

    public static void main(String args[]) throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/work_exchange_db", "root", "rootpass");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT order_status_description FROM order_status");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        connection.close();
    }

}
