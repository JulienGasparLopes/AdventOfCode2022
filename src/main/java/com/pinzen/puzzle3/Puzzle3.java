package main.java.com.pinzen.puzzle3;

import java.util.ArrayList;
import java.util.List;

import main.java.com.pinzen.Puzzle;

public class Puzzle3 extends Puzzle {

    private List<Rucksack> rucksacs;

    private static int charToPriority(char c) {
        // a = 97, A = 65
        return (c > 96) ? (c - 96) : (c - 38);
    }

    public class Rucksack {
        private String content;
        private String compartmentOne;
        private String compartmentTwo;

        public Rucksack(String content) {
            this.content = content;
            this.compartmentOne = content.substring(0, content.length() / 2);
            this.compartmentTwo = content.substring(content.length() / 2, content.length());
        }

        public int getWrongItemValue() {
            for (char c : this.compartmentOne.toCharArray()) {
                if (this.compartmentTwo.indexOf(c) > -1) {
                    return charToPriority(c);
                }
            }
            return 0;
        }

        public static int findBadge(Rucksack sac1, Rucksack sac2, Rucksack sac3) {
            for (char c : sac1.content.toCharArray()) {
                if ((sac2.content.indexOf(c) > -1) && (sac3.content.indexOf(c) > -1)) {
                    return charToPriority(c);
                }
            }
            return 0;
        }
    }

    public Puzzle3() {
        super(3);
        this.rucksacs = new ArrayList<Rucksack>();
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int score = 0;

        for (String line : this.input) {
            Rucksack sac = new Rucksack(line);
            score += sac.getWrongItemValue();

            this.rucksacs.add(sac);
        }

        return score;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int score = 0;

        int index = 0;
        while (index < this.rucksacs.size()) {
            Rucksack sac1 = this.rucksacs.get(index);
            Rucksack sac2 = this.rucksacs.get(index + 1);
            Rucksack sac3 = this.rucksacs.get(index + 2);

            score += Rucksack.findBadge(sac1, sac2, sac3);
            index += 3;
        }
        return score;
    }

}
