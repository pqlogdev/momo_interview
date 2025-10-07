package core;

import repository.BillRepository;
import repository.PaymentRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing payments
 */
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final BillRepository billRepository;
    private final Account account;

    public PaymentService(PaymentRepository paymentRepository, BillRepository billRepository) {
        this.paymentRepository = paymentRepository;
        this.billRepository = billRepository;
        this.account = new Account();
    }

    /**
     * Add funds to account
     */
    public long cashIn(long amount) {
        account.cashIn(amount);
        return account.getBalance();
    }

    /**
     * Get current account balance
     */
    public long getBalance() {
        return account.getBalance();
    }

    /**
     * Pay a specific bill
     */
    public boolean payBill(int billId) {
        Bill bill = billRepository.findById(billId);
        if (bill == null || bill.getState() == BillState.PAID) {
            return false;
        }

        if (!account.canPay(bill.getAmount())) {
            return false;
        }

        account.deduct(bill.getAmount());
        bill.setState(BillState.PAID);
        
        // Create payment record
        Payment payment = new Payment(
            paymentRepository.getNextId(),
            bill.getAmount(),
            LocalDate.now(),
            PaymentState.PROCESSED,
            bill.getId()
        );
        paymentRepository.save(payment);
        
        return true;
    }

    /**
     * Pay multiple bills
     */
    public boolean payMultipleBills(List<Integer> billIds) {
        // Calculate total amount needed
        long totalAmount = 0;
        List<Bill> billsToPay = billIds.stream()
                .map(billRepository::findById)
                .filter(bill -> bill != null && bill.getState() != BillState.PAID)
                .collect(Collectors.toList());
        
        for (Bill bill : billsToPay) {
            totalAmount += bill.getAmount();
        }
        
        // Check if we have enough funds
        if (!account.canPay(totalAmount)) {
            return false;
        }
        
        // Pay bills in order of due date (earliest first)
        billsToPay.sort(Comparator.comparing(Bill::getDueDate));
        
        for (Bill bill : billsToPay) {
            account.deduct(bill.getAmount());
            bill.setState(BillState.PAID);
            
            // Create payment record
            Payment payment = new Payment(
                paymentRepository.getNextId(),
                bill.getAmount(),
                LocalDate.now(),
                PaymentState.PROCESSED,
                bill.getId()
            );
            paymentRepository.save(payment);
        }
        
        return true;
    }

    /**
     * Schedule a payment for a future date
     */
    public boolean schedulePayment(int billId, LocalDate scheduleDate) {
        Bill bill = billRepository.findById(billId);
        if (bill == null || bill.getState() == BillState.PAID) {
            return false;
        }
        
        // Create pending payment
        Payment payment = new Payment(
            paymentRepository.getNextId(),
            bill.getAmount(),
            scheduleDate,
            PaymentState.PENDING,
            bill.getId()
        );
        paymentRepository.save(payment);
        
        // Mark bill as scheduled
        bill.setState(BillState.SCHEDULED);
        
        return true;
    }

    /**
     * Get all payments
     */
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}