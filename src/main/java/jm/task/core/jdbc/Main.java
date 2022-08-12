package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService testUser = new UserServiceImpl();
        testUser.createUsersTable();
        testUser.saveUser("Алексей", "Малинин", (byte) 25);
        testUser.saveUser("Георгий", "Брынза", (byte) 34);
        testUser.saveUser("Василий", "Пупкин", (byte) 47);
        testUser.saveUser("Николай", "Николаев", (byte) 22);
        List<User> allUsers = testUser.getAllUsers();
        allUsers.forEach(System.out::println);
        testUser.cleanUsersTable();
        testUser.dropUsersTable();
    }
}
