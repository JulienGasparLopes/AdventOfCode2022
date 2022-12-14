package main.java.com.pinzen;

import java.util.List;

public abstract class Puzzle {

    protected int puzzleId;
    protected List<String> input;

    public Puzzle(int puzzleId) {
        this.puzzleId = puzzleId;
        this.input = Helpers.loadFile("puzzle" + this.puzzleId + "/input.in");
    }

    public abstract Object computeFirstHalfAnswer();

    public abstract Object computeSecondHalfAnswer();
}