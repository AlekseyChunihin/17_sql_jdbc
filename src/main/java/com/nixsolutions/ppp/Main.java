package com.nixsolutions.ppp;

import com.nixsolutions.ppp.dao.JdbcRoleDao;
import com.nixsolutions.ppp.dao.JdbcUserDao;
import com.nixsolutions.ppp.entity.Role;
import com.nixsolutions.ppp.entity.User;
import com.nixsolutions.ppp.util.SqlQueriesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;


public class Main {

    private static final Logger log = LoggerFactory.getLogger(SqlQueriesUtil.class);

    public static void main(String[] args) {
        System.out.println("DDD");
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        JdbcRoleDao jdbcRoleDao = new JdbcRoleDao();
        Role role = new Role("role3");
        String date = "2009-03-31";
        jdbcRoleDao.createTableRole();
        jdbcUserDao.createTableUser();
        Role role1 = new Role("role13");
        jdbcRoleDao.create(role1);
        /*Role roleForUser = jdbcRoleDao.findByName("role3");
        User user = new User("login2", "password2", "email2","firstname2","lastname2", Date.valueOf(date), roleForUser);
        User userFound = jdbcUserDao.findById(1L);
        System.out.println(userFound);
        Role roleFound = jdbcRoleDao.findByName("role3");
        System.out.println(roleFound);*/
    }
}
