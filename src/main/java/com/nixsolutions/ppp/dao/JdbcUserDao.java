package com.nixsolutions.ppp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nixsolutions.ppp.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao extends GenericJdbcDao implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);

    //+
    public void createTableUser() {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user_table (" +
                    "  user_id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "  login VARCHAR(100) NOT NULL," +
                    "  password VARCHAR(100) NOT NULL," +
                    "  email VARCHAR(100) NOT NULL," +
                    "  first_name VARCHAR(50) NOT NULL," +
                    "  last_name VARCHAR(50) NOT NULL," +
                    "  birthday DATE," +
                    "  role_id BIGINT," +
                    "  foreign key (role_id) references role_table (role_id)" +
                    ")");
            statement.close();
        } catch (SQLException e) {
            log.error("failed to create table user: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //+
    public void create(User user) {
        Connection connection = getConnection();
        String insertQuery = "INSERT INTO USER_TABLE (login, password, email, first_name, last_name, birthday, role_id) VALUES(?,?,?,?,?,?,?)";
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                connection.setAutoCommit(false);
                //preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getFirstName());
                preparedStatement.setString(5, user.getLastName());
                preparedStatement.setDate(6, user.getBirthday());
                preparedStatement.setLong(7, user.getRole().getId());
                log.info("inserting new user with: name : {} and email: {}", user.getFirstName() + user.getLastName(), user.getEmail());
                int rows = preparedStatement.executeUpdate();
                System.out.println("rows added = " + rows);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("failed to insert user : {}", e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("failed to update : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        Connection connection = getConnection();
        String updateQuery = "UPDATE user_table SET login = ?, password = ?, email = ?, first_name = ?, last_name = ?, birthday = ? WHERE user_id = ?";
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                connection.setAutoCommit(false);
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
                connection.rollback();
                log.error("failed to update : {}", e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("failed to update : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //+
    public void remove(User user) {
        Connection connection = getConnection();
        String deleteQuery = "DELETE from user_table WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.executeUpdate();
            log.info("user was deleted with name: {}", user.getFirstName());
            connection.commit();
        } catch (SQLException e) {
            log.error("failed to delete user with email : {}, {}", user.getEmail(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //+
    public List<User> findAll() {
        Connection connection = getConnection();
        List<User> users = new ArrayList<>();
        String getAllRoles = "Select * From user_table";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getAllRoles)) {
            ResultSet result = preparedStatement.executeQuery(getAllRoles);
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
        } catch (SQLException e) {
            log.info("could not find role {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }

    //+
    public User findById(Long id) {
        Connection connection = getConnection();
        User user = new User();
        String findUserByLogin = "Select * From user_table where user_id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(findUserByLogin)) {
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                user.setId(result.getLong("user_id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setBirthday(result.getDate("birthday"));
            }
            result.close();
        } catch (SQLException e) {
            log.info("could not find user with id {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }

    //+
    public User findByLogin(String login) {
        Connection connection = getConnection();
        User user = new User();
        String findUserByLogin = "Select * From user_table where login =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(findUserByLogin)) {
            preparedStatement.setString(1, login);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                user.setId(result.getLong("user_id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setBirthday(result.getDate("birthday"));
            }
            result.close();
        } catch (SQLException e) {
            log.info("could not find user by login {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }

    //+
    public User findByEmail(String email) {
        Connection connection = getConnection();
        User user = new User();
        String findUserByEmail = "Select * From user_table where email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(findUserByEmail)) {
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                user.setId(result.getLong("user_id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setBirthday(result.getDate("birthday"));
            }
            result.close();
        } catch (SQLException e) {
            log.info("could not find user by email {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }
}
