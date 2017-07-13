import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Frontier {
    private ArrayList<Point> frontierList = new ArrayList<>();
    private boolean[][] grid;

    public Frontier(boolean[][] grid) {
        this.grid = grid;
    }

    public Point getNextFrontier(int mazeType) {
        Point frontier = frontierList.get(ThreadLocalRandom.current().nextInt(frontierList.size()));

        if (mazeType == MazeGenerator.CLASSIC) {
            ArrayList<Point> origins = getPossibleOrigins(frontier);
            Point randomOrigin = origins.get(ThreadLocalRandom.current().nextInt(origins.size()));
            addFrontierToMaze(frontier, randomOrigin);
            addPossibleFrontiers((int) frontier.getX(), (int) frontier.getY());
        } else if (mazeType == MazeGenerator.DIAGONAL) {
            ArrayList<Point> origins = getPossibleDiagonalOrigins(frontier);
            Point randomOrigin = origins.get(ThreadLocalRandom.current().nextInt(origins.size()));
            addFrontierToMaze(frontier, randomOrigin);
            addPossibleDiagonalFrontiers((int) frontier.getX(), (int) frontier.getY());
        }
        return frontier;
    }

    public void addPossibleDiagonalFrontiers(int x, int y) {
        addPossibleFrontiers(x, y);

        // Check top left cell
        try {
            if (!grid[x - 2][y - 2] && !isDuplicate(x - 2, y - 2)) {
                frontierList.add(new Point(x - 2, y - 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        // Check top right cell
        try {
            if (!grid[x + 2][y - 2] && !isDuplicate(x + 2, y - 2)) {
                frontierList.add(new Point(x + 2, y - 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        // Check bottom left cell
        try {
            if (!grid[x - 2][y + 2] && !isDuplicate(x - 2, y + 2)) {
                frontierList.add(new Point(x - 2, y + 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        // Check bottom right cell
        try {
            if (!grid[x + 2][y + 2] && !isDuplicate(x + 2, y + 2)) {
                frontierList.add(new Point(x + 2, y + 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }

    public void addPossibleFrontiers(int x, int y) {
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

    private ArrayList<Point> getPossibleDiagonalOrigins(Point frontier) {
        ArrayList<Point> originList = new ArrayList<>();
        int x = (int) frontier.getX();
        int y = (int) frontier.getY();
        originList.addAll(getPossibleOrigins(frontier));

        // Try top left
        try {
            if (grid[x - 2][y - 2]) {
                originList.add(new Point(x - 2, y - 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        // Try top right
        try {
            if (grid[x + 2][y - 2]) {
                originList.add(new Point(x + 2, y - 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        // Try bottom left
        try {
            if (grid[x - 2][y + 2]) {
                originList.add(new Point(x - 2, y + 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        // Try bottom right
        try {
            if (grid[x + 2][y + 2]) {
                originList.add(new Point(x + 2, y + 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) {}

        return originList;
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

    public boolean isFrontierListEmpty() {
        return frontierList.isEmpty();
    }
}
