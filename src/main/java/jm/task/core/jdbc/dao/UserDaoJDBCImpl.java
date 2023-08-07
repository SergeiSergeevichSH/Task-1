package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String tableName = "users";
    private Util util = new Util();


    public UserDaoJDBCImpl() {
    }
    //создание таблицы
    public void createUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS " + tableName +
                    "(`id` INT NOT NULL AUTO_INCREMENT, " +
                    " `name` VARCHAR(255) NOT NULL, " +
                    " `lastName` VARCHAR(255) NOT NULL, " +
                    " `age` INT NOT NULL," +
                    "PRIMARY KEY (`id`)" +
                    ") DEFAULT CHARACTER SET = utf8");
        } catch (SQLException e) {
            System.out.println("Не удалось создать" + tableName + " таблицу: " + e);
        }
    }
    //удаление таблицы
    public void dropUsersTable() {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement("DROP TABLE IF EXISTS " + tableName)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось удалить" + tableName + " таблицу: " + e);
        }

    }
    //сохранение пользователя в таблицу
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO " + tableName + "(name, lastname, age) VALUES (?, ?, ?)");
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
//            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Не удалось сохранить " + name + "в" + tableName + "таблицу: " + e);
        }
    }
    //сохранение пользователя в таблицу
    public void removeUserById(long id) {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM " + tableName + " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось удалить пользователя с id = " + id + e);
        }

    }
    //загрузка пользователей из таблицы
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM " + tableName)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Не удалось загрузить данные из таблицы "
                    + tableName  + e);
        }
        return users;
    }
    //загрузка пользователей из таблицы
    public void cleanUsersTable() {
        try (Connection con = util.getConnection(); Statement state = con.createStatement()) {
            state.execute("TRUNCATE TABLE " + tableName);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить " + tableName + " таблицу: " + e);
        }
    }
}
