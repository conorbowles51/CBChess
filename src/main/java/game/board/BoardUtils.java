package game.board;

public class BoardUtils {
    public static final boolean[] A_FILE = initFile(0);
    public static final boolean[] B_FILE = initFile(1);
    public static final boolean[] G_FILE = initFile(6);
    public static final boolean[] H_FILE = initFile(7);

    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] SEVENTH_ROW = initRow(48);

    public static final int NUM_TILES = 64;
    public static final int SIZE = 8;

    public static boolean isValidIndex(final int tileIndex) {
        return tileIndex >= 0 && tileIndex < 64;
    }

    private static boolean[] initFile(final int columnNumber){
        final boolean[] column = new boolean[NUM_TILES];

        for(int i = 0; i < NUM_TILES; i++){
            column[i] = i % SIZE == columnNumber;
        }

        return column;
    }

    private static boolean[] initRow(final int rowStart){
        final boolean[] row = new boolean[NUM_TILES];

        for(int i = 0; i < NUM_TILES; i++){
            row[i] = i >= rowStart && i < rowStart + SIZE;
        }

        return row;
    }
}
