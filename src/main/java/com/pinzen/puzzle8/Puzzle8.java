package main.java.com.pinzen.puzzle8;

import main.java.com.pinzen.Puzzle;

public class Puzzle8 extends Puzzle {

    private int width, height;
    private int[][] trees;

    public Puzzle8() {
        super(8);
        this.width = this.input.get(0).length();
        this.height = this.input.size();

        this.trees = new int[width][height];

        int currentHeight = 0;
        for (String str : this.input) {
            for (int i = 0; i < str.length(); i++) {
                this.trees[i][currentHeight] = Integer.parseInt(str.charAt(i) + "");
            }
            currentHeight++;
        }
    }

    private boolean isVisibleFromLeft(int x, int y) {
        int size = this.trees[x][y];
        int currentX = x;
        while (currentX > 0) {
            currentX--;
            if (this.trees[currentX][y] >= size)
                return false;
        }
        return true;
    }

    private boolean isVisibleFromRight(int x, int y) {
        int size = this.trees[x][y];
        int currentX = x;
        while (currentX < this.width - 1) {
            currentX++;
            if (this.trees[currentX][y] >= size)
                return false;
        }
        return true;
    }

    private boolean isVisibleFromTop(int x, int y) {
        int size = this.trees[x][y];
        int currentY = y;
        while (currentY > 0) {
            currentY--;
            if (this.trees[x][currentY] >= size)
                return false;
        }
        return true;
    }

    private boolean isVisibleFromBottom(int x, int y) {
        int size = this.trees[x][y];
        int currentY = y;
        while (currentY < this.height - 1) {
            currentY++;
            if (this.trees[x][currentY] >= size)
                return false;
        }
        return true;
    }

    @Override
    public Object computeFirstHalfAnswer() {
        int visibleCount = 0;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (isVisibleFromLeft(i, j) || isVisibleFromRight(i, j) || isVisibleFromTop(i, j)
                        || isVisibleFromBottom(i, j))
                    visibleCount++;
            }
        }
        return visibleCount;
    }

    private int countVisibleTreesLeft(int x, int y) {
        int size = this.trees[x][y];
        int treeCount = 0;
        int currentX = x;
        while (currentX > 0) {
            currentX--;
            treeCount++;
            if (this.trees[currentX][y] >= size)
                return treeCount;
        }
        return treeCount;
    }

    private int countVisibleTreesRight(int x, int y) {
        int size = this.trees[x][y];
        int treeCount = 0;
        int currentX = x;
        while (currentX < this.width - 1) {
            currentX++;
            treeCount++;
            if (this.trees[currentX][y] >= size)
                return treeCount;
        }
        return treeCount;
    }

    private int countVisibleTreesTop(int x, int y) {
        int size = this.trees[x][y];
        int treeCount = 0;
        int currentY = y;
        while (currentY > 0) {
            currentY--;
            treeCount++;
            if (this.trees[x][currentY] >= size)
                return treeCount;
        }
        return treeCount;
    }

    private int countVisibleTreesBottom(int x, int y) {
        int size = this.trees[x][y];
        int treeCount = 0;
        int currentY = y;
        while (currentY < this.height - 1) {
            currentY++;
            treeCount++;
            if (this.trees[x][currentY] >= size)
                return treeCount;
        }
        return treeCount;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int maxScenicScore = 0;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                int scenicScore = countVisibleTreesLeft(i, j) * countVisibleTreesRight(i, j)
                        * countVisibleTreesTop(i, j) * countVisibleTreesBottom(i, j);
                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore;
                }
            }
        }
        return maxScenicScore;
    }

}
