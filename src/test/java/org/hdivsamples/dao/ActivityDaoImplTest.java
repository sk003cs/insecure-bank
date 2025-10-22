package org.hdivsamples.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hdivsamples.bean.Transaction;
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
        String accountNumber = "1";
        List<Transaction> transactions = activityDao.findTransactionsByCashAccountNumber(accountNumber);
        
        // Verify that transactions are returned
        assertNotNull("Transactions should not be null", transactions);
        
        // Test with a different valid account number
        accountNumber = "2";
        transactions = activityDao.findTransactionsByCashAccountNumber(accountNumber);
        assertNotNull("Transactions should not be null", transactions);
    }
}

// Made with Bob
