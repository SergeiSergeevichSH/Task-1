package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1234";
    //private static final String DRIVER = "MySQL Connector/J";
    private static final String AUTOCOMMIT = "false";
    private static Connection connection;
//    private static SessionFactory factory = null;

    public static Connection getConnection() {
        try {
//            Class.forName(DRIVER);
//            System.out.println("URI = " + URI);
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch ( SQLException e) {
            System.out.println("Не удалось установить соединение c БД" + e);
        }
        return connection;
    }
}
