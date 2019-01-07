package fall2018.csc2017.slidingtiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fall2018.csc2017.R;

/**
 * Generates a shuffled board.
 */
class SlidingTilesBoardGenerator {

    /**
     * The total number of tiles.
     */
    private int numTiles;

    /**
     * the size of the board
     */
    private int boardSize = 4;

    /**
     * Generates a board with a predetermined number of moves
     */
    SlidingTilesBoard generateDeterminedBoard(ArrayList<Integer> moves) {
        this.numTiles = boardSize * boardSize;
        List<SlidingTilesTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTilesTile(tileNum));
        }
        tiles.set(numTiles - 1, new SlidingTilesTile(numTiles, R.drawable.tile_blank));
        SlidingTilesBoard board = new SlidingTilesBoard(tiles, boardSize);
        int blankIndex = numTiles - 1;
        for (int move : moves) {
            if (isValidSwap(blankIndex, move)) {
                swap(blankIndex, move, board);
                blankIndex = move;
            }
        }
        return board;
    }

    /**
     * Generates and returns a shuffled board.
     *
     * @return a shuffled board.
     */
    SlidingTilesBoard generateSolvableBoard(int boardSize) {
        this.boardSize = boardSize;
        this.numTiles = boardSize * boardSize;
        List<SlidingTilesTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTilesTile(tileNum));
        }
        tiles.set(numTiles - 1, new SlidingTilesTile(numTiles, R.drawable.tile_blank));
        SlidingTilesBoard board = new SlidingTilesBoard(tiles, boardSize);
        shuffleTiles(board);
        return board;
    }

    /**
     * Randomly shuffle the tiles of a solved board 500 times.
     *
     * @param board the tiles of a solved board
     */
    private void shuffleTiles(SlidingTilesBoard board) {
        int blankIndex = numTiles - 1;
        Random rand = new Random();
        int randomPosition;
        // Possible shift left, right, up, down
        int[] possibleMoves = {-1, 1, -board.getBoardSize(), board.getBoardSize()};
        for (int i = 0; i < 500; i++) {
            do {
                randomPosition = blankIndex + possibleMoves[rand.nextInt(4)];
            } while (!isValidSwap(blankIndex, randomPosition));
            swap(blankIndex, randomPosition, board);
            blankIndex = randomPosition;
        }
    }

    /**
     * Swap the tile at position with the blank tile.
     *
     * @param position   the position of the tile to be swapped
     * @param blankIndex the position of the blank tile
     * @param board      the board containing the tiles
     */
    private void swap(int blankIndex, int position, SlidingTilesBoard board) {
        int row = position / board.getBoardSize();
        int col = position % board.getBoardSize();
        int blankTileRow = blankIndex / board.getBoardSize();
        int blankTileCol = blankIndex % board.getBoardSize();
        board.swapTiles(row, col, blankTileRow, blankTileCol);
    }

    /**
     * Check if the swap is valid.
     *
     * @param blankIndex the position of the blank tile
     * @param position   the position of the tile to be swapped.
     * @return if the swap was valid.
     */
    private boolean isValidSwap(int blankIndex, int position) {
        int row = position / boardSize;
        int col = position % boardSize;
        int blankTileRow = blankIndex / boardSize;
        return position < numTiles && position >= 0 &&
                (row != blankTileRow + 1 || col != 0) &&
                (row != blankTileRow - 1 || col != boardSize - 1);
    }
}
