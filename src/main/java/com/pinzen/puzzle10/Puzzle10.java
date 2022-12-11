package main.java.com.pinzen.puzzle10;

import java.util.ArrayList;
import java.util.List;

import main.java.com.pinzen.Puzzle;

public class Puzzle10 extends Puzzle {

    private int currentValue;
    private List<Integer> cycleValues;

    private String[][] screen;

    public Puzzle10() {
        super(10);
        cycleValues = new ArrayList<Integer>();
        currentValue = 1;
        cycleValues.add(currentValue);
        this.screen = new String[6][40];

        for (String line : input) {
            if (line.equals("noop"))
                computeNoop();
            if (line.startsWith("addx")) {
                int x = Integer.parseInt(line.split(" ")[1]);
                computeAddX(x);
            }
        }
    }

    private void computeNoop() {
        this.cycleValues.add(currentValue);
    }

    private void computeAddX(int x) {
        this.cycleValues.add(currentValue);
        currentValue += x;
        this.cycleValues.add(currentValue);
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int score = 0;
        for (int cycleNumber : new int[] { 20, 60, 100, 140, 180, 220 }) {
            score += cycleNumber * cycleValues.get(cycleNumber - 1);
        }
        return score;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 6; y++) {
                int cycleNumber = x + y * 40;
                int value = cycleValues.get(cycleNumber);

                if (value == (x - 1) || value == x || value == (x + 1)) {
                    this.screen[y][x] = "#";
                } else {
                    this.screen[y][x] = ".";
                }
            }
        }
        for (String[] screenChars : this.screen) {
            System.out.println(String.join("", screenChars));
        }
        return null;
    }

}
