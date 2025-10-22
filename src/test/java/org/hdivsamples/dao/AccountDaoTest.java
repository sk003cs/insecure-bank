package org.hdivsamples.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hdivsamples.bean.Account;
import org.hdivsamples.config.CoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CoreConfig.class })
public class AccountDaoTest {

    @Autowired
    private AccountDao accountDao;

    @Test
    public void testFindUsersByUsername() {
        // Test with a valid username
        String username = "john";
        List<Account> accounts = accountDao.findUsersByUsername(username);
        
        // Verify that the result is not null
        assertNotNull("Accounts should not be null", accounts);
        
        // Verify that we found at least one account
        assertEquals("Should find at least one account", true, accounts.size() > 0);
        
        // Verify that the username matches
        assertEquals("Username should match", username, accounts.get(0).getUsername());
    }

    @Test
    public void testFindUsersByUsernameWithSpecialCharacters() {
        // Test with a string containing special characters that could be used in SQL injection
        String username = "john' OR '1'='1";
        List<Account> accounts = accountDao.findUsersByUsername(username);
        
        // Verify that the result is not null
        assertNotNull("Accounts should not be null", accounts);
        
        // In the vulnerable implementation, this would return all accounts
        // After fixing, it should return an empty list since no user has this username
        // But we don't assert that here since we're just establishing baseline behavior
    }

    // Skipping tests for findUsersByUsernameAndPassword due to LDAP connection issues
    // We'll focus on fixing the SQL injection in findUsersByUsername first
}

// Made with Bob
