package com.nixsolutions.ppp;

import com.nixsolutions.ppp.dao.JdbcRoleDao;
import com.nixsolutions.ppp.dao.JdbcUserDao;
import com.nixsolutions.ppp.entity.Role;
import com.nixsolutions.ppp.entity.User;
import com.nixsolutions.ppp.h2Connector.H2Connector;
import com.nixsolutions.ppp.util.SqlQueriesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.util.List;


public class Main {

    private static final Logger log = LoggerFactory.getLogger(SqlQueriesUtil.class);

    public static void main(String[] args) {
        System.out.println("DDD");
        Connection connection = H2Connector.getConnection();
        JdbcUserDao jdbcUserDao = new JdbcUserDao(connection);
        JdbcRoleDao jdbcRoleDao = new JdbcRoleDao(connection);
        String date = "2015-03-31";
        User user = new User(1L, "log", "pass", "em2", "john", "eee", Date.valueOf(date));
        Role role = new Role(2L, "rol2");
//        jdbcUserDao.create(user);
//        jdbcRoleDao.create(role);
        User userForUpdate = new User(1L, "logUpdatedNEW", "passUpdated", "em2Updated", "john", "eee", Date.valueOf(date));
        Role roleForUpdate = new Role(2L, "rol2NewName");
        jdbcUserDao.update(userForUpdate);
        jdbcRoleDao.update(roleForUpdate);
        Role foundRole = jdbcRoleDao.findByName("rol2NewName");
        System.out.println("Name of found role: " + foundRole.getName());
    }
}
