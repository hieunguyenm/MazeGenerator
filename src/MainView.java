import processing.core.PApplet;

public class MainView extends PApplet {
    private DialogMenu menu = new DialogMenu();
    private Generator maze = null;

    public static void main(String[] args) {
        PApplet.main("MainView");
    }

    public void settings() {
        size(Canvas.SCREEN_SIZE, Canvas.SCREEN_SIZE);
    }

    public void setup() {
        background(0);
        ellipseMode(CORNER);
        menu.showMenu();
        Canvas.updateCellValues();
        maze = new Generator(this, DialogMenu.getDisplayType(), DialogMenu.getPresetCellOption());
    }

    public void draw() {
        maze.draw();
    }
}