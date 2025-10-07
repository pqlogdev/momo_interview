package commands;

import core.PaymentService;

/**
 * Command to add funds to the account
 */
public class CashInCommand implements Command {
    private final PaymentService paymentService;

    public CashInCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: CASH_IN <amount>");
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
}