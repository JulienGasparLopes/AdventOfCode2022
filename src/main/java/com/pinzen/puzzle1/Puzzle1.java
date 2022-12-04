package main.java.com.pinzen.puzzle1;

import java.util.ArrayList;
import java.util.List;

import main.java.com.pinzen.Puzzle;

public class Puzzle1 extends Puzzle {

    private List<Integer> caloriesByElf;

    public Puzzle1() {
        super(1);
        this.caloriesByElf = new ArrayList<Integer>();

        // Add one empty line so that last elf will be computed on loops
        this.input.add("");
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int currentMaxCalories = 0;
        int currentCalories = 0;

        for (String line : this.input) {
            if (line.length() > 0) {
                currentCalories += Integer.parseInt(line);
            } else {
                caloriesByElf.add(currentCalories);
                if (currentCalories > currentMaxCalories)
                    currentMaxCalories = currentCalories;
                currentCalories = 0;
            }
        }

        return currentMaxCalories;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int top3MaxCalories = 0;

        for (int i = 0; i < 3; i++) {
            int currentMaxCalories = 0;
            int currentMaxCaloriesIndex = -1;
            for (int calories : this.caloriesByElf) {
                if (calories > currentMaxCalories) {
                    currentMaxCalories = calories;
                    currentMaxCaloriesIndex = caloriesByElf.indexOf(calories);
                }
            }
            top3MaxCalories += currentMaxCalories;
            caloriesByElf.remove(currentMaxCaloriesIndex);
        }

        return top3MaxCalories;
    }
}