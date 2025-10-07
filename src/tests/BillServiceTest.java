package tests;

import core.Bill;
import core.BillService;
import core.BillState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BillRepository;
import utils.DateUtils;
import core.BillType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BillServiceTest {
    private BillService billService;
    private BillRepository billRepository;

    @BeforeEach
    void setUp() {
        billRepository = new BillRepository();
        billService = new BillService(billRepository);
        billService.initializeWithSampleData();
    }

    @Test
    void testGetAllBills() {
        List<Bill> bills = billService.getAllBills();
        assertEquals(3, bills.size());
    }

    @Test
    void testGetBillById() {
        Bill bill = billService.getBillById(1);
        assertNotNull(bill);
        assertEquals(1, bill.getId());
        assertEquals("EVN HCMC", bill.getProvider());
    }

    @Test
    void testGetBillsByProvider() {
        List<Bill> bills = billService.getBillsByProvider("VNPT");
        assertEquals(1, bills.size());
        assertEquals("VNPT", bills.get(0).getProvider());
    }

    @Test
    void testScheduleBill() {
        boolean result = billService.scheduleBill(1, DateUtils.parseDate("28/10/2020"));
        assertTrue(result);
        
        Bill bill = billService.getBillById(1);
        assertEquals(BillState.SCHEDULED, bill.getState());
    }

    @Test
    void testMarkBillAsPaid() {
        boolean result = billService.markBillAsPaid(1);
        assertTrue(result);
        
        Bill bill = billService.getBillById(1);
        assertEquals(BillState.PAID, bill.getState());
    }

    @Test
    void testMarkBillAsUnpaid() {
        billService.markBillAsPaid(1);
        boolean result = billService.markBillAsUnpaid(1);
        assertTrue(result);
        
        Bill bill = billService.getBillById(1);
        assertEquals(BillState.NOT_PAID, bill.getState());
    }

    @Test
    void testCreateBill() {
        Bill bill = billService.createBill(BillType.ELECTRIC, 1000000, DateUtils.parseDate("28/10/2020"), "EVN HCMC");
        assertNotNull(bill);
        assertEquals(4, bill.getId());
        assertEquals(BillState.NOT_PAID, bill.getState());
    }

    @Test
    void testUpdateBill() {
        Bill bill = billService.createBill(BillType.INTERNET, 1000000, DateUtils.parseDate("20/10/2020"), "FPT");
        Bill newBill = billService.updateBill(bill.getId(), BillType.ELECTRIC, 1500000, DateUtils.parseDate("28/10/2020"), BillState.PAID, "EVN HCMC");
        assertNotNull(newBill);
        assertEquals(BillType.ELECTRIC, newBill.getType());
        assertEquals("EVN HCMC", newBill.getProvider());
        assertEquals(1500000, newBill.getAmount());
        assertEquals(BillState.PAID, newBill.getState());
    }

    @Test
    void testDeleteBill() {
        Bill bill = billService.createBill(BillType.INTERNET, 1000000, DateUtils.parseDate("20/10/2020"), "FPT");
        boolean result = billService.deleteBill(bill.getId());
        assertTrue(result);
        
        Bill billbyId = billService.getBillById(bill.getId());
        assertNull(billbyId);
    }
}