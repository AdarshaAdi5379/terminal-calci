import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Calculator calc = new Calculator();

        // One-shot mode (command-line arguments)
        if (args.length > 0) {
            String expr = String.join(" ", args).trim();
            if (expr.equalsIgnoreCase("help")) {
                printHelp();
                return;
            }
            try {
                Double result = calc.process(expr);
                if (result != null) System.out.println(result);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            return;
        }

        // Interactive mode
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Terminal Calculator — type 'help' for commands.");

        while (true) {
            System.out.print("> ");
            String line = br.readLine();
            if (line == null) break;
            line = line.trim();
            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) break;
            if (line.equalsIgnoreCase("help")) { printHelp(); continue; }

            try {
                Double result = calc.process(line);
                if (result != null) System.out.println(result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  help          Show this message");
        System.out.println("  exit | quit   Exit the program");
        System.out.println("Examples:");
        System.out.println("  2+3*4");
        System.out.println("  (2+3)^2");
        System.out.println("  x=5");
        System.out.println("  x*2");
    }
}
