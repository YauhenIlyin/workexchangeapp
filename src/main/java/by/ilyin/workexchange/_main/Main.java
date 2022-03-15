package by.ilyin.workexchange._main;

import by.ilyin.workexchange.util.email.EmailManager;

import java.sql.*;

public class Main {

    public static void main(String args[]) throws SQLException {

        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/work_exchange_db", "root", "rootpass");

        Statement statement = connection.createStatement();
        //ResultSet resultSet = statement.executeQuery("SELECT order_status_description FROM order_status WHERE ");
        ResultSet resultSet = statement.executeQuery("""
                INSERT INTO users(first_name, last_name, registration_date,
                                last_activity_date, e_mail, mobile_number, login, user_role_id, account_status_id)
                        VALUES (
                                ?,?,?,?,?,?,?,
                        (SELECT user_role.id FROM user_role WHERE user_role.role_description = ?),
                        (SELECT account_status.account_status_id FROM account_status WHERE account_status_description = ?)
                            );
                """
        );
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }


        connection.close();
        /*
        String query =
                """
                        SELECT users.id, users.first_name, users.last_name, users.registration_date, users.last_activity_date, users.e_mail, users.mobile_number, users.login, users.user_password, user_role.role_description, account_status.account_status_description
                        FROM work_exchange_db.users 
                        JOIN user_role ON users.user_role_id=user_role.id
                        JOIN account_status ON users.account_status_id=account_status.id;
                        """;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        int size = resultSet.getFetchSize();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        System.out.println("columnCount :" + columnCount);
        for(int i = 1; i < columnCount; i++) {
            System.out.print(metaData.getColumnName(i));
            System.out.print(" ");
        }
        System.out.println();
        while (resultSet.next()) {
            for (int i = 1; i < columnCount; ++i) {
                System.out.print(resultSet.getString(i));
                System.out.print(" ");
            }
            System.out.println();
        }
        connection.close();

        EmailManager emailManager = new EmailManager();
        emailManager.sendMail("prog.jekylin@gmail.com");
        */
    }

}
