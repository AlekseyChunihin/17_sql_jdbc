package com.nixsolutions.ppp;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nixsolutions.ppp.dao.JdbcUserDao;
import com.nixsolutions.ppp.entity.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
@DBUnit
public class JdbcUserDaoTest {

    private final JdbcUserDao jdbcUserDao = new JdbcUserDao();

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(jdbcUserDao.getConnection());

    @Test
    @DataSet(value = "datasets/users.yml", executeScriptsBefore = "create_tables.sql", executeScriptsAfter = "drop_tables.sql")
    @ExpectedDataSet(value = "datasets/users.yml")
    public void testFindAll() {
        List<User> users = jdbcUserDao.findAll();
        assertNotNull(users);
        assertEquals(3, users.size());
    }
}
