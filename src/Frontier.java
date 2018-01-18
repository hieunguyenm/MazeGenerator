import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Frontier {
    private ArrayList<Point> frontierList = new ArrayList<>();
    private boolean[][] grid;

    public Frontier(boolean[][] grid) {
        this.grid = grid;
    }

    public Point getNextFrontier() {
        Point frontier = frontierList.get(ThreadLocalRandom.current().nextInt(frontierList.size()));
        ArrayList<Point> origins = getPossibleOrigins(frontier);
        Point randomOrigin = origins.get(ThreadLocalRandom.current().nextInt(origins.size()));
        addFrontierToMaze(frontier, randomOrigin);
        addPossibleFrontiers((int) frontier.getX(), (int) frontier.getY());
        return frontier;
    }

    public void addPossibleFrontiers(int x, int y) {
        // Check cell above
        checkAndAdd(x, y - 2);

        // Check right cell
        checkAndAdd(x + 2, y);

        // Check cell below
        checkAndAdd(x, y + 2);

        // Check left cell
        checkAndAdd(x - 2, y);
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

        if (checkOrigin(x, y - 2)) {
            originList.add(new Point(x, y - 2));
        }

        if (checkOrigin(x + 2, y)) {
            originList.add(new Point(x + 2, y));
        }

        if (checkOrigin(x, y + 2)) {
            originList.add(new Point(x, y + 2));
        }

        if (checkOrigin(x - 2, y)) {
            originList.add(new Point(x - 2, y));
        }

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

    private boolean checkOrigin(int x, int y) {
        try {
            if (grid[x][y]) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) { }
        return false;
    }

    public boolean isFrontierListEmpty() {
        return frontierList.isEmpty();
    }

    private void checkAndAdd(int x, int y) {
        try {
            if (!grid[x][y] && !isDuplicate(x, y)) {
                frontierList.add(new Point(x, y));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
