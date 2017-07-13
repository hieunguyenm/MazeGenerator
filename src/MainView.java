import processing.core.PApplet;

public class MainView extends PApplet {
    private DialogMenu menu = new DialogMenu();
    private MazeGenerator maze = null;

    public static void main(String[] args) { PApplet.main("MainView"); }

    public void settings() {
        size(Canvas.SCREEN_SIZE, Canvas.SCREEN_SIZE);
    }

    public void setup() {
        background(0);
        ellipseMode(CORNER);
        menu.showMenu();
        Canvas.updateCellValues();
        maze = new MazeGenerator(this, menu.getMazeType(), menu.getDisplayType(), menu.getPresetCellOption());
    }

    public void draw() {
            maze.draw();
    }
}