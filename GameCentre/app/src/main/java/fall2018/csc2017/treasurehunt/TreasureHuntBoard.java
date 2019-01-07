package fall2018.csc2017.treasurehunt;

import java.io.Serializable;
import java.util.Observable;

/**
 * A game board for the game Treasure Hunt.
 */
class TreasureHuntBoard extends Observable implements Serializable {

    /**
     * A 2D array that stores the tiles on the board, where tiles[i][j] refers to the tile at the
     * ith row and jth column of the board. The 2D array must be the same number of rows and
     * columns,
     */
    private TreasureHuntTile[][] tiles;

    /**
     * Initialize this board.
     *
     * @param tiles A 2D array that stores the tiles on the board, where tiles[i][j] refers to the
     *              tile at the ith row and jth column. The 2D array must have the same number of
     *              rows and columns.
     */
    TreasureHuntBoard(TreasureHuntTile[][] tiles) {
        this.tiles = tiles;
        revealSurrounding(getBoardSize() - 1, 0);
        tiles[0][getBoardSize() - 1].reveal();
    }

    /**
     * Set the background of the tiles around the tile at (row, col) to their type.
     *
     * @param row the row that a tile is in.
     * @param col the column that a tile is in.
     */
    void revealSurrounding(int row, int col) {
        int boardSize = tiles.length;
        for (int i = Math.max(row - 1, 0); i != Math.min(row + 2, boardSize); i++) {
            for (int j = Math.max(col - 1, 0); j != Math.min(col + 2, boardSize); j++) {
                tiles[i][j].reveal();
            }
        }
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     * If a tile with type TREASURE is swapped, set its type and background to BLANK.
     *
     * @param row1 the first tile row.
     * @param col1 the first tile col.
     * @param row2 the second tile row.
     * @param col2 the second tile col.
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        TreasureHuntTile temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;
        if (tiles[row2][col2].getType() == TreasureHuntTile.TREASURE) {
            tiles[row2][col2].setType(TreasureHuntTile.BLANK);
            tiles[row2][col2].reveal();
        }
        if (tiles[row1][col1].getType() == TreasureHuntTile.TREASURE) {
            tiles[row1][col1].setType(TreasureHuntTile.BLANK);
            tiles[row1][col1].reveal();
        }
        setChanged();
        notifyObservers("swapped tiles");
    }

    /**
     * Return an array that stores the person's position. The 0th index and 1st index are the row
     * and the col that the person is in respectively.
     *
     * @return an array that stores the person's position. The 0th index and 1st index are the row
     * and the col that the person is in respectively.
     */
    int[] getPersonPosition() {
        for (int row = 0; row != this.getBoardSize(); row++) {
            for (int col = 0; col != this.getBoardSize(); col++) {
                if (getTile(row, col).getType() == TreasureHuntTile.PERSON) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    /**
     * Return the tile at (row, col).
     *
     * @param row the row that the tile is in.
     * @param col the column that the tile is in.
     * @return the tile at (row, col).
     */
    TreasureHuntTile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return the size of this board.
     *
     * @return the size of this board.
     */
    int getBoardSize() {
        return tiles.length;
    }
}
