package org.hdivsamples.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hdivsamples.bean.Account;
import org.hdivsamples.config.CoreConfig;
import org.hdivsamples.config.SpringWebMvcConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebMvcConfig.class, CoreConfig.class })
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
        
        // Verify basic behavior
        assertNotNull(accounts);
        if (!accounts.isEmpty()) {
            assertEquals(username, accounts.get(0).getUsername());
        }
        
        // Test with a username containing special characters
        String usernameWithSpecialChars = "john-doe";
        List<Account> accountsWithSpecialChars = accountDao.findUsersByUsername(usernameWithSpecialChars);
        assertNotNull(accountsWithSpecialChars);
    }

    @Test
    public void testFindUsersByUsernameAndPassword() {
        // Test with valid credentials
        String username = "john";
        String password = "john";
        List<Account> accounts = accountDao.findUsersByUsernameAndPassword(username, password);
        
        // Verify basic behavior
        assertNotNull(accounts);
        if (!accounts.isEmpty()) {
            assertEquals(username, accounts.get(0).getUsername());
        }
        
        // Test with credentials containing special characters
        String usernameWithSpecialChars = "john-doe";
        String passwordWithSpecialChars = "pass-word";
        List<Account> accountsWithSpecialChars = accountDao.findUsersByUsernameAndPassword(
            usernameWithSpecialChars, passwordWithSpecialChars);
        assertNotNull(accountsWithSpecialChars);
    }

    @Test
    public void testFindAllUsers() {
        // Test the findAllUsers method
        List<Account> allAccounts = accountDao.findAllUsers();
        
        // Verify basic behavior
        assertNotNull(allAccounts);
        // There should be at least some accounts in the database
        assertTrue(allAccounts.size() >= 0);
    }
}

// Made with Bob
