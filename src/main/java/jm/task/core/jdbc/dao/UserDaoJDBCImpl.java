package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String CREATE_USER_TABLE = """
            CREATE TABLE IF NOT EXISTS user(id INT  NOT NULL AUTO_INCREMENT,
                                               name VARCHAR(45) NULL,
                                               lastname VARCHAR(45) NULL,
                                               age INT NULL,
                                               PRIMARY KEY (id));
            """;
    private final String DROP_USER_TABLE = """
            DROP TABLE IF EXISTS user;
            """;
    private final String INSERT_USER_TABLE = """
            INSERT INTO user(name, lastname, age)
            VALUES (?, ?, ?);
            """;
    private final String DETETE_USER_TABLE = """
            DELETE FROM user;
            """;

    private final String CLEAR_USER_TABLE = """
            DELETE FROM user 
            WHERE id = ?;
            """;
    private final String GET_ALL_USER_TABLE = """
            SELECT *
            FROM user
            """;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement userTablePreparedStatement = Util.getConnection().createStatement()) {
            userTablePreparedStatement.executeUpdate(CREATE_USER_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement userTablePreparedStatement = Util.getConnection().prepareStatement(DROP_USER_TABLE)) {
            userTablePreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement userTablePreparedStatement = Util.getConnection().prepareStatement(INSERT_USER_TABLE)) {
            userTablePreparedStatement.setString(1, name);
            userTablePreparedStatement.setString(2, lastName);
            userTablePreparedStatement.setLong(3, age);
            userTablePreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement userTablePreparedStatement = Util.getConnection().prepareStatement(CLEAR_USER_TABLE)) {
            userTablePreparedStatement.setLong(1, id);
            userTablePreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (PreparedStatement userTablePreparedStatement = Util.getConnection().prepareStatement(GET_ALL_USER_TABLE)) {
            ResultSet userTableResultSet = userTablePreparedStatement.executeQuery();
            while (userTableResultSet.next()) {
                result.add(new User(userTableResultSet.getLong("id"),
                        userTableResultSet.getString("name"),
                        userTableResultSet.getString("lastname"),
                        (byte) userTableResultSet.getLong("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void cleanUsersTable() {
        try (PreparedStatement userTablePreparedStatement = Util.getConnection().prepareStatement(DETETE_USER_TABLE)) {
            userTablePreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
