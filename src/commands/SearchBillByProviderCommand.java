package commands;

import core.Bill;
import core.BillService;
import utils.DateUtils;

import java.util.List;

/**
 * Command to search bills by provider name
 */
public class SearchBillByProviderCommand implements Command {
    private final BillService billService;

    public SearchBillByProviderCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: SEARCH_BILL_BY_PROVIDER <provider_name>");
            return;
        }

        String providerName = args[0];
        List<Bill> bills = billService.getBillsByProvider(providerName);
        
        if (bills.isEmpty()) {
            System.out.println("No bills found for provider: " + providerName);
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