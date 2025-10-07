package commands;

import core.Bill;
import core.BillService;
import core.PaymentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to pay one or multiple bills
 */
public class PayCommand implements Command {
    private final BillService billService;
    private final PaymentService paymentService;

    public PayCommand(BillService billService, PaymentService paymentService) {
        this.billService = billService;
        this.paymentService = paymentService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: PAY <bill_id> [<bill_id> ...]");
            return;
        }

        // Single bill payment
        if (args.length == 1) {
            try {
                int billId = Integer.parseInt(args[0]);
                Bill bill = billService.getBillById(billId);
                
                if (bill == null) {
                    System.out.println("Sorry! Not found a bill with such id");
                    return;
                }
                
                boolean success = paymentService.payBill(billId);
                if (success) {
                    System.out.println("Payment has been completed for Bill with id " + billId + ".");
                    System.out.println("Your current balance is: " + paymentService.getBalance());
                } else {
                    System.out.println("Sorry! Not enough fund to proceed with payment.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid bill ID format. Please enter a valid number.");
            }
        } 
        // Multiple bill payment
        else {
            List<Integer> billIds = new ArrayList<>();
            for (String arg : args) {
                try {
                    int billId = Integer.parseInt(arg);
                    Bill bill = billService.getBillById(billId);
                    
                    if (bill == null) {
                        System.out.println("Sorry! Not found a bill with id " + billId);
                        return;
                    }
                    
                    billIds.add(billId);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid bill ID format: " + arg);
                    return;
                }
            }
            
            boolean success = paymentService.payMultipleBills(billIds);
            if (success) {
                System.out.println("Payment has been completed for all specified bills.");
                System.out.println("Your current balance is: " + paymentService.getBalance());
            } else {
                System.out.println("Sorry! Not enough fund to proceed with payment.");
            }
        }
    }
}