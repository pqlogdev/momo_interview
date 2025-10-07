package core;

import java.time.LocalDate;

/**
 * Represents a payment transaction
 */
public class Payment {
    private int id;
    private long amount;
    private LocalDate paymentDate;
    private PaymentState state;
    private int billId;

    public Payment(int id, long amount, LocalDate paymentDate, PaymentState state, int billId) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.state = state;
        this.billId = billId;
    }

    public int getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public PaymentState getState() {
        return state;
    }

    public int getBillId() {
        return billId;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return id + ". " + amount + " " + paymentDate + " " + state + " " + billId;
    }
}