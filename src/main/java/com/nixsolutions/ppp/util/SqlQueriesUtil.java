package com.nixsolutions.ppp.util;

import com.nixsolutions.ppp.entity.Role;
import com.nixsolutions.ppp.h2Connector.H2Connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlQueriesUtil {

    private static final Logger log = LoggerFactory.getLogger(SqlQueriesUtil.class);

    private Connection connection = H2Connector.getConnection();

    public  void createUser() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE users ( \n" +
                    "   id BIGINT NOT NULL, \n" +
                    "   login VARCHAR(50) NOT NULL, \n" +
                    "   password VARCHAR(50) NOT NULL, \n" +
                    "   email VARCHAR(50) NOT NULL, \n" +
                    "   firstName VARCHAR(50) NOT NULL, \n" +
                    "   lastName VARCHAR(50) NOT NULL, \n" +
                    "   birthday DATE \n" +
                    ");");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO ПЕРЕДЕЛАТЬ ПОД ТИО КАК В МЕЙН
    public void createRoles() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE roles ( " +
                    "   id BIGINT NOT NULL," +
                    "   name VARCHAR(50) NOT NULL)");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //working
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getAllRoles = "Select * From roles";
            ResultSet result = statement.executeQuery(getAllRoles);
            while (result.next()) {
                Long id = result.getLong("id");
                String name = result.getString("name");
                Role role = new Role(id, name);
                log.info("found role. Role: id: {}, name: {} ", id, name);
                roles.add(role);
            }
        result.close();
        statement.close();
    } catch (SQLException e) {
        log.info("could not find role {} ", e.getMessage());
    }
        return roles;
        }
    }

    /*public void getUsers(){
        try {
            Statement statement = connection.createStatement();
            String getAllCitiesQuery = "Select * From users";
            ResultSet result = statement.executeQuery("Select * From users");
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Location location = new Location(id, name);
                log.info("found locations. Location: id: {}, name: {} ", id, name);
                cities.add(location);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            log.info("could not find locations {} ", e.getMessage());
        }
    }*/

