package main.java.com.pinzen.puzzle7;

import java.util.HashMap;

import main.java.com.pinzen.Puzzle;

public class Puzzle7 extends Puzzle {

    public abstract class Data {
        public String name;

        public Data(String name) {
            this.name = name;
        }

        public abstract int getSize();
    }

    public class File extends Data {
        private int size;

        public File(String name, int size) {
            super(name);
            this.size = size;
        }

        @Override
        public int getSize() {
            return this.size;
        }
    }

    public class Folder extends Data {
        private HashMap<String, Data> content;
        private Folder parent;

        public Folder(String name, Folder parent) {
            super(name);
            content = new HashMap<String, Data>();
            this.parent = parent;
        }

        public String getPath() {
            String path = this.name;
            Folder current = this;
            while (current.parent != null) {
                path = current.parent.name + "/" + path;
                current = current.parent;
            }
            return path;
        }

        public int getSize() {
            int size = 0;
            for (Data data : this.content.values()) {
                size += data.getSize();
            }
            return size;
        }

        public void addFolder(String name) {
            if (!this.content.containsKey(name)) {
                Folder newFolder = new Folder(name, this);
                this.content.put(name, newFolder);
                Puzzle7.ALL_FOLDERS.put(newFolder.getPath(), newFolder);
            }
        }

        public void addFile(String name, int size) {
            if (!this.content.containsKey(name)) {
                this.content.put(name, new File(name, size));
            }
        }
    }

    public static HashMap<String, Folder> ALL_FOLDERS;

    private Folder root;
    private Folder currentFolder;

    public Puzzle7() {
        super(7);

        root = new Folder("root", null);
        currentFolder = root;
        ALL_FOLDERS = new HashMap<String, Folder>();
    }

    @Override
    public Object computeFirstHalfAnswer() {
        for (String line : this.input) {
            if (line.startsWith("$ cd")) {
                String targetName = line.replace("$ cd ", "");
                if (targetName.equals("/")) {
                    this.currentFolder = this.root;
                } else if (targetName.equals("..")) {
                    this.currentFolder = this.currentFolder.parent;
                } else {
                    Folder target = (Folder) this.currentFolder.content.get(targetName);
                    this.currentFolder = target != null ? target : root;

                }
            } else if (line.equals("$ ls")) {

            } else if (line.startsWith("dir")) {
                String dirName = line.replace("dir ", "");
                currentFolder.addFolder(dirName);
            } else {
                String[] values = line.split(" ");
                currentFolder.addFile(values[1], Integer.parseInt(values[0]));
            }
        }

        int total = 0;
        for (Folder folder : ALL_FOLDERS.values()) {
            int size = folder.getSize();
            if (size <= 100000) {
                total += size;
            }
        }
        return total;
    }

    @Override
    public Object computeSecondHalfAnswer() {
        int neededSize = 30000000 - (70000000 - this.root.getSize());
        int minSizeToDelete = Integer.MAX_VALUE;
        for (Folder folder : ALL_FOLDERS.values()) {
            int size = folder.getSize();
            if ((size >= neededSize) && (size <= minSizeToDelete)) {
                minSizeToDelete = size;
            }
        }
        return minSizeToDelete;
    }

}
