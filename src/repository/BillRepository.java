package repository;

import core.Bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BillRepository {
    private final Map<Integer, Bill> bills = new HashMap<>();
    private int nextId = 1;

    
    public Bill save(Bill bill) {
        bills.put(bill.getId(), bill);
        if (bill.getId() >= nextId) {
            nextId = bill.getId() + 1;
        }
        return bill;
    }

    
    public Bill findById(int id) {
        return bills.get(id);
    }

    
    public List<Bill> findAll() {
        return new ArrayList<>(bills.values());
    }

    public void update(Bill bill) {
        Bill b = bills.get(bill.getId());
        
        b.setType(bill.getType());
        b.setAmount(bill.getAmount());
        b.setDueDate(bill.getDueDate());
        b.setState(bill.getState());
        b.setProvider(bill.getProvider());
        bills.put(bill.getId(), b);
    }

    
    public boolean delete(int id) {
        return bills.remove(id) != null;
    }

    
    public int getNextId() {
        return nextId;
    }
}