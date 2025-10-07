package commands;

import core.Bill;
import core.BillService;
import core.BillState;
import core.BillType;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;


public class BillCommand implements Command {
    private final BillService billService;

    public BillCommand(BillService billService) {
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
            case "LIST":
                listBills();
                break;
            case "CREATE":
                createBill(operationArgs);
                break;
            case "UPDATE":
                updateBill(operationArgs);
                break;
            case "DELETE":
                deleteBill(operationArgs);
                break;
            case "SEARCH_BY_PROVIDER":
                searchBillsByProvider(operationArgs);
                break;
            case "DUE_DATE":
                listUnpaidBills();
                break;
            default:
                System.out.println("Unknown bill operation: " + operation);
                printUsage();
        }
    }

    private void printUsage() {
        System.out.println("Bill command usage:");
        System.out.println("  BILL LIST - List all bills");
        System.out.println("  BILL CREATE <type> <amount> <dueDate> <provider> - Create a new bill");
        System.out.println("  BILL UPDATE <billId> <type> <amount> <dueDate> <state> <provider> - Update a bill");
        System.out.println("  BILL DELETE <billId> - Delete a bill");
        System.out.println("  BILL SEARCH_BY_PROVIDER <provider_name> - Search bills by provider");
        System.out.println("  BILL DUE_DATE - List all unpaid bills");
    }

    private void listBills() {
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

    private void createBill(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: BILL CREATE <type> <amount> <dueDate> <provider>");
            return;
        }
        
        try {
            String type = args[0];
            long amount = Long.parseLong(args[1]);
            LocalDate dueDate = DateUtils.parseDate(args[2]);
            String provider = args[3];
            
            Bill bill = billService.createBill(BillType.valueOf(type), amount, dueDate, provider);
            if (bill != null) {
                System.out.println("Bill created successfully with ID: " + bill.getId());
            } else {
                System.out.println("Failed to create bill.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error creating bill: " + e.getMessage());
        }
    }

    private void updateBill(String[] args) {
        if (args.length < 6) {
            System.out.println("Usage: BILL UPDATE <billId> <type> <amount> <dueDate> <state> <provider>");
            return;
        }
        
        try {
            int billId = Integer.parseInt(args[0]);
            BillType type = BillType.valueOf(args[1]);
            long amount = Long.parseLong(args[2]);
            LocalDate dueDate = DateUtils.parseDate(args[3]);
            BillState state = BillState.valueOf(args[4]);
            String provider = args[5];
            
            Bill bill = billService.updateBill(billId, type, amount, dueDate, state, provider);
            if (bill != null) {
                System.out.println("Bill updated successfully.");
            } else {
                System.out.println("Failed to update bill.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error updating bill: " + e.getMessage());
        }
    }

    private void deleteBill(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: BILL DELETE <billId>");
            return;
        }
        
        try {
            int billId = Integer.parseInt(args[0]);
            boolean isDeleted = billService.deleteBill(billId);
            if (isDeleted) {
                System.out.println("Bill deleted successfully.");
            } else {
                System.out.println("Failed to delete bill.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid bill ID format. Please enter a valid number.");
        }
    }

    private void searchBillsByProvider(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: BILL SEARCH_BY_PROVIDER <provider_name>");
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

    private void listUnpaidBills() {
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