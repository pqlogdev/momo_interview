package core;


public class Account {
    private long balance;

    public Account() {
        this.balance = 0;
    }

    /**
     * Add funds to the account
     * @param amount Amount to add
     */
    public void cashIn(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.balance += amount;
    }

    /**
     * Deduct funds from the account
     * @param amount Amount to deduct
     * @return true if successful, false if insufficient funds
     */
    public boolean deduct(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        
        if (balance < amount) {
            return false;
        }
        
        balance -= amount;
        return true;
    }

    /**
     * Check if account has sufficient funds
     * @param amount Amount to check
     * @return true if sufficient funds available
     */
    public boolean canPay(long amount) {
        return balance >= amount;
    }

    /**
     * Get current account balance
     * @return Current balance
     */
    public long getBalance() {
        return balance;
    }
}