package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JdbcRoleDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcRoleDao.class);

    private final Connection connection;

    public JdbcRoleDao(Connection connection) {
        this.connection = connection;
    }

    //+
    public void createTableRole() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE roles_table ( " +
                    "   id BIGINT Primary key AUTO_INCREMENT," +
                    "   name VARCHAR(50) NOT NULL)");
            statement.close();
        } catch (SQLException e) {
            log.error("failed to create : {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    //+
    public void create(Role role) {
        try {
            connection.setAutoCommit(false);
            String insertQuery = "INSERT INTO roles_table (id, name) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setLong(1, role.getId());
            preparedStatement.setString(2, role.getName());
            log.info("inserting new role with name: name : {}", role.getName());
            int rows = preparedStatement.executeUpdate();
            System.out.println("rows added = " + rows);
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            log.error("failed to create role: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    //+
    public void update(Role role) {
        String updateQuery = "UPDATE roles_table SET name = ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setLong(2, role.getId());
            log.info("updating role with name : {}", role.getName());
            int rows = preparedStatement.executeUpdate();
            connection.commit();
            log.info("rows were updated: {}", rows);
            preparedStatement.close();
        } catch (SQLException e) {
            log.error("failed to update role: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //+
    public void remove(Role role) {
        String deleteQuery = "DELETE from roles_table WHERE name = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, role.getName());
            preparedStatement.executeUpdate();
            log.info("role was deleted with name: {}", role.getName());
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            log.error("failed to delete role with name : {}, {}", role.getName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //+
    public Role findByName(String name) {
        Role role = new Role();
        try {
            String findRoleByName = "Select * From roles_table where name = '" + name + "'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(findRoleByName);
            while (result.next()) {
                role.setId(result.getLong("id"));
                role.setName(result.getString("name"));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            log.info("could not find role by name {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return role;
    }
}
