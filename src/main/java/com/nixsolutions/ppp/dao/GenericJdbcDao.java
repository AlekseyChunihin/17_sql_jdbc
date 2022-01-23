package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.entity.User;
import com.nixsolutions.ppp.h2Connector.H2Connector;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public abstract class GenericJdbcDao<E> implements Dao {

    private static final Logger log = LoggerFactory.getLogger(GenericJdbcDao.class);

/*TODO мне нужно создавать в GenericJdbcDao переменную connection?

    private final Connection connection;

    public GenericJdbcDao() {
        this.connection = getConnection();
    }*/
 private final Connection connection = GenericJdbcDao.getConnection();
    public static Connection getConnection() {
        Properties props = loadProperties();
        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");
        log.info("Connecting to {}", url);
        try {
            JdbcConnectionPool cp = JdbcConnectionPool.create(url, user, password);
            Connection connection = cp.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            log.error("Failed to connect to database {}", e.getMessage());
        }
        return null;
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

    public abstract List<E> findAll();

    public abstract E findById(Long id);
}
