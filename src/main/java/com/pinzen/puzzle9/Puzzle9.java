package main.java.com.pinzen.puzzle9;

import java.util.HashSet;
import java.util.Set;

import main.java.com.pinzen.Puzzle;

public class Puzzle9 extends Puzzle {

    private class Knot {
        private int x, y;
        private Knot headingKnot, trailingKnot;

        public Knot(int x, int y, Knot headingKnot) {
            this.x = x;
            this.y = y;
            this.headingKnot = headingKnot;
            if (this.headingKnot != null) {
                this.headingKnot.setTrailingKnot(this);
            }
        }

        public Knot(int x, int y) {
            this(x, y, null);
        }

        public void setTrailingKnot(Knot trailingKnot) {
            this.trailingKnot = trailingKnot;
        }

        public void move(String direction) {
            if (headingKnot != null) {
                this.move();
            } else {
                switch (direction) {
                    case "R":
                        this.x++;
                        break;
                    case "L":
                        this.x--;
                        break;
                    case "U":
                        this.y++;
                        break;
                    case "D":
                        this.y--;
                        break;
                }
            }
            if (this.trailingKnot != null) {
                this.trailingKnot.move(direction);
            }
        }

        public void move() {
            int diffX = this.headingKnot.x - this.x;
            int diffY = this.headingKnot.y - this.y;

            if (diffX == 0) {
                if (Math.abs(diffY) > 1) {
                    this.y += diffY / 2;
                }
            } else if (diffY == 0) {
                if (Math.abs(diffX) > 1) {
                    this.x += diffX / 2;
                }
            } else if (Math.abs(diffX) != Math.abs(diffY) || (Math.abs(diffX) > 1)) {
                this.x += Math.signum(diffX);
                this.y += Math.signum(diffY);
            }
        }
    }

    public Puzzle9() {
        super(9);
    }

    private int runCommands(Knot head, Knot tail) {
        Set<String> visitedPositions = new HashSet<String>();

        for (String line : input) {
            String[] values = line.split(" ");
            String direction = values[0];
            int stepNumber = Integer.parseInt(values[1]);
            for (int i = 0; i < stepNumber; i++) {
                head.move(direction);
                visitedPositions.add(tail.x + "." + tail.y);
            }
        }

        return visitedPositions.size();
    }

    @Override
    public Object computeFirstHalfAnswer() {
        Knot head = new Knot(0, 0);
        Knot tail = new Knot(0, 0, head);

        return this.runCommands(head, tail);
    }

    @Override
    public Object computeSecondHalfAnswer() {
        Knot head = new Knot(0, 0);
        Knot k2 = new Knot(0, 0, head);
        Knot k3 = new Knot(0, 0, k2);
        Knot k4 = new Knot(0, 0, k3);
        Knot k5 = new Knot(0, 0, k4);
        Knot k6 = new Knot(0, 0, k5);
        Knot k7 = new Knot(0, 0, k6);
        Knot k8 = new Knot(0, 0, k7);
        Knot k9 = new Knot(0, 0, k8);
        Knot tail = new Knot(0, 0, k9);

        return this.runCommands(head, tail);
    }

}
