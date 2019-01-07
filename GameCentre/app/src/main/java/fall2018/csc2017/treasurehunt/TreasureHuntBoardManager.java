package fall2018.csc2017.treasurehunt;

import java.io.Serializable;
import java.util.Stack;

import fall2018.csc2017.BoardGameUtilities.BoardManager;

/**
 * Manage a Treasure Hunt board, including swapping tiles, checking for a win, and managing taps.
 */
class TreasureHuntBoardManager implements Serializable, BoardManager {

    /**
     * The board being managed
     */
    private TreasureHuntBoard board;

    /**
     * Stack containing history of moves
     */
    private Stack<String> undoStack;

    /**
     * Constructor for testing purposes
     *
     * @param board the board for the game Treasure Hunt.
     */
    TreasureHuntBoardManager(TreasureHuntBoard board) {
        this.board = board;
        this.undoStack = new Stack<>();
    }

    /**
     * Returns a boolean representing the completion of the game.
     *
     * @return whether the puzzle has been completed.
     */
    @Override
    public boolean puzzleSolved() {
        return board.getTile(0, board.getBoardSize() - 1).getType() == TreasureHuntTile.PERSON;
    }

    /**
     * Return whether the tile tapped at position is valid.
     *
     * @param position the position which was tapped.
     * @return whether the tile tapped at position is valid.
     */
    @Override
    public boolean isValidTap(int position) {
        int row = position / board.getBoardSize();
        int col = position % board.getBoardSize();
        if (board.getTile(row, col).getType() == TreasureHuntTile.BRICK)
            return false;
        TreasureHuntTile above = row == 0 ? null : board.getTile(row - 1, col);
        TreasureHuntTile below = row == board.getBoardSize() - 1 ? null : board.getTile(row + 1, col);
        TreasureHuntTile left = col == 0 ? null : board.getTile(row, col - 1);
        TreasureHuntTile right = col == board.getBoardSize() - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getType() == TreasureHuntTile.PERSON)
                || (above != null && above.getType() == TreasureHuntTile.PERSON)
                || (left != null && left.getType() == TreasureHuntTile.PERSON)
                || (right != null && right.getType() == TreasureHuntTile.PERSON);
    }

    /**
     * Process a touch that was made at position
     *
     * @param position the position which was tapped.
     */
    @Override
    public void touchMove(int position) {
        int row = position / board.getBoardSize();
        int col = position % board.getBoardSize();
        if (isValidTap(position)) {
            int personRow;
            int personCol;
            if (row > 0 && board.getTile(row - 1, col).getType() == TreasureHuntTile.PERSON) {
                personRow = row - 1;
                personCol = col;
            } else if (row < board.getBoardSize() - 1 &&
                    board.getTile(row + 1, col).getType() == TreasureHuntTile.PERSON) {
                personRow = row + 1;
                personCol = col;
            } else if (col > 0 && board.getTile(row, col - 1).getType() == TreasureHuntTile.PERSON) {
                personRow = row;
                personCol = col - 1;
            } else {
                personRow = row;
                personCol = col + 1;
            }
            if (row > personRow) {
                undoStack.push("above");
            } else if (row < personRow) {
                undoStack.push("below");
            } else if (col > personCol) {
                undoStack.push("left");
            } else if (col < personCol) {
                undoStack.push("right");
            }
            board.revealSurrounding(row, col);
            board.swapTiles(row, col, personRow, personCol);
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

    /**
     * Return the current board.
     */
    TreasureHuntBoard getBoard() {
        return board;
    }

    /**
     * Undo the last move performed
     */
    void undo() {
        if (!undoStack.empty()) {
            int[] position = board.getPersonPosition();
            int personRow = position[0];
            int personCol = position[1];
            switch (undoStack.pop()) {
                case "left":
                    board.swapTiles(personRow, personCol, personRow, personCol - 1);
                    break;
                case "right":
                    board.swapTiles(personRow, personCol, personRow, personCol + 1);
                    break;
                case "above":
                    board.swapTiles(personRow - 1, personCol, personRow, personCol);
                    break;
                case "below":
                    board.swapTiles(personRow + 1, personCol, personRow, personCol);
                    break;
            }
        }
    }
}
