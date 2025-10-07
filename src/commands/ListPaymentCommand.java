package commands;

import core.Payment;
import core.PaymentService;
import utils.DateUtils;

import java.util.List;

/**
 * Command to list payment history
 */
public class ListPaymentCommand implements Command {
    private final PaymentService paymentService;

    public ListPaymentCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void execute(String[] args) {
        List<Payment> payments = paymentService.getAllPayments();
        
        if (payments.isEmpty()) {
            System.out.println("No payments found.");
            return;
        }
        
        System.out.println("No. Amount Payment Date State Bill Id");
        for (Payment payment : payments) {
            System.out.println(payment.getId() + ". " + 
                    payment.getAmount() + " " + 
                    DateUtils.formatDate(payment.getPaymentDate()) + " " + 
                    payment.getState() + " " + 
                    payment.getBillId());
        }
    }
}