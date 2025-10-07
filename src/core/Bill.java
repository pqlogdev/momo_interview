package core;

import java.time.LocalDate;

/**
 * Represents a bill in the system
 */
public class Bill {
    private int id;
    private BillType type;
    private long amount;
    private LocalDate dueDate;
    private BillState state;
    private String provider;

    public Bill(int id, BillType type, long amount, LocalDate dueDate, String provider) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = BillState.NOT_PAID;
        this.provider = provider;
    }

    public int getId() {
        return id;
    }

    public BillType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BillState getState() {
        return state;
    }

    public String getProvider() {
        return provider;
    }

    public void setState(BillState state) {
        this.state = state;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return id + ". " + type + " " + amount + " " + dueDate + " " + state + " " + provider;
    }
}