package commands;

import core.Bill;
import core.BillService;
import utils.DateUtils;

import java.util.List;

/**
 * Command to list bills by due date
 */
public class DueDateCommand implements Command {
    private final BillService billService;

    public DueDateCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {
        List<Bill> unpaidBills = billService.getUnpaidBills();
        
        if (unpaidBills.isEmpty()) {
            System.out.println("No unpaid bills found.");
            return;
        }
        
        System.out.println("Bill No. Type Amount Due Date State PROVIDER");
        for (Bill bill : unpaidBills) {
            System.out.println(bill.getId() + ". " + 
                    bill.getType() + " " + 
                    bill.getAmount() + " " + 
                    DateUtils.formatDate(bill.getDueDate()) + " " + 
                    bill.getState() + " " + 
                    bill.getProvider());
        }
    }
}