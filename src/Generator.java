import processing.core.PApplet;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    private int displayType;
    private int presetCellOption;
    private boolean[][] grid;
    private PApplet parent;
    private Frontier frontierObject;
    private Point frontier = null;

    public Generator(PApplet parent, int displayType, int presetCellOption) {
        this.parent = parent;
        this.displayType = displayType;
        this.presetCellOption = presetCellOption;
        setup();
    }

    private void setup() {
        parent.frameRate(Canvas.PRESET_CELLS_ARRAY[presetCellOption] * 2);

        grid = new boolean[Canvas.getTotalCellsPerAxis()][Canvas.getTotalCellsPerAxis()];
        frontierObject = new Frontier(grid);

        int randomX = ThreadLocalRandom.current().nextInt(Canvas.PRESET_CELLS_ARRAY[presetCellOption]);
        int randomY = ThreadLocalRandom.current().nextInt(Canvas.PRESET_CELLS_ARRAY[presetCellOption]);
        grid[randomX][randomY] = true;

        frontierObject.addPossibleFrontiers(randomX, randomY);

        if (displayType == DialogMenu.GENERATE) {
            generateMaze();
        }
    }

    public void draw() {
        parent.background(0);

        if (!frontierObject.isFrontierListEmpty()) {
            frontier = frontierObject.getNextFrontier();
        }

        parent.noStroke();
        parent.fill(255);
        for (int x = 0; x < Canvas.getTotalCellsPerAxis(); x++) {
            for (int y = 0; y < Canvas.getTotalCellsPerAxis(); y++) {
                if (grid[x][y]) {
                    parent.rect(Canvas.SCREEN_PADDING + x * Canvas.getCellWidth(), Canvas.SCREEN_PADDING + y * Canvas.getCellWidth(),
                            Canvas.getCellWidth(), Canvas.getCellWidth());
                }
            }
        }

        // Draw red highlight over current frontier
        if (frontier != null) {
            int x = (int) frontier.getX();
            int y = (int) frontier.getY();
            parent.fill(255, 0, 0);
            parent.rect(Canvas.SCREEN_PADDING + x * Canvas.getCellWidth(), Canvas.SCREEN_PADDING + y * Canvas.getCellWidth(),
                    Canvas.getCellWidth(), Canvas.getCellWidth());
        }
    }

    private void generateMaze() {
        while (!frontierObject.isFrontierListEmpty()) {
            frontierObject.getNextFrontier();
        }
    }
}