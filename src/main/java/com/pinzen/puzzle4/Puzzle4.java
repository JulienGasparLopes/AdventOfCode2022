package main.java.com.pinzen.puzzle4;

import main.java.com.pinzen.Puzzle;

public class Puzzle4 extends Puzzle {

    public class Area {
        private int start, end;

        public Area(String input) {
            String[] values = input.split("-");
            this.start = Integer.parseInt(values[0]);
            this.end = Integer.parseInt(values[1]);
        }

        public boolean contains(Area area) {
            return start <= area.start && end >= area.end;
        }

        public boolean contains(int point) {
            return start <= point && end >= point;
        }
    }

    public class PairArea {
        private Area area1, area2;

        public PairArea(String input) {
            String[] values = input.split(",");
            this.area1 = new Area(values[0]);
            this.area2 = new Area(values[1]);
        }

        public boolean oneAreaContained() {
            return area1.contains(area2) || area2.contains(area1);
        }

        public boolean areasOverlap() {
            return area1.contains(area2.start) || area1.contains(area2.end) || area2.contains(area1.start)
                    || area2.contains(area1.end) || this.oneAreaContained();
        }
    }

    public Puzzle4() {
        super(4);
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int score = 0;
        for (String line : input) {
            PairArea pairArea = new PairArea(line);
            score += pairArea.oneAreaContained() ? 1 : 0;
        }
        return score;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int score = 0;
        for (String line : input) {
            PairArea pairArea = new PairArea(line);
            score += pairArea.areasOverlap() ? 1 : 0;
        }
        return score;
    }

}
