package main.java.com.pinzen.puzzle6;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.com.pinzen.Puzzle;

public class Puzzle6 extends Puzzle {

    public Puzzle6() {
        super(6);
    }

    private boolean isSeparator(String packet) {
        for (int i = 0; i < packet.length(); i++) {
            char first = packet.charAt(i);
            for (int j = 0; j < packet.length(); j++) {
                if (i == j)
                    continue;
                char second = packet.charAt(j);
                if (first == second)
                    return false;
            }
        }

        return true;
    }

    @Override
    public Object computeFirstHalfAnswer() {
        String data = this.input.get(0);

        int i = 0;
        String packet;
        do {
            packet = data.substring(i, i + 4);
            i++;
        } while (!this.isSeparator(packet));

        return i + 3;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        String data = this.input.get(0);

        int i = 0;
        String packet;
        do {
            packet = data.substring(i, i + 14);
            i++;
        } while (!this.isSeparator(packet));

        return i + 13;
    }

}
