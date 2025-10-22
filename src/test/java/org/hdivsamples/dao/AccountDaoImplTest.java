package org.hdivsamples.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hdivsamples.bean.Account;
import org.hdivsamples.config.CoreConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { CoreConfig.class })
public class AccountDaoImplTest {

    @Autowired
    private AccountDao accountDao;

    @Before
    public void setup() {
        // Setup code if needed
    }

    @Test
    public void testFindUsersByUsername() {
        // Test with a valid username
        String username = "john";
        List<Account> accounts = accountDao.findUsersByUsername(username);
        
        // Verify that accounts are returned
        assertNotNull("Accounts should not be null", accounts);
        assertTrue("At least one account should be returned", accounts.size() > 0);
        assertEquals("Username should match", username, accounts.get(0).getUsername());
    }

    // Skipping this test as it requires an LDAP server
    // @Test
    // public void testFindUsersByUsernameAndPassword() {
    //     // Test with valid credentials
    //     String username = "john";
    //     String password = "john";
    //     List<Account> accounts = accountDao.findUsersByUsernameAndPassword(username, password);
    //
    //     // Verify that accounts are returned
    //     assertNotNull("Accounts should not be null", accounts);
    //     assertTrue("At least one account should be returned", accounts.size() > 0);
    //     assertEquals("Username should match", username, accounts.get(0).getUsername());
    // }

    @Test
    public void testFindAllUsers() {
        List<Account> accounts = accountDao.findAllUsers();
        
        // Verify that accounts are returned
        assertNotNull("Accounts should not be null", accounts);
        assertTrue("At least one account should be returned", accounts.size() > 0);
    }
}

// Made with Bob
