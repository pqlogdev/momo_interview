package repository;

import core.Payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository for storing and retrieving payments
 */
public class PaymentRepository {
    private final Map<Integer, Payment> payments = new HashMap<>();
    private int nextId = 1;

    /**
     * Save a payment to the repository
     */
    public Payment save(Payment payment) {
        payments.put(payment.getId(), payment);
        if (payment.getId() >= nextId) {
            nextId = payment.getId() + 1;
        }
        return payment;
    }

    /**
     * Find a payment by ID
     */
    public Payment findById(int id) {
        return payments.get(id);
    }

    /**
     * Get all payments
     */
    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }

    /**
     * Get the next available ID
     */
    public int getNextId() {
        return nextId;
    }
}