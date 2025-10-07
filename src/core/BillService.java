package core;

import repository.BillRepository;
// import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class BillService {
    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    
    public Bill getBillById(int id) {
        return billRepository.findById(id);
    }

    
    public List<Bill> getBillsByProvider(String providerName) {
        return billRepository.findAll().stream()
                .filter(bill -> bill.getProvider().contains(providerName))
                .collect(Collectors.toList());
    }

    
    public List<Bill> getUnpaidBills() {
        return billRepository.findAll().stream()
                .filter(bill -> bill.getState() == BillState.NOT_PAID)
                .collect(Collectors.toList());
    }

    public boolean scheduleBill(int billId, LocalDate scheduleDate) {
        Bill bill = billRepository.findById(billId);
        if (bill == null || bill.getState() != BillState.NOT_PAID) {
            return false;
        }
        
        bill.setState(BillState.SCHEDULED);
        return true;
    }
    
    public void initializeWithSampleData() {
        // Create sample bills for testing
        createBill(BillType.ELECTRIC, 200000, LocalDate.now().plusDays(10), "EVN HCMC");
        createBill(BillType.WATER, 175000, LocalDate.now().plusDays(5), "SAWACO");
        createBill(BillType.INTERNET, 350000, LocalDate.now().plusDays(15), "VNPT");
    }

    
    public boolean markBillAsPaid(int billId) {
        Bill bill = billRepository.findById(billId);
        if (bill == null) {
            return false;
        }
        
        bill.setState(BillState.PAID);
        return true;
    }

    public Bill createBill(BillType type, long amount, LocalDate dueDate, String provider) {
        int id = billRepository.getNextId();
        Bill bill = new Bill(id, type, amount, dueDate, BillState.NOT_PAID, provider);
        billRepository.save(bill);
        return bill;
    }

    public Bill updateBill(int billId, BillType type, long amount, LocalDate dueDate, BillState state, String provider) {
        Bill bill = billRepository.findById(billId);
        if (bill == null) {
            return null;
        }
        
        bill.setType(type);
        bill.setAmount(amount);
        bill.setDueDate(dueDate);
        bill.setState(state);
        bill.setProvider(provider);
        billRepository.update(bill);
        return bill;
    }

    public boolean deleteBill(int billId) {
        return billRepository.delete(billId);
    }

    
    // public void initializeWithSampleData() {
    //     billRepository.save(new Bill(1, BillType.ELECTRIC, 200000, 
    //             DateUtils.parseDate("25/10/2020"), "EVN HCMC"));
    //     billRepository.save(new Bill(2, BillType.WATER, 175000, 
    //             DateUtils.parseDate("30/10/2020"), "SAVACO HCMC"));
    //     billRepository.save(new Bill(3, BillType.INTERNET, 800000, 
    //             DateUtils.parseDate("30/11/2020"), "VNPT"));
    // }
}