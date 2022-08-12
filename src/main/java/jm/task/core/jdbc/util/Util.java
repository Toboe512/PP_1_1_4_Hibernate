package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.cfg.Configuration;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pp_113?useSSL=false";
    private static final String DB_USER_NAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_HIBERNATE_DRIVER_CLASS = "com.mysql.jdbc.Diver";
    private static final String DB_HIBERNATE_DIALECT = "org.hibernate.dialect.MySQL5Dialect";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
    }

    public static Configuration hibernateConfiguration() {
        return new Configuration()
                .setProperty("hibernate.driver_class", DB_HIBERNATE_DRIVER_CLASS)
                .setProperty("hibernate.connection.url", DB_URL)
                .setProperty("hibernate.connection.username", DB_USER_NAME)
                .setProperty("hibernate.connection.password", DB_PASSWORD)
                .setProperty("hibernate.dialect", DB_HIBERNATE_DIALECT)
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.current_session_context_class", "thread")
                .addAnnotatedClass(User.class);
    }
}
