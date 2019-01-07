package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class SlidingTilesBoard extends Observable implements Serializable, Iterable<SlidingTilesTile> {

    /**
     * The size of the board
     */
    private int boardSize = 4;

    /**
     * The tiles on the board in row-major order.
     */
    private SlidingTilesTile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the board
     */
    SlidingTilesBoard(List<SlidingTilesTile> tiles, int boardSize) {
        this.boardSize = boardSize;
        this.tiles = new SlidingTilesTile[boardSize][boardSize];
        Iterator<SlidingTilesTile> iter = tiles.iterator();

        for (int row = 0; row != boardSize; row++) {
            for (int col = 0; col != boardSize; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return boardSize * boardSize;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    SlidingTilesTile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        SlidingTilesTile placeHolder = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = placeHolder;
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
     * Return a list of Integers containing the row and column of the tile with id tileID,
     * or -1 and -1 if there isn't one.
     *
     * @param tileID The id of the tile
     * @return the list containing the row and col, or -1 and -1
     */
    List<Integer> getTileLocationByID(int tileID) {
        List<Integer> result = new ArrayList<>();
        for (int row = 0; row != getBoardSize(); row++) {
            for (int col = 0; col != getBoardSize(); col++) {
                if (tiles[row][col].getId() == tileID) {
                    result.add(row);
                    result.add(col);
                    return result;
                }
            }
        }
        result.add(-1);
        result.add(-1);
        return result;
    }

    @Override
    @NonNull
    public Iterator<SlidingTilesTile> iterator() {
        return new BoardIterator();
    }

    /**
     * Iterator class for the board tiles.
     */
    private class BoardIterator implements Iterator<SlidingTilesTile> {
        /**
         * The index of the current row.
         */
        int rowIndex = 0;
        /**
         * The index of the current column.
         */
        int colIndex = 0;


        @Override
        public boolean hasNext() {
            return rowIndex != boardSize;
        }

        @Override
        public SlidingTilesTile next() {
            SlidingTilesTile currentTile = tiles[rowIndex][colIndex];
            // Checking to see if the last column is reached inorder to start over
            // on the second row
            if (colIndex == boardSize - 1) {
                rowIndex++;
                colIndex = 0;
            } else {
                colIndex++;
            }
            return currentTile;

        }
    }

    /**
     * return he size of the board
     *
     * @return the board size
     */
    public int getBoardSize() {
        return boardSize;
    }
}
