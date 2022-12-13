package main.java.com.pinzen.puzzle11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import main.java.com.pinzen.Puzzle;

public class Puzzle11 extends Puzzle {

    private class Item {
        public double stress;

        public Item(double stress) {
            this.stress = stress;
        }
    }

    private class Monkey {
        private int index;

        public LinkedList<Item> items;

        private double divisibleBy;
        private String operationSymbol;
        private String[] values;
        public int monkeyIndexIfTrue, monkeyIndexIfFalse;

        public double inspectedItemsNumber;

        public Monkey(int index) {
            this.index = index / 7;
            this.inspectedItemsNumber = 0;
            this.items = new LinkedList<Item>();
            // Items list
            String[] stressLevels = input.get(index + 1).replace("  Starting items: ", "").split(", ");
            for (String stressLevel : stressLevels) {
                this.items.add(new Item(Integer.parseInt(stressLevel)));
            }
            // Operation
            String operation = input.get(index + 2).replace("  Operation: new = ", "");
            this.operationSymbol = operation.contains(" * ") ? " \\* " : " \\+ ";
            this.values = operation.split(this.operationSymbol);
            // Test and Conditions
            this.divisibleBy = Double.parseDouble(input.get(index + 3).replace("  Test: divisible by ", ""));
            this.monkeyIndexIfTrue = Integer
                    .parseInt(input.get(index + 4).replace("    If true: throw to monkey ", ""));
            this.monkeyIndexIfFalse = Integer
                    .parseInt(input.get(index + 5).replace("    If false: throw to monkey ", ""));
        }

        private double applyStress(double currentStress) {
            boolean multiply = this.operationSymbol.equals(" \\* ");
            double left = values[0].equals("old") ? currentStress : Double.parseDouble(values[0]);
            double right = values[1].equals("old") ? currentStress : Double.parseDouble(values[1]);
            return multiply ? (left * right) : (left + right);
        }

        public void inspectItem(boolean relief) {
            Item item = this.items.pollFirst();
            item.stress = applyStress(item.stress);

            if (relief)
                item.stress = Math.floor((item.stress) / 3d);
            else
                item.stress = item.stress % globalModulo;

            double stressModulo = item.stress % divisibleBy;
            monkeys.get(stressModulo == 0f ? monkeyIndexIfTrue : monkeyIndexIfFalse).items.addLast(item);

            this.inspectedItemsNumber++;
        }
    }

    private HashMap<Integer, Monkey> monkeys;
    private double globalModulo;

    public Puzzle11() {
        super(11);
    }

    private void parseMonkeys() {
        this.monkeys = new HashMap<Integer, Monkey>();
        this.globalModulo = 1;
        int i = 0;
        while (i < this.input.size()) {
            Monkey m = new Monkey(i);
            this.monkeys.put(i / 7, m);
            this.globalModulo *= m.divisibleBy;
            i += 7;
        }
    }

    private String retrieveMonkeyBusiness() {
        List<Double> values = new ArrayList<Double>();
        double max = 0, secondMax = 0;
        for (Monkey m : monkeys.values()) {
            values.add(m.inspectedItemsNumber);
            if (m.inspectedItemsNumber > max)
                max = m.inspectedItemsNumber;
        }
        // Second max loop
        for (double value : values) {
            if (value != max && value > secondMax) {
                secondMax = value;
            }
        }
        System.out.println(max + " " + secondMax);
        return String.format("%.2f", max * secondMax);
    }

    private void computeMonkeyRound(int index, boolean relief) {
        Monkey monkey = this.monkeys.get(index);
        while (monkey.items.size() > 0) {
            monkey.inspectItem(relief);
        }
    }

    @Override
    public Object computeFirstHalfAnswer() {
        this.parseMonkeys();

        for (int round = 0; round < 20; round++) {
            for (int i = 0; i < this.monkeys.size(); i++) {
                this.computeMonkeyRound(i, true);
            }
        }

        return retrieveMonkeyBusiness();
    }

    @Override
    public Object computeSecondHalfAnswer() {
        this.parseMonkeys();

        for (int round = 0; round < 10000; round++) {
            for (int i = 0; i < this.monkeys.size(); i++) {
                this.computeMonkeyRound(i, false);
            }
        }

        return retrieveMonkeyBusiness();
    }

}
