public class Canvas {
    public static final int SCREEN_PADDING = 20;
    public static final int SCREEN_SIZE = 960 + SCREEN_PADDING * 2;
    public static final Integer[] PRESET_CELLS_ARRAY = {10, 40, 80, 160};
    public static final int DRAW_AREA = SCREEN_SIZE - SCREEN_PADDING * 2;

    private static int totalCellsPerAxis = 0;
    private static float cellWidth = 0;

    public static void updateCellValues() {
        totalCellsPerAxis = 2 * Canvas.PRESET_CELLS_ARRAY[DialogMenu.getPresetCellOption()] - 1;
        cellWidth = DRAW_AREA / totalCellsPerAxis;
    }

    public static int getTotalCellsPerAxis() {
        return totalCellsPerAxis;
    }

    public static float getCellWidth() {
        return cellWidth;
    }
}
