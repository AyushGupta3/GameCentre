package fall2018.csc2017.memorymatrix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;


import fall2018.csc2017.BoardGameUtilities.BoardManager;


/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class MemoryMatrixBoardManager extends Observable implements Serializable, BoardManager {

    /**
     * The board being managed.
     */
    private MemoryMatrixBoard board;

    /**
     * The current score of the game.
     */
    private MemoryMatrixScore score;

    /**
     * Return the current board.
     */
    MemoryMatrixBoard getBoard() {
        return board;
    }

    /**
     * Create a new MemoryMatrixBoardManager with board
     */
    MemoryMatrixBoardManager(MemoryMatrixBoard board) {
        this.board = board;
        score = new MemoryMatrixScore("testName");
    }

    /**
     * Manage a new board which is given an array of blank tiles and shuffled.
     */
    MemoryMatrixBoardManager(int boardSize) {
        List<MemoryMatrixTile> solvedTiles = new ArrayList<>();
        List<MemoryMatrixTile> blankTiles = new ArrayList<>();
        float numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != Math.ceil(numTiles / 2); tileNum++) {
            solvedTiles.add(new MemoryMatrixTile(MemoryMatrixTile.GRAY));
            blankTiles.add(new MemoryMatrixTile(MemoryMatrixTile.GRAY));
        }
        for (int tileNum = 0; tileNum != Math.floor(numTiles / 2); tileNum++) {
            solvedTiles.add(new MemoryMatrixTile(MemoryMatrixTile.BLUE));
            blankTiles.add(new MemoryMatrixTile(MemoryMatrixTile.GRAY));
        }

        Collections.shuffle(solvedTiles);
        this.board = new MemoryMatrixBoard(solvedTiles, blankTiles, boardSize);
        score = new MemoryMatrixScore();

    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;
        int boardSize = board.getBoardSize();
        MemoryMatrixTile[][] complete = board.getFinished();
        for (int row = 0; row != boardSize; row++) {
            for (int col = 0; col != boardSize; col++)
                if (complete[row][col].getBackground() != board.getTile(row, col).getBackground()
                        && complete[row][col].getBackground() == MemoryMatrixTile.BLUE) {
                    solved = false;
                }
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
        int boardSize = board.getBoardSize();
        int row = position / boardSize;
        int col = position % boardSize;
        return board.getTile(row, col).getBackground() == MemoryMatrixTile.GRAY;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        int boardSize = board.getBoardSize();
        int row = position / boardSize;
        int col = position % boardSize;
        MemoryMatrixTile[][] complete = board.getFinished();
        if (complete[row][col].getBackground() == MemoryMatrixTile.BLUE && isValidTap(position)) {
            board.swapTileImage(position, true);
        } else if (isValidTap(position)) {
            board.swapTileImage(position, false);
            score.countMove();
            setChanged();
            notifyObservers();
        }

    }

    /**
     * Return this board manager's score object
     *
     * @return the score object
     */
    public MemoryMatrixScore getScore() {
        return score;
    }
}