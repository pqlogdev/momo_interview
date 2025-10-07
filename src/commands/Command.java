package commands;

/**
 * Interface for command pattern implementation
 */
public interface Command {
    /**
     * Execute the command with given arguments
     * @param args Command arguments
     */
    void execute(String[] args);
}