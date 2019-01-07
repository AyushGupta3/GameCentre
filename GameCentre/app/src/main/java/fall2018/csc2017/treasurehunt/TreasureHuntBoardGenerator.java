package fall2018.csc2017.treasurehunt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Generate a board for the game Treasure Hunt.
 */
class TreasureHuntBoardGenerator {

    /**
     * A tuple that represents the position of a tile on the board.
     */
    private static class Tuple {

        /**
         * The row that the tile is in.
         */
        final int row;
        /**
         * The column that the tile is in.
         */
        final int col;

        /**
         * Initializes the tile.
         *
         * @param row the row that the tie is in.
         * @param col the column that the tile is in.
         */
        Tuple(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple tuple = (Tuple) o;
            return row == tuple.row &&
                    col == tuple.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    /**
     * Returns whether or not the board can be solved, that is whether there is a path that connects
     * the bottom left tile to the upper right tile of the board.
     *
     * @param board the board of a Treasure Hunt game.
     * @return true if the board can be solved and false otherwise.
     */
    private static boolean solvable(TreasureHuntTile[][] board) {
        Set<Tuple> visited = new HashSet<>();
        List<Tuple> toVisit = new ArrayList<>();
        int size = board.length;
        toVisit.add(new Tuple(size - 1, 0));
        visited.add(new Tuple(size - 1, 0));
        while (toVisit.size() > 0) {
            Tuple next = toVisit.remove(toVisit.size() - 1);
            int row = next.row;
            int col = next.col;
            if (board[row][col].getType() == TreasureHuntTile.TREASURE)
                return true;
            if (row > 0 && !visited.contains(new Tuple(row - 1, col)) &&
                    board[row - 1][col].getType() != TreasureHuntTile.BRICK) {
                toVisit.add(new Tuple(row - 1, col));
                visited.add(new Tuple(row - 1, col));
            }
            if (row < size - 1 && !visited.contains(new Tuple(row + 1, col)) &&
                    board[row + 1][col].getType() != TreasureHuntTile.BRICK) {
                toVisit.add(new Tuple(row + 1, col));
                visited.add(new Tuple(row + 1, col));
            }
            if (col > 0 && !visited.contains(new Tuple(row, col - 1)) &&
                    board[row][col - 1].getType() != TreasureHuntTile.BRICK) {
                toVisit.add(new Tuple(row, col - 1));
                visited.add(new Tuple(row, col - 1));
            }
            if (col < size - 1 && !visited.contains(new Tuple(row, col + 1)) &&
                    board[row][col + 1].getType() != TreasureHuntTile.BRICK) {
                toVisit.add(new Tuple(row, col + 1));
                visited.add(new Tuple(row, col + 1));
            }
        }
        return false;
    }

    /**
     * Returns a board for the game Treasure Hunt.
     * The size of the board must be greater or equal to 2.
     *
     * @param size the size of the board.
     * @return a board for the game Treasure Hunt.
     */
    private static TreasureHuntTile[][] createNewBoard(int size) {
        TreasureHuntTile[][] board = new TreasureHuntTile[size][size];
        for (int i = 0; i != size; i++) {
            for (int j = 0; j != size; j++) {
                board[i][j] = new TreasureHuntTile(TreasureHuntTile.BLANK);
            }
        }
        board[size - 1][0].setType(TreasureHuntTile.PERSON);
        board[0][size - 1].setType(TreasureHuntTile.TREASURE);
        int numBricks = 2 * size * size / 5;
        for (int i = 0; i != numBricks; i++) {
            int row = 0;
            int col = 0;
            while (board[row][col].getType() != TreasureHuntTile.BLANK) {
                row = (int) (Math.random() * size);
                col = (int) (Math.random() * size);
            }
            board[row][col].setType(TreasureHuntTile.BRICK);
        }
        return board;
    }

    /**
     * Returns a solvable board for the game Treasure Hunt.
     * The size of the board must be greater or equal to 2.
     *
     * @param size the size of the board.
     * @return a solvable board for the game Treasure Hunt.
     */
    static TreasureHuntBoard generateSolvableBoard(int size) {
        TreasureHuntTile[][] board = createNewBoard(size);
        while (!solvable(board)) {
            board = createNewBoard(size);
        }
        return new TreasureHuntBoard(board);
    }
}
