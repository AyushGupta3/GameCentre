package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;


import fall2018.csc2017.BoardGameUtilities.BoardManager;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SlidingTilesBoardManager implements Serializable, BoardManager {

    /**
     * The board being managed.
     */
    private SlidingTilesBoard board;

    /**
     * Stack of moves to be undone (contains only "left", "right", "above", "below")
     */
    private Stack<String> undoStack;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    SlidingTilesBoardManager(SlidingTilesBoard board) {
        this.board = board;
        undoStack = new Stack<>();
    }

    /**
     * Return the current board.
     */
    SlidingTilesBoard getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    SlidingTilesBoardManager(int boardSize) {
        this.board = new SlidingTilesBoardGenerator().generateSolvableBoard(boardSize);
        undoStack = new Stack<>();
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;

        SlidingTilesTile prevTile = board.getTile(0, 0);
        for (SlidingTilesTile t : board) {
            if (t.getId() < prevTile.getId()) {
                solved = false;
            }
            prevTile = t;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {
        int row = position / board.getBoardSize();
        int col = position % board.getBoardSize();
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        SlidingTilesTile above = row == 0 ? null : board.getTile(row - 1, col);
        SlidingTilesTile below = row == board.getBoardSize() - 1 ? null : board.getTile(row + 1, col);
        SlidingTilesTile left = col == 0 ? null : board.getTile(row, col - 1);
        SlidingTilesTile right = col == board.getBoardSize() - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        int row = position / board.getBoardSize();
        int col = position % board.getBoardSize();
        int blankId = board.numTiles();

        if (isValidTap(position)) {
            List<Integer> blankTileLocation = board.getTileLocationByID(blankId);
            int blankTileRow = blankTileLocation.get(0);
            int blankTileCol = blankTileLocation.get(1);
            if (row > blankTileRow) {
                undoStack.push("above");
            } else if (row < blankTileRow) {
                undoStack.push("below");
            } else if (col > blankTileCol) {
                undoStack.push("left");
            } else if (col < blankTileCol) {
                undoStack.push("right");
            }
            board.swapTiles(row, col, blankTileRow, blankTileCol);
        }

    }

    /**
     * Undo the last move performed.
     */
    void undo() {
        if (!undoStack.empty()) {
            int blankId = board.numTiles();
            List<Integer> blankTileLocation = board.getTileLocationByID(blankId);
            int blankTileRow = blankTileLocation.get(0);
            int blankTileCol = blankTileLocation.get(1);
            switch (undoStack.pop()) {
                case "left":
                    board.swapTiles(blankTileRow, blankTileCol, blankTileRow, blankTileCol - 1);
                    break;
                case "right":
                    board.swapTiles(blankTileRow, blankTileCol, blankTileRow, blankTileCol + 1);
                    break;
                case "above":
                    board.swapTiles(blankTileRow - 1, blankTileCol, blankTileRow, blankTileCol);
                    break;
                case "below":
                    board.swapTiles(blankTileRow + 1, blankTileCol, blankTileRow, blankTileCol);
                    break;
            }

        }

    }

    /**
     * Return this board manager's undo stack.
     *
     * @return this board manager's undo stack
     */
    Stack<String> getUndoStack() {
        return undoStack;
    }
}
