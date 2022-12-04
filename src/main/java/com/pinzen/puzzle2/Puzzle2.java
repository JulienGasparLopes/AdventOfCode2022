package main.java.com.pinzen.puzzle2;

import main.java.com.pinzen.Puzzle;

public class Puzzle2 extends Puzzle {

    private static int LOOSE = 0, DRAW = 3, WIN = 6;

    private enum Choice {
        ROCK("A", "X", 1, "SCISSOR", "PAPER"),
        PAPER("B", "Y", 2, "ROCK", "SCISSOR"),
        SCISSOR("C", "Z", 3, "PAPER", "ROCK");

        private String key_opp, key_myself;
        private int value;
        private String winAgainst;
        private String looseAgainst;

        Choice(String key_opp, String key_myself, int value, String winAgainst, String looseAgainst) {
            this.key_opp = key_opp;
            this.key_myself = key_myself;
            this.value = value;
            this.winAgainst = winAgainst;
            this.looseAgainst = looseAgainst;
        }

        public int getScore() {
            return this.value;
        }

        public int getScoreAgainst(Choice choice) {
            if (choice.name().equals(this.name()))
                return DRAW;
            if (choice.name().equals(this.winAgainst))
                return WIN;
            return LOOSE;
        }

        public Choice getChoiceForOutcome(int outcome) {
            if (outcome == DRAW)
                return this;
            if (outcome == LOOSE)
                return getChoiceFromKey(this.winAgainst);
            return getChoiceFromKey(this.looseAgainst);
        }

        public static Choice getChoiceFromKey(String key) {
            for (Choice choice : Choice.values()) {
                if (choice.key_myself.equals(key) || choice.key_opp.equals(key) || choice.name().equals(key)) {
                    return choice;
                }
            }
            return null;
        }
    };

    private int outcomStringToValue(String outcomeString) {
        if (outcomeString.equals("X"))
            return LOOSE;
        if (outcomeString.equals("Y"))
            return DRAW;
        return WIN;
    }

    public Puzzle2() {
        super(2);
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int score = 0;
        for (String line : input) {
            String[] choices = line.split(" ");
            Choice choiceOpp = Choice.getChoiceFromKey(choices[0]);
            Choice choiceMyself = Choice.getChoiceFromKey(choices[1]);

            score += choiceMyself.getScore();
            score += choiceMyself.getScoreAgainst(choiceOpp);
        }

        return score;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int score = 0;
        for (String line : input) {
            String[] choices = line.split(" ");
            Choice choiceOpp = Choice.getChoiceFromKey(choices[0]);
            int outcom = outcomStringToValue(choices[1]);

            Choice choiceMyself = choiceOpp.getChoiceForOutcome(outcom);
            score += choiceMyself.getScore();
            score += choiceMyself.getScoreAgainst(choiceOpp);
        }

        return score;
    }
}