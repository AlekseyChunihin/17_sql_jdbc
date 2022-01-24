package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRoleDao extends GenericJdbcDao implements RoleDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcRoleDao.class);

    public void createTableRole() {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("Create table IF NOT EXISTS role_table" +
                    "(role_id int PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL" +
                    ")");
            statement.close();
        } catch (SQLException e) {
            log.error("failed to create table role: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Role role) {
        Connection connection = getConnection();
        String insertQuery = "INSERT INTO role_table (name) VALUES(?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, role.getName());
            log.info("inserting new role with name: {}", role.getName());
            int rows = preparedStatement.executeUpdate();
            System.out.println("rows added = " + rows);
            connection.commit();
        } catch (SQLException e) {
            log.error("failed to insert role: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Role role) {
        Connection connection = getConnection();
        String updateQuery = "UPDATE role_table SET name = ? WHERE role_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setLong(2, role.getId());
            log.info("updating role with name : {}", role.getName());
            int rows = preparedStatement.executeUpdate();
            connection.commit();
            log.info("rows were updated: {}", rows);
        } catch (SQLException e) {
            log.error("failed to update role: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Role role) {
        Connection connection = getConnection();
        String deleteQuery = "DELETE from role_table WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, role.getName());
            preparedStatement.executeUpdate();
            log.info("role was deleted with name: {}", role.getName());
            connection.commit();
        } catch (SQLException e) {
            log.error("failed to delete role with name : {}, {}", role.getName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Role findByName(String name) {
        Connection connection = getConnection();
        Role role = new Role();
        String findRoleByName = "Select * From role_table where name=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(findRoleByName)) {
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                role.setId(result.getLong("role_id"));
                role.setName(result.getString("name"));
            }
            result.close();
        } catch (SQLException e) {
            log.info("could not find role by name {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return role;
    }

    public List<Role> findAll() {
        Connection connection = getConnection();
        List<Role> roles = new ArrayList<>();
        String getAllRoles = "Select * From role_table";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getAllRoles)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Role role = new Role();
                role.setId(result.getLong("role_id"));
                role.setName(result.getString("name"));
                roles.add(role);
            }
            result.close();
        } catch (SQLException e) {
            log.info("could not find role {} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return roles;
    }
}
