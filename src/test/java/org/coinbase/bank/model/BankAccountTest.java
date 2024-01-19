package org.coinbase.bank.model;

import org.coinbase.bank.model.BankAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountTest {
    @Test
    public void testDeposit() {
        User user = new User("dev");
        BankAccount account = new BankAccount("dev", user);
        account.deposit(5);
        assertEquals(account.getBalance(), 5D);
    }

    @Test
    public void testWithdraw() {
        User user = new User("dev");
        BankAccount account = new BankAccount("dev", user);
        account.deposit(5);
        boolean withdrawStatus = account.withdraw(2D);
        assertEquals(withdrawStatus, true);
        assertEquals(account.getBalance(), 3D);

        withdrawStatus = account.withdraw(5D);
        assertEquals(withdrawStatus, false);

    }

    @Test
    public void testGetBalance()
    {
        User user = new User("dev");
        BankAccount account = new BankAccount("dev", user);
        assertEquals(account.getBalance(), 0D);

    }
}
