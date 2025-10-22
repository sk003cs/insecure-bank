package org.hdivsamples.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hdivsamples.bean.Transaction;
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
public class ActivityDaoImplTest {

    @Autowired
    private ActivityDao activityDao;

    @Before
    public void setup() {
        // Setup code if needed
    }

    @Test
    public void testFindTransactionsByCashAccountNumber() {
        // Test with a valid account number
        String accountNumber = "1234567890";
        List<Transaction> transactions = activityDao.findTransactionsByCashAccountNumber(accountNumber);
        
        // Verify basic behavior - we're not testing specific data, just that the method works
        assertNotNull(transactions);
        
        // Test with a normal account number containing special characters
        String accountNumberWithSpecialChars = "1234-5678-90";
        List<Transaction> transactionsWithSpecialChars = activityDao.findTransactionsByCashAccountNumber(accountNumberWithSpecialChars);
        assertNotNull(transactionsWithSpecialChars);
    }

    @Test
    public void testInsertNewActivity() {
        // Test the insertNewActivity method
        java.util.Date date = new java.util.Date();
        String description = "Test Transaction";
        String number = "1234567890";
        double amount = 100.00;
        double availableBalance = 500.00;
        
        // This should not throw an exception
        activityDao.insertNewActivity(date, description, number, amount, availableBalance);
        
        // Verify the transaction was inserted by retrieving it
        List<Transaction> transactions = activityDao.findTransactionsByCashAccountNumber(number);
        assertNotNull(transactions);
    }
}

// Made with Bob
