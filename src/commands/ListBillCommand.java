package commands;

import core.Bill;
import core.BillService;
import utils.DateUtils;

import java.util.List;

/**
 * Command to list all bills
 */
public class ListBillCommand implements Command {
    private final BillService billService;

    public ListBillCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {
        List<Bill> bills = billService.getAllBills();
        
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
            return;
        }
        
        System.out.println("Bill No. Type Amount Due Date State PROVIDER");
        for (Bill bill : bills) {
            System.out.println(bill.getId() + ". " + 
                    bill.getType() + " " + 
                    bill.getAmount() + " " + 
                    DateUtils.formatDate(bill.getDueDate()) + " " + 
                    bill.getState() + " " + 
                    bill.getProvider());
        }
    }
}