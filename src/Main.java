// OOAD Spring 2023 Project 3
// Friendly Neighborhood Car Dealership (FNCD)
// Base Implementation Adopted from Bruce Montgomery - 2/19/23
// UML Diagram comments can be found within the final UML Diagram


import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Main {
    public static void main(String[] args)  throws FileNotFoundException {
        PrintStream outPut = new PrintStream("./SimResults.txt");
        System.setOut(outPut);
        // bootstrap code only - no logic
        Simulator sim = new Simulator();
        sim.run();  // Let's do this thing
    }
}
