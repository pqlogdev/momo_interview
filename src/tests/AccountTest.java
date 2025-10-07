package tests;

import core.Account;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    @Test
    void testAddFunds() {
        Account account = new Account();
        account.cashIn(100000);
        assertEquals(100000, account.getBalance());
    }

    @Test
    void testInsufficientFunds() {
        Account account = new Account();
        account.cashIn(50000);
        assertFalse(account.canPay(100000));
    }
    
    @Test
    void testDeductFunds() {
        Account account = new Account();
        account.cashIn(100000);
        boolean result = account.deduct(50000);
        assertTrue(result);
        assertEquals(50000, account.getBalance());
    }
    
    @Test
    void testDeductInsufficientFunds() {
        Account account = new Account();
        account.cashIn(50000);
        boolean result = account.deduct(100000);
        assertFalse(result);
        assertEquals(50000, account.getBalance());
    }
    
    @Test
    void testNegativeAmount() {
        Account account = new Account();
        assertThrows(IllegalArgumentException.class, () -> account.cashIn(-1000));
        assertThrows(IllegalArgumentException.class, () -> account.deduct(-1000));
    }
}