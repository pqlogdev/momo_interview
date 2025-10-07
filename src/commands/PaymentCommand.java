package commands;

import core.Bill;
import core.BillService;
import core.Payment;
import core.PaymentService;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentCommand implements Command {
    private final PaymentService paymentService;
    private final BillService billService;

    public PaymentCommand(PaymentService paymentService, BillService billService) {
        this.paymentService = paymentService;
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String operation = args[0].toUpperCase();
        String[] operationArgs = new String[args.length - 1];
        System.arraycopy(args, 1, operationArgs, 0, args.length - 1);

        switch (operation) {
            case "PAY":
                payBill(operationArgs);
                break;
            case "CASH_IN":
                cashIn(operationArgs);
                break;
            case "LIST":
                listPayments();
                break;
            case "SCHEDULE":
                schedulePayment(operationArgs);
                break;
            default:
                System.out.println("Unknown payment operation: " + operation);
                printUsage();
        }
    }

    private void printUsage() {
        System.out.println("Payment command usage:");
        System.out.println("  PAYMENT PAY <bill_id> [<bill_id> ...] - Pay one or multiple bills");
        System.out.println("  PAYMENT CASH_IN <amount> - Add funds to your account");
        System.out.println("  PAYMENT LIST - List all payments");
        System.out.println("  PAYMENT SCHEDULE <bill_id> <date> - Schedule a payment for a future date");
    }

    private void payBill(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: PAYMENT PAY <bill_id> [<bill_id> ...]");
            return;
        }

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

    private void cashIn(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: PAYMENT CASH_IN <amount>");
            return;
        }

        try {
            long amount = Long.parseLong(args[0]);
            long balance = paymentService.cashIn(amount);
            System.out.println("Your available balance: " + balance);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listPayments() {
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

    private void schedulePayment(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: PAYMENT SCHEDULE <bill_id> <date>");
            return;
        }

        try {
            int billId = Integer.parseInt(args[0]);
            Bill bill = billService.getBillById(billId);
            
            if (bill == null) {
                System.out.println("Sorry! Not found a bill with such id");
                return;
            }
            
            LocalDate scheduleDate = DateUtils.parseDate(args[1]);
            boolean success = paymentService.schedulePayment(billId, scheduleDate);
            
            if (success) {
                System.out.println("Payment for bill id " + billId + " is scheduled on " + args[1]);
            } else {
                System.out.println("Failed to schedule payment. The bill may already be paid.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid bill ID format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use format dd/MM/yyyy.");
        }
    }
}