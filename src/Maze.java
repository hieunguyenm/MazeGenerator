import processing.core.PApplet;

import javax.swing.JOptionPane;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Maze extends PApplet {
    private final String[] DISPLAY_TYPE_BUTTONS = {"Animate", "Generate"};
    private final Integer[] PRESET_CELLS_ARRAY = {10, 40, 80, 160};
    private final int SCREEN_PADDING = 20;
    private final int SCREEN_SIZE = 960 + SCREEN_PADDING * 2;
    private final int ANIMATE = 0;
    private final int GENERATE = 1;

    private ArrayList<Point> frontierList = new ArrayList<>();
    private boolean[][] grid;
    private int totalCellsPerAxis = 0;
    private float cellWidth = 0;
    private int displayType = 0;
    private int presetCellOption = 0;

    public static void main(String[] args) {
        PApplet.main("Maze");
    }

    public void settings() {
        size(SCREEN_SIZE, SCREEN_SIZE);
    }

    public void setup() {
        getDisplayType();
        getCellOption();
        totalCellsPerAxis = 2 * PRESET_CELLS_ARRAY[presetCellOption] - 1;
        cellWidth = (SCREEN_SIZE - SCREEN_PADDING * 2) / totalCellsPerAxis;
        grid = new boolean[totalCellsPerAxis][totalCellsPerAxis];

        frameRate(PRESET_CELLS_ARRAY[presetCellOption] * 2);
        background(0);
        noStroke();
        // fillCheckeredGrid();
        // fillIsolatedGrid();

        // Create initial starting point and frontiers
        int randomX = ThreadLocalRandom.current().nextInt(PRESET_CELLS_ARRAY[presetCellOption]);
        int randomY = ThreadLocalRandom.current().nextInt(PRESET_CELLS_ARRAY[presetCellOption]);
        grid[randomX][randomY] = true;
        addPossibleFrontiers(randomX, randomY);

        if (displayType == GENERATE) {
            generatePrimMaze();
        }
    }

    public void draw() {
        Point frontier = null;
        if (displayType == ANIMATE) {
            background(0);
            if (!frontierList.isEmpty()) {
                frontier = getNextFrontier();
            }
        }

        fill(255);
        for (int x = 0; x < totalCellsPerAxis; x++) {
            for (int y = 0; y < totalCellsPerAxis; y++) {
                if (grid[x][y]) {
                    rect(SCREEN_PADDING + x * cellWidth, SCREEN_PADDING + y * cellWidth, cellWidth, cellWidth);
                }
            }
        }

        if (frontier != null) {
            int x = (int) frontier.getX();
            int y = (int) frontier.getY();
            fill(255, 0, 0);
            rect(SCREEN_PADDING + x * cellWidth, SCREEN_PADDING + y * cellWidth, cellWidth, cellWidth);
        }
    }

    private void generatePrimMaze() {
        while (!frontierList.isEmpty()) {
            getNextFrontier();
        }
    }

    private Point getNextFrontier() {
        Point frontier = frontierList.get(ThreadLocalRandom.current().nextInt(frontierList.size()));
        ArrayList<Point> origins = getPossibleOrigins(frontier);
        Point randomOrigin = origins.get(ThreadLocalRandom.current().nextInt(origins.size()));
        addFrontierToMaze(frontier, randomOrigin);
        addPossibleFrontiers((int) frontier.getX(), (int) frontier.getY());
        return frontier;
    }

    private void addPossibleFrontiers(int x, int y) {
        // Check cell above
        try {
            if (!grid[x][y - 2] && !isDuplicate(x, y - 2)) {
                frontierList.add(new Point(x, y - 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        //Check right cell
        try {
            if (!grid[x + 2][y] && !isDuplicate(x + 2, y)) {
                frontierList.add(new Point(x + 2, y));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        // Check wall cell
        try {
            if (!grid[x][y + 2] && !isDuplicate(x, y + 2)) {
                frontierList.add(new Point(x, y + 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        // Check left cell
        try {
            if (!grid[x - 2][y] && !isDuplicate(x - 2, y)) {
                frontierList.add(new Point(x - 2, y));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }

    private void addFrontierToMaze(Point frontier, Point origin) {
        grid[(int) frontier.getX()][(int) frontier.getY()] = true;
        frontierList.remove(frontier);

        // Remove wall between origin and frontier
        int dx = (int) (origin.getX() - frontier.getX());
        int dy = (int) (origin.getY() - frontier.getY());
        grid[(int) origin.getX() + dx / -2][(int) origin.getY() + dy / -2] = true;
    }

    private ArrayList<Point> getPossibleOrigins(Point frontier) {
        ArrayList<Point> originList = new ArrayList<>();
        int x = (int) frontier.getX();
        int y = (int) frontier.getY();

        try {
            if (grid[x][y - 2]) {
                originList.add(new Point(x, y - 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (grid[x + 2][y]) {
                originList.add(new Point(x + 2, y));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (grid[x][y + 2]) {
                originList.add(new Point(x, y + 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (grid[x - 2][y]) {
                originList.add(new Point(x - 2, y));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        return originList;
    }

    private boolean isDuplicate(int x, int y) {
        for (Point frontier : frontierList) {
            if ((int) frontier.getX() == x && (int) frontier.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private void getDisplayType() {
        displayType = JOptionPane.showOptionDialog(null, "Select display type", "Select display type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, DISPLAY_TYPE_BUTTONS, DISPLAY_TYPE_BUTTONS[0]);
        if (displayType == -1) {
            System.exit(0);
        }
    }

    private void getCellOption() {
        presetCellOption = JOptionPane.showOptionDialog(null, "Select dimension", "Select dimension",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, PRESET_CELLS_ARRAY, PRESET_CELLS_ARRAY[0]);
        if (presetCellOption == -1) {
            System.exit(0);
        }
    }


    // Sample function for generation of checkered grid
    private void fillCheckeredGrid() {
        boolean previousX = true;
        boolean current = true;
        for (int x = 0; x < totalCellsPerAxis; x++) {
            for (int y = 0; y < totalCellsPerAxis; y++) {
                grid[x][y] = current;
                current = !current;
            }
            current = !previousX;
            previousX = !previousX;
        }
    }

    // Sample function for generation of isolated cells in a grid
    private void fillIsolatedGrid() {
        for (int x = 0; x < totalCellsPerAxis; x += 2) {
            for (int y = 0; y < totalCellsPerAxis; y += 2) {
                grid[x][y] = true;
            }
        }
    }
}