package tests;

import core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BillRepository;
import repository.PaymentRepository;
import utils.DateUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {
    private PaymentService paymentService;
    private BillService billService;
    private BillRepository billRepository;
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        billRepository = new BillRepository();
        paymentRepository = new PaymentRepository();
        billService = new BillService(billRepository);
        paymentService = new PaymentService(paymentRepository, billRepository);
        
        billService.initializeWithSampleData();
    }

    @Test
    void testCashIn() {
        long balance = paymentService.cashIn(1000000);
        assertEquals(1000000, balance);
        assertEquals(1000000, paymentService.getBalance());
    }

    @Test
    void testPayBill() {
        paymentService.cashIn(1000000);
        boolean result = paymentService.payBill(1);
        
        assertTrue(result);
        assertEquals(800000, paymentService.getBalance());
        assertEquals(BillState.PAID, billService.getBillById(1).getState());
        
        List<Payment> payments = paymentService.getAllPayments();
        assertEquals(1, payments.size());
        assertEquals(PaymentState.PROCESSED, payments.get(0).getState());
    }

    @Test
    void testPayBillInsufficientFunds() {
        paymentService.cashIn(100000);
        boolean result = paymentService.payBill(1);
        
        assertFalse(result);
        assertEquals(100000, paymentService.getBalance());
    }

    @Test
    void testSchedulePayment() {
        boolean result = paymentService.schedulePayment(1, DateUtils.parseDate("28/10/2020"));
        
        assertTrue(result);
        assertEquals(BillState.SCHEDULED, billService.getBillById(1).getState());
        
        List<Payment> payments = paymentService.getAllPayments();
        assertEquals(1, payments.size());
        assertEquals(PaymentState.PENDING, payments.get(0).getState());
    }

    @Test
    void testPayMultipleBills() {
        paymentService.cashIn(1000000);
        boolean result = paymentService.payMultipleBills(Arrays.asList(1, 2));
        
        assertTrue(result);
        assertEquals(625000, paymentService.getBalance());
        assertEquals(BillState.PAID, billService.getBillById(1).getState());
        assertEquals(BillState.PAID, billService.getBillById(2).getState());
    }
}