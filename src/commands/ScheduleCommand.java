package commands;

import core.Bill;
import core.BillService;
import core.PaymentService;
import utils.DateUtils;

import java.time.LocalDate;

/**
 * Command to schedule a bill payment
 */
public class ScheduleCommand implements Command {
    private final BillService billService;
    private final PaymentService paymentService;

    public ScheduleCommand(BillService billService, PaymentService paymentService) {
        this.billService = billService;
        this.paymentService = paymentService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: SCHEDULE <bill_id> <date>");
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