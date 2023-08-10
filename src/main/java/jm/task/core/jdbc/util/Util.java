package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
//    private static final String URL = "jdbc:postgresql://localhost:5432/test_db";
    private static final String URL = "jdbc:mysql://localhost:3306/test_db";
//    private static final String LOGIN = "postgres";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1234";
//    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String AUTOCOMMIT = "false";
    private static Connection connection;
    private static SessionFactory factory = null;

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        }
        catch (SQLException e) {
            System.out.println("Не удалось установить соединение c БД" + e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getFactory() {
        if (factory == null) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                properties.put(Environment.DRIVER, DRIVER);
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, LOGIN);
                properties.put(Environment.PASS, PASSWORD);
//                properties.put(Environment.PASS, PASSWORD);
                // Добавляем параметр для вывода SQL-запросов в лог
//                properties.put(Environment.AUTOCOMMIT, "true");
                Configuration cfg = new Configuration()
                        .setProperties(properties)
                        .addAnnotatedClass(jm.task.core.jdbc.model.User.class);
                factory = cfg.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return factory;
    }
}
