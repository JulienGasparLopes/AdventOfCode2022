package main.java.com.pinzen.puzzle2;

import main.java.com.pinzen.Puzzle;

public class Puzzle2 extends Puzzle {

    private enum CHOICE {
        ROCK("A", "X", 1, "SCISSOR"),
        PAPER("B", "Y", 2, "ROCK"),
        SCISSOR("C", "Z", 3, "PAPER");

        private String key_opp, key_myself;
        private int value;
        private String winAgainst;

        CHOICE(String key_opp, String key_myself, int value, String winAgainst) {
            this.key_opp = key_opp;
            this.key_myself = key_myself;
            this.value = value;
            this.winAgainst = winAgainst;
        }

        public int getScore() {
            return this.value;
        }

        public int getScoreAgainst(CHOICE choice) {
            if (choice.name().equals(this.name()))
                return 3; // Draw
            if (choice.name().equals(this.winAgainst))
                return 6; // Win
            return 0; // Loose
        }

        public static CHOICE getChoiceFromKey(String key) {
            for (CHOICE choice : CHOICE.values()) {
                if (choice.key_myself.equals(key) || choice.key_opp.equals(key) || choice.name().equals(key)) {
                    return choice;
                }
            }
            return null;
        }
    };

    public Puzzle2() {
        super(2);
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int score = 0;
        for (String line : input) {
            String[] choices = line.split(" ");
            CHOICE choice_opp = CHOICE.getChoiceFromKey(choices[0]);
            CHOICE choice_myself = CHOICE.getChoiceFromKey(choices[1]);

            score += choice_myself.getScore();
            score += choice_myself.getScoreAgainst(choice_opp);
        }

        return score;
    }

    @Override
    public Object computeSecondHalfAnswer() {

        return 0;
    }
}