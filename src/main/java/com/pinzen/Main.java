package main.java.com.pinzen;

import main.java.com.pinzen.puzzle1.Puzzle1;
import main.java.com.pinzen.puzzle2.Puzzle2;
import main.java.com.pinzen.puzzle3.Puzzle3;
import main.java.com.pinzen.puzzle4.Puzzle4;
import main.java.com.pinzen.puzzle5.Puzzle5;

public class Main {
    public static void main(String[] args) {
        // Puzzle puzzle = new Puzzle1();
        // Puzzle puzzle = new Puzzle2();
        // Puzzle puzzle = new Puzzle3();
        // Puzzle puzzle = new Puzzle4();
        Puzzle puzzle = new Puzzle5();

        System.out.println("First part answer: " + puzzle.computeFirstHalfAnswer());
        System.out.println("Second part answer: " + puzzle.computeSecondHalfAnswer());
    }
}