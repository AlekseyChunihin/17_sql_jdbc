package com.nixsolutions.ppp;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nixsolutions.ppp.dao.JdbcRoleDao;
import com.nixsolutions.ppp.entity.Role;
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
public class JdbcRoleDaoTest {

    private final JdbcRoleDao jdbcRoleDao = new JdbcRoleDao();

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(jdbcRoleDao.getConnection());

    @Test
    @DataSet(value = "datasets/roles.yml", executeScriptsBefore = "create_tables.sql", executeScriptsAfter = "drop_tables.sql")
    @ExpectedDataSet(value = "datasets/roles.yml")
    public void testFindAll() {
        List<Role> roles = jdbcRoleDao.findAll();
        assertNotNull(roles);
        assertEquals(2, roles.size());
    }

    @Test
    @DataSet(value = "datasets/roles.yml", executeScriptsBefore = "create_tables.sql", executeScriptsAfter = "drop_tables.sql")
    @ExpectedDataSet(value = "datasets/roles.yml")
    public void testFindByName() {
        //Role roleTest = new Role("role1");
        Role roleTest = jdbcRoleDao.findByName("role1");
        assertNotNull(roleTest);
        //assertEquals(3, roles.size());
    }



}
