package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.h2Connector.H2Connector;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class GenericJdbcDao {

    private static final Logger log = LoggerFactory.getLogger(GenericJdbcDao.class);

    private final JdbcConnectionPool jdbcConnectionPool;

    public GenericJdbcDao() {
        Properties props = loadProperties();
        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");
        jdbcConnectionPool = JdbcConnectionPool.create(url, user, password);
    }

    public Connection getConnection() {
        try {
            Connection connection = jdbcConnectionPool.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            log.error("Failed to connect to database {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = H2Connector.class.getResourceAsStream("/h2.properties")) {
            props.load(input);
        } catch (IOException | NullPointerException e) {
            log.error("Failed to get properties {}", e.getMessage());
        }
        return props;
    }
}
