package repository;

import core.Payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PaymentRepository {
    private final Map<Integer, Payment> payments = new HashMap<>();
    private int nextId = 1;

    
    public Payment save(Payment payment) {
        payments.put(payment.getId(), payment);
        if (payment.getId() >= nextId) {
            nextId = payment.getId() + 1;
        }
        return payment;
    }

    
    public Payment findById(int id) {
        return payments.get(id);
    }

    
    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }

    public Payment updateById(int id, Payment payment) {
        Payment p = payments.get(id);
        if (p == null) {
            System.out.println("No payments found");
            return null;
        }
        p.setAmount(payment.getAmount());
        p.setPaymentDate(payment.getPaymentDate());
        p.setState(payment.getState());
        p.setBillId(payment.getBillId());
        payments.put(id, p);
        return p;
    }

    
    public boolean delete(int id) {
        return payments.remove(id) != null;
    }

    
    public int getNextId() {
        return nextId;
    }
}