package utils;

import java.util.Scanner;

/**
 * Utility class for console operations
 */
public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Print welcome message
     */
    public static void printWelcomeMessage() {
        System.out.println("Welcome to Bill Payment Service");
        System.out.println("Type 'EXIT' to quit the program");
    }

    /**
     * Read input from console
     */
    public static String readInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }
}