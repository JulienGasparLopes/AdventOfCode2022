package main.java.com.pinzen.puzzle5;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.com.pinzen.Puzzle;

public class Puzzle5 extends Puzzle {

    private LinkedList<String>[] stacks;
    private List<String> lines;

    public Puzzle5() {
        super(5);

        this.resetStacks();
        this.lines = this.input.subList(10, this.input.size());
    }

    @SuppressWarnings("unchecked")
    private void resetStacks() {
        this.stacks = new LinkedList[9];

        for (int i = 0; i < 9; i++) {
            this.stacks[i] = new LinkedList<String>();
        }

        int currentIndex = 0;
        while (currentIndex < 8) {
            String line = this.input.get(currentIndex);
            currentIndex++;
            int stackIndex = 0;
            do {
                String value = line.substring(stackIndex * 4 + 1, stackIndex * 4 + 2);
                if (!value.equals(" ")) {
                    this.stacks[stackIndex].push(value);
                }
                stackIndex++;
            } while (stackIndex < stacks.length);
        }
    }

    @SuppressWarnings("unused")
    private void printStacks() {
        for (LinkedList<String> stack : this.stacks) {
            System.out.println(stack);
        }
        System.out.println("=====================================");
    }

    @Override
    public Object computeFirstHalfAnswer() {
        Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

        for (String line : this.lines) {
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            int number = Integer.parseInt(matcher.group(1));
            int source = Integer.parseInt(matcher.group(2)) - 1;
            int target = Integer.parseInt(matcher.group(3)) - 1;

            List<String> stackSource = this.stacks[source];
            List<String> toMove = stackSource.subList(stackSource.size() - number, stackSource.size());
            this.stacks[source] = new LinkedList<String>(stackSource.subList(0, stackSource.size() - number));

            List<String> toMoveInv = new LinkedList<String>();
            for (int i = toMove.size() - 1; i >= 0; i--) {
                toMoveInv.add(toMove.get(i));
            }
            this.stacks[target].addAll(toMoveInv);
        }

        String answer = "";
        for (LinkedList<String> stack : this.stacks) {
            answer += stack.getLast();
        }

        return answer;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        this.resetStacks();

        Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

        for (String line : this.lines) {
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            int number = Integer.parseInt(matcher.group(1));
            int source = Integer.parseInt(matcher.group(2)) - 1;
            int target = Integer.parseInt(matcher.group(3)) - 1;

            List<String> stackSource = this.stacks[source];
            List<String> toMove = stackSource.subList(stackSource.size() - number, stackSource.size());
            this.stacks[source] = new LinkedList<String>(stackSource.subList(0, stackSource.size() - number));

            this.stacks[target].addAll(toMove);
        }

        String answer = "";
        for (LinkedList<String> stack : this.stacks) {
            answer += stack.getLast();
        }

        return answer;
    }

}
