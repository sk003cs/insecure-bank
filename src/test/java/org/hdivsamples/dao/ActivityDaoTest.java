package org.hdivsamples.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hdivsamples.bean.Transaction;
import org.hdivsamples.config.CoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CoreConfig.class })
public class ActivityDaoTest {

    @Autowired
    private ActivityDao activityDao;

    @Test
    public void testFindTransactionsByCashAccountNumber() {
        // Test with a valid account number
        String accountNumber = "1";
        List<Transaction> transactions = activityDao.findTransactionsByCashAccountNumber(accountNumber);
        
        // Verify that the result is not null
        assertNotNull("Transactions should not be null", transactions);
        
        // Note: We're not asserting the size or content of the list since it depends on the database state
        // This test just verifies that the method executes without errors
    }

    @Test
    public void testFindTransactionsByCashAccountNumberWithSpecialCharacters() {
        // Test with a string containing special characters that could be used in SQL injection
        String accountNumber = "1' OR '1'='1";
        List<Transaction> transactions = activityDao.findTransactionsByCashAccountNumber(accountNumber);
        
        // Verify that the result is not null
        assertNotNull("Transactions should not be null", transactions);
        
        // In the vulnerable implementation, this would return all transactions
        // After fixing, it should return an empty list since no account has this number
        // But we don't assert that here since we're just establishing baseline behavior
    }
}

// Made with Bob
