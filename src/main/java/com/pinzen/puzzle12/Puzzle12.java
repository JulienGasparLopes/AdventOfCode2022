package main.java.com.pinzen.puzzle12;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import main.java.com.pinzen.Puzzle;

public class Puzzle12 extends Puzzle {
    public int width, height;
    public Vertex[][] heightMap;
    public Vertex start, end;

    public class Vertex {
        public int x, y, height;
        public Path shortestPath;

        public Vertex(int x, int y, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
        }

        public String toString() {
            return "Vertex(" + x + "," + y + "): " + height;
        }

        public double getEndDistance() {
            return Math.pow(this.x - end.x, 2) + Math.pow(this.y - end.y, 2);
        }

        public double getPonderation() {
            return this.getEndDistance() * (width * height + 1) + this.y * (width + 1) + this.x;
        }
    }

    public class Path {
        public LinkedList<Vertex> vertices;

        public Path(Vertex[] vertices) {
            this.vertices = new LinkedList<Vertex>();
            for (Vertex v : vertices)
                this.vertices.add(v);
        }

        public Path(List<Vertex> vertices) {
            this.vertices = new LinkedList<Vertex>();
            for (Vertex v : vertices)
                this.vertices.add(v);
        }

        public Path getExtended(Vertex v) {
            Path path = new Path(this.vertices);
            path.vertices.add(v);
            return path;
        }

        public double getPonderation() {
            return this.vertices.size() * width * height * width * width * width * width * width
                    + this.vertices.getLast().getPonderation();
        }
    }

    public Puzzle12() {
        super(12);

        this.width = this.input.get(0).length();
        this.height = this.input.size();
        this.heightMap = new Vertex[height][width];

        int x = 0, y = 0;
        for (String line : this.input) {
            x = 0;
            for (char c : line.toCharArray()) {
                int height = c - 97;
                if (c == 'E') {
                    this.end = new Vertex(x, y, 25);
                    this.heightMap[y][x] = this.end;
                } else if (c == 'S') {
                    this.start = new Vertex(x, y, 0);
                    this.heightMap[y][x] = this.start;
                } else {
                    this.heightMap[y][x] = new Vertex(x, y, height);
                }
                x++;
            }
            y++;
        }
        System.out.println("Width: " + width + ", Height: " + height);
        System.out.println("Start: " + this.start);
        System.out.println("End: " + this.end);
    }

    public Vertex getVertex(int x, int y) {
        return this.heightMap[y][x];
    }

    @Override
    public Object computeFirstHalfAnswer() {
        SortedMap<Double, Path> paths = new TreeMap<Double, Path>();
        Path startingPath = new Path(new Vertex[] { start });
        paths.put(startingPath.getPonderation(), startingPath);

        Path pathToEnd = null;
        while (pathToEnd == null) {
            Path currentPath = paths.remove(paths.firstKey());
            Vertex currentVertex = currentPath.vertices.getLast();
            List<Vertex> nextVertices = new ArrayList<Vertex>();
            if (currentVertex.x > 0)
                nextVertices.add(this.getVertex(currentVertex.x - 1, currentVertex.y));
            if (currentVertex.x < this.width - 1)
                nextVertices.add(this.getVertex(currentVertex.x + 1, currentVertex.y));
            if (currentVertex.y > 0)
                nextVertices.add(this.getVertex(currentVertex.x, currentVertex.y - 1));
            if (currentVertex.y < this.height - 1)
                nextVertices.add(this.getVertex(currentVertex.x, currentVertex.y + 1));

            for (Vertex v : nextVertices) {
                if ((v.height <= currentVertex.height + 1)) {
                    Path newPath = currentPath.getExtended(v);
                    if (v.shortestPath == null || v.shortestPath.vertices.size() > newPath.vertices.size()) {
                        v.shortestPath = newPath;
                        if (v.x == end.x && v.y == end.y) {
                            pathToEnd = newPath;
                            break;
                        }
                        System.out.println(v.getEndDistance());
                        paths.put(newPath.getPonderation(), newPath);
                    }
                }
            }
        }

        return pathToEnd.vertices.size() - 1;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        SortedMap<Double, Path> paths = new TreeMap<Double, Path>();
        Path startingPath = new Path(new Vertex[] { end });
        paths.put(startingPath.getPonderation(), startingPath);

        Path pathToEnd = null;
        while (pathToEnd == null) {
            Path currentPath = paths.remove(paths.firstKey());
            Vertex currentVertex = currentPath.vertices.getLast();
            List<Vertex> nextVertices = new ArrayList<Vertex>();
            if (currentVertex.x > 0)
                nextVertices.add(this.getVertex(currentVertex.x - 1, currentVertex.y));
            if (currentVertex.x < this.width - 1)
                nextVertices.add(this.getVertex(currentVertex.x + 1, currentVertex.y));
            if (currentVertex.y > 0)
                nextVertices.add(this.getVertex(currentVertex.x, currentVertex.y - 1));
            if (currentVertex.y < this.height - 1)
                nextVertices.add(this.getVertex(currentVertex.x, currentVertex.y + 1));

            for (Vertex v : nextVertices) {
                if ((v.height <= currentVertex.height + 1)) {
                    Path newPath = currentPath.getExtended(v);
                    if (v.shortestPath == null || v.shortestPath.vertices.size() > newPath.vertices.size()) {
                        v.shortestPath = newPath;
                        if (v.height == 0) {
                            pathToEnd = newPath;
                            break;
                        }
                        paths.put((double) (newPath.vertices.size() * (width * height + 1) + v.y * (width + 1) + v.x),
                                newPath);
                    }
                }
            }
        }

        return pathToEnd.vertices.size() - 1;
    }

}
