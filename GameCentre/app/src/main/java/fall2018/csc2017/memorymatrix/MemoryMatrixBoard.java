package fall2018.csc2017.memorymatrix;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The memory matrix board.
 */
public class MemoryMatrixBoard extends Observable implements Serializable {

    /**
     * The number of rows and cols in the board.
     */
    private int boardSize = 6;

    /**
     * The list of unsolved tiles on the board in row-major order.
     */
    private MemoryMatrixTile[][] tiles;

    /**
     * The list of solved tiles
     */
    private MemoryMatrixTile[][] solvedTiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     */
    MemoryMatrixBoard(List<MemoryMatrixTile> solutionTiles, List<MemoryMatrixTile> blankTiles, int boardSize) {
        setBoardSize(boardSize);
        tiles = new MemoryMatrixTile[boardSize][boardSize];
        solvedTiles = new MemoryMatrixTile[boardSize][boardSize];
        Iterator<MemoryMatrixTile> iter = solutionTiles.iterator();
        Iterator<MemoryMatrixTile> blankIter = blankTiles.iterator();

        for (int row = 0; row != boardSize; row++) {
            for (int col = 0; col != boardSize; col++) {
                this.tiles[row][col] = blankIter.next();
                this.solvedTiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Swaps the two arrays for when we want to display the solution.
     */
    void swapBoardsForDisplay() {
        MemoryMatrixTile[][] temp = tiles;
        tiles = solvedTiles;
        solvedTiles = temp;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    MemoryMatrixTile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tile images based on whether it was a correct move or not.
     */
    void swapTileImage(int position, boolean isCorrect) {
        int row = position / boardSize;
        int col = position % boardSize;
        if (isCorrect) {
            tiles[row][col].setBackground(MemoryMatrixTile.BLUE);
        } else {
            tiles[row][col].setBackground(MemoryMatrixTile.RED);
        }

        setChanged();
        notifyObservers("swapped tiles");
    }

    @Override
    public String toString() {
        return "SlidingTilesBoard{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Retrieve the solvedTiles
     *
     * @return the solvedTiles
     */
    MemoryMatrixTile[][] getFinished() {
        return solvedTiles;
    }


    /**
     * Retrieve the boardSize
     *
     * @return the boardSize
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Set the current boardSize
     *
     * @param boardSize the boardSize
     */
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
}
