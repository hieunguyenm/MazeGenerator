import javax.swing.JOptionPane;

public class DialogMenu {
    public static final int ANIMATE = 0;
    public static final int GENERATE = 1;
    private final String[] MAZE_TYPE_BUTTONS = {"Classic", "Diagonal"};
    private final String[] DISPLAY_TYPE_BUTTONS = {"Animate", "Generate"};
    private final int X_BUTTON = -1;

    private static int mazeType = 0;
    private static int displayType = 0;
    private static int presetCellOption = 0;

    public DialogMenu() {}

    public void showMenu() {
        askMazeType();
        askDisplayType();
        askCellOption();
    }

    private void askMazeType() {
        mazeType = JOptionPane.showOptionDialog(null, "Select maze type", "Select maze type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, MAZE_TYPE_BUTTONS, MAZE_TYPE_BUTTONS[0]);
        if (mazeType == X_BUTTON) {
            System.exit(0);
        }
    }

    private void askDisplayType() {
        displayType = JOptionPane.showOptionDialog(null, "Select display type", "Select display type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, DISPLAY_TYPE_BUTTONS, DISPLAY_TYPE_BUTTONS[0]);
        if (displayType == X_BUTTON) {
            System.exit(0);
        }
    }

    private void askCellOption() {
        presetCellOption = JOptionPane.showOptionDialog(null, "Select dimension", "Select dimension",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, Canvas.PRESET_CELLS_ARRAY,
                Canvas.PRESET_CELLS_ARRAY[0]);
        if (presetCellOption == X_BUTTON) {
            System.exit(0);
        }
    }

    public static int getMazeType() {
        return mazeType;
    }

    public static int getDisplayType() {
        return displayType;
    }

    public static int getPresetCellOption() {
        return presetCellOption;
    }
}
