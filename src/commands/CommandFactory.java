package commands;

import core.BillService;
import core.PaymentService;

/**
 * Factory for creating command instances
 */
public class CommandFactory {
    private final BillService billService;
    private final PaymentService paymentService;

    public CommandFactory(BillService billService, PaymentService paymentService) {
        this.billService = billService;
        this.paymentService = paymentService;
    }

    /**
     * Create a command based on the command name
     */
    public Command createCommand(String commandName) {
        switch (commandName.toUpperCase()) {
            case "CASH_IN":
                return new CashInCommand(paymentService);
            case "LIST_BILL":
                return new ListBillCommand(billService);
            case "PAY":
                return new PayCommand(billService, paymentService);
            case "DUE_DATE":
                return new DueDateCommand(billService);
            case "SCHEDULE":
                return new ScheduleCommand(billService, paymentService);
            case "LIST_PAYMENT":
                return new ListPaymentCommand(paymentService);
            case "SEARCH_BILL_BY_PROVIDER":
                return new SearchBillByProviderCommand(billService);
            default:
                return null;
        }
    }
}