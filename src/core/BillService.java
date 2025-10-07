package core;

import repository.BillRepository;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing bills
 */
public class BillService {
    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    /**
     * Get all bills
     */
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    /**
     * Get bill by ID
     */
    public Bill getBillById(int id) {
        return billRepository.findById(id);
    }

    /**
     * Get bills by provider name
     */
    public List<Bill> getBillsByProvider(String providerName) {
        return billRepository.findAll().stream()
                .filter(bill -> bill.getProvider().contains(providerName))
                .collect(Collectors.toList());
    }

    /**
     * Get bills that are not paid
     */
    public List<Bill> getUnpaidBills() {
        return billRepository.findAll().stream()
                .filter(bill -> bill.getState() == BillState.NOT_PAID)
                .collect(Collectors.toList());
    }

    /**
     * Schedule a bill payment for a future date
     */
    public boolean scheduleBill(int billId, LocalDate scheduleDate) {
        Bill bill = billRepository.findById(billId);
        if (bill == null || bill.getState() != BillState.NOT_PAID) {
            return false;
        }
        
        bill.setState(BillState.SCHEDULED);
        return true;
    }

    /**
     * Mark a bill as paid
     */
    public boolean markBillAsPaid(int billId) {
        Bill bill = billRepository.findById(billId);
        if (bill == null) {
            return false;
        }
        
        bill.setState(BillState.PAID);
        return true;
    }

    /**
     * Initialize with sample data as per the assignment
     */
    public void initializeWithSampleData() {
        billRepository.save(new Bill(1, BillType.ELECTRIC, 200000, 
                DateUtils.parseDate("25/10/2020"), "EVN HCMC"));
        billRepository.save(new Bill(2, BillType.WATER, 175000, 
                DateUtils.parseDate("30/10/2020"), "SAVACO HCMC"));
        billRepository.save(new Bill(3, BillType.INTERNET, 800000, 
                DateUtils.parseDate("30/11/2020"), "VNPT"));
    }
}