package com.nixsolutions.ppp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nixsolutions.ppp.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao{

    private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);

    private final Connection connection;

    public JdbcUserDao(Connection connection) {
        this.connection = connection;
    }

    //+
    public void createTableUser() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE users_table ( " +
                    "   id BIGINT Primary Key," +
                    "   login VARCHAR(50) NOT NULL," +
                    "   password VARCHAR(50) NOT NULL," +
                    "   email VARCHAR(50) NOT NULL," +
                    "   firstName VARCHAR(50) NOT NULL," +
                    "   lastName VARCHAR(50) NOT NULL," +
                    "   birthday DATE)");
            statement.close();
        } catch (SQLException e) {
            log.error("failed to create : {}", e.getMessage());
        }
    }

    //+
    public void create(User user) {
        try {
            connection.setAutoCommit(false);
            String insertQuery = "INSERT INTO users_table (id, login, password, email, firstname, lastname, birthday) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getFirstName());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setDate(7, user.getBirthday());
            log.info("inserting new user with: name : {} and email: {}", user.getFirstName() + user.getLastName(), user.getEmail());
            int rows = preparedStatement.executeUpdate();
            System.out.println("rows added = " + rows);
            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("failed to create : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        String updateQuery = "UPDATE users_table SET login = ?, password = ?, email = ?, firstname = ?, lastname = ?, birthday = ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setDate(6, user.getBirthday());
            preparedStatement.setLong(7, user.getId());
            log.info("updating user with name and email: name : {}, email {}", user.getFirstName() + " " + user.getLastName(), user.getEmail());
            int rows = preparedStatement.executeUpdate();
            connection.commit();
            log.info("rows were updated: {}", rows);
        } catch (SQLException e) {
            log.error("failed to update : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //+
    public void remove(User user) {
        String deleteQuery = "DELETE from users_table WHERE email = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.executeUpdate();
            log.info("user was deleted with name: {}", user.getFirstName());
            connection.commit();
        } catch (SQLException e) {
            log.error("failed to delete user with email : {}, {}", user.getEmail(), e.getMessage());
        }
    }

    //+
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getAllRoles = "Select * From users_table";
            ResultSet result = statement.executeQuery(getAllRoles);
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirstName(result.getString("firstname"));
                user.setLastName(result.getString("lastname"));
                user.setBirthday(result.getDate("birthday"));
                users.add(user);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            log.info("could not find role {} ", e.getMessage());
        }
        return users;
    }
    //+
    public User findById(Long id) {
        User user = new User();
        try {
            Statement statement = connection.createStatement();
            String findUserByLogin = "Select * From users_table where id = '" + id + "'";
            ResultSet result = statement.executeQuery(findUserByLogin);
            while (result.next()) {
                user.setId(result.getLong("id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirstName(result.getString("firstname"));
                user.setLastName(result.getString("lastname"));
                user.setBirthday(result.getDate("birthday"));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            log.info("could not find user with id {} ", e.getMessage());
        }
        return user;
    }

    //+
    public User findByLogin(String login) {
        User user = new User();
        try {
            Statement statement = connection.createStatement();
            String findUserByLogin = "Select * From users_table where login = '" + login + "'";
            ResultSet result = statement.executeQuery(findUserByLogin);
            while (result.next()) {
                user.setId(result.getLong("id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirstName(result.getString("firstname"));
                user.setLastName(result.getString("lastname"));
                user.setBirthday(result.getDate("birthday"));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            log.info("could not find user by login {} ", e.getMessage());
        }
        return user;
    }
    //+
    public User findByEmail(String email) {
        User user = new User();
        try {
            Statement statement = connection.createStatement();
            String findUserByLogin = "Select * From users_table where email = '" + email + "'";
            ResultSet result = statement.executeQuery(findUserByLogin);
            while (result.next()) {
                user.setId(result.getLong("id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirstName(result.getString("firstname"));
                user.setLastName(result.getString("lastname"));
                user.setBirthday(result.getDate("birthday"));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            log.info("could not find user by email {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }
}
