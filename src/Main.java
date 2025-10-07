import commands.CommandFactory;
import commands.Command;
import core.BillService;
import core.PaymentService;
import repository.BillRepository;
import repository.PaymentRepository;
import utils.ConsoleUtils;

public class Main {
    public static void main(String[] args) {
        BillRepository billRepository = new BillRepository();
        PaymentRepository paymentRepository = new PaymentRepository();
        BillService billService = new BillService(billRepository);
        PaymentService paymentService = new PaymentService(paymentRepository, billRepository);
        
        // Initialize with sample data
        billService.initializeWithSampleData();
        
        CommandFactory commandFactory = new CommandFactory(billService, paymentService);
        
        if (args.length > 0) {
            // Execute a single command from command line arguments
            String commandName = args[0];
            String[] commandArgs = new String[args.length - 1];
            System.arraycopy(args, 1, commandArgs, 0, args.length - 1);
            
            Command command = commandFactory.createCommand(commandName);
            if (command != null) {
                command.execute(commandArgs);
            } else {
                System.out.println("Unknown command: " + commandName);
            }
        } else {
            // Interactive shell mode
            ConsoleUtils.printWelcomeMessage();
            boolean running = true;
            
            while (running) {
                String input = ConsoleUtils.readInput();
                if (input.trim().isEmpty()) {
                    continue;
                }
                
                String[] inputParts = input.trim().split("\\s+", 2);
                String commandName = inputParts[0];
                String[] commandArgs = inputParts.length > 1 ? inputParts[1].split("\\s+") : new String[0];
                
                if (commandName.equalsIgnoreCase("EXIT")) {
                    System.out.println("Good bye!");
                    running = false;
                } else {
                    Command command = commandFactory.createCommand(commandName);
                    if (command != null) {
                        command.execute(commandArgs);
                    } else {
                        System.out.println("Unknown command: " + commandName);
                    }
                }
            }
        }
    }
}