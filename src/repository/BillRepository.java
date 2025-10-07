package repository;

import core.Bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository for storing and retrieving bills
 */
public class BillRepository {
    private final Map<Integer, Bill> bills = new HashMap<>();
    private int nextId = 1;

    /**
     * Save a bill to the repository
     */
    public Bill save(Bill bill) {
        bills.put(bill.getId(), bill);
        if (bill.getId() >= nextId) {
            nextId = bill.getId() + 1;
        }
        return bill;
    }

    /**
     * Find a bill by ID
     */
    public Bill findById(int id) {
        return bills.get(id);
    }

    /**
     * Get all bills
     */
    public List<Bill> findAll() {
        return new ArrayList<>(bills.values());
    }

    /**
     * Delete a bill
     */
    public boolean delete(int id) {
        return bills.remove(id) != null;
    }

    /**
     * Get the next available ID
     */
    public int getNextId() {
        return nextId;
    }
}