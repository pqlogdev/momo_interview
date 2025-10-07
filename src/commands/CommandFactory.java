package commands;

import core.BillService;
import core.PaymentService;


public class CommandFactory {
    private final BillService billService;
    private final PaymentService paymentService;

    public CommandFactory(BillService billService, PaymentService paymentService) {
        this.billService = billService;
        this.paymentService = paymentService;
    }

    public Command createCommand(String commandName) {
        String[] parts = commandName.toUpperCase().split(" ", 2);
        String mainCommand = parts[0];
        
        // Handle unified bill commands
        if (mainCommand.equals("BILL")) {
            return new BillCommand(billService);
        }
        
        // Handle unified payment commands
        if (mainCommand.equals("PAYMENT")) {
            return new PaymentCommand(paymentService, billService);
        }
        
        // Handle legacy command format for backward compatibility
        switch (mainCommand) {
            case "CASH_IN":
                return new PaymentCommand(paymentService, billService);
            case "LIST_BILL":
            case "CREATE_BILL":
            case "UPDATE_BILL":
            case "DELETE_BILL":
            case "DUE_DATE":
            case "SEARCH_BILL_BY_PROVIDER":
                return new BillCommand(billService);
            case "PAY":
            case "SCHEDULE":
            case "LIST_PAYMENT":
                return new PaymentCommand(paymentService, billService);
            default:
                return null;
        }
    }
}