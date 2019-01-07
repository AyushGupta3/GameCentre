package fall2018.csc2017.treasurehunt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreasureHuntBoardTest {

    /**
     * Return a 4x4 board with person at the bottom left corner and treasure at the upper right
     * corner. In this method, only the TreasureHuntBoard's constructor is called to create the
     * board, and no other method in TreasureHuntBoard is called to alter the board.
     *
     * @return a 4x4 board with person at the bottom left corner and treasure at the upper right
     * corner.
     */
    private TreasureHuntBoard generateBoard() {
        TreasureHuntTile[][] tiles = new TreasureHuntTile[4][4];
        for (int i = 0; i != 4; i++) {
            for (int j = 0; j != 4; j++) {
                if (i == 3 && j == 0) {
                    tiles[i][j] = new TreasureHuntTile(TreasureHuntTile.PERSON);
                } else if (i == 0 && j == 3) {
                    tiles[i][j] = new TreasureHuntTile(TreasureHuntTile.TREASURE);
                } else {
                    tiles[i][j] = new TreasureHuntTile(TreasureHuntTile.BLANK);
                }
            }
        }
        return new TreasureHuntBoard(tiles);
    }

    /**
     * Test whether or not the constructor of the TreasureHuntBoard correctly initializes the board.
     */
    @Test
    public void testBoardConstructor() {
        TreasureHuntBoard board = generateBoard();
        // check that the background and the type of each tile in the board are initialized
        // correctly.
        for (int i = 0; i != 4; i++) {
            for (int j = 0; j != 4; j++) {
                if (i == 3 && j == 0) {
                    assertEquals(board.getTile(i, j).getBackground(), TreasureHuntTile.PERSON);
                    assertEquals(board.getTile(i, j).getType(), TreasureHuntTile.PERSON);
                } else if (i >= 2 && j <= 1) {
                    assertEquals(board.getTile(i, j).getBackground(), TreasureHuntTile.BLANK);
                    assertEquals(board.getTile(i, j).getType(), TreasureHuntTile.BLANK);
                } else if (i == 0 && j == 3) {
                    assertEquals(board.getTile(i, j).getBackground(), TreasureHuntTile.TREASURE);
                    assertEquals(board.getTile(i, j).getType(), TreasureHuntTile.TREASURE);
                } else {
                    assertEquals(board.getTile(i, j).getBackground(), TreasureHuntTile.BLACK);
                    assertEquals(board.getTile(i, j).getType(), TreasureHuntTile.BLANK);
                }
            }
        }
    }

    /**
     * Test whether or not the method swapTiles in the TreasureHuntBoard correctly swaps the tiles.
     */
    @Test
    public void testSwapTiles() {
        TreasureHuntBoard board = generateBoard();
        TreasureHuntTile tile1 = board.getTile(1, 2);
        TreasureHuntTile tile2 = board.getTile(0, 3);
        assertEquals(board.getTile(1, 2), tile1);
        assertEquals(board.getTile(0, 3), tile2);
        assertEquals(tile2.getBackground(), TreasureHuntTile.TREASURE);
        board.swapTiles(1, 2, 0, 3);
        assertEquals(board.getTile(1, 2), tile2);
        assertEquals(board.getTile(0, 3), tile1);
        assertEquals(tile2.getBackground(), TreasureHuntTile.BLANK);

        TreasureHuntBoard board2 = generateBoard();
        TreasureHuntTile tile3 = board2.getTile(0, 3);
        assertEquals(tile3.getBackground(), TreasureHuntTile.TREASURE);
        board2.swapTiles(0, 3, 2, 2);
        assertEquals(tile3.getBackground(), TreasureHuntTile.BLANK);
    }

    /**
     * Test whether or not the method getBoardSize in the TreasureHuntBoard correctly returns the size
     * of the board.
     */
    @Test
    public void testGetSize() {
        TreasureHuntBoard board = generateBoard();
        assertEquals(board.getBoardSize(), 4);
    }

    /**
     * Test whether or not the method getPersonPosition in the TreasureHuntBoard correctly returns
     * the position of the person on the board.
     */
    @Test
    public void testGetPersonPosition() {
        TreasureHuntBoard board = generateBoard();
        int[] position1 = board.getPersonPosition();
        assertEquals(3, position1[0]);
        assertEquals(0, position1[1]);
        board.swapTiles(3, 0, 1, 2);
        int[] position2 = board.getPersonPosition();
        assertEquals(1, position2[0]);
        assertEquals(2, position2[1]);

        // test the scenario where no person is in the board.
        TreasureHuntTile[][] tiles = new TreasureHuntTile[2][2];
        for (int i = 0; i != 2; i++)
            for (int j = 0; j != 2; j++)
                tiles[i][j] = new TreasureHuntTile(TreasureHuntTile.BLANK);
        TreasureHuntBoard board2 = new TreasureHuntBoard(tiles);
        assertEquals(null, board2.getPersonPosition());
    }

    /**
     * Test whether or not the method revealSurrounding in the TreasureHuntBoard correctly sets
     * the background of tiles around a location to their color.
     */
    @Test
    public void testRevealSurrounding() {
        TreasureHuntBoard board = generateBoard();
        for (int i = 2; i != 4; i++) {
            for (int j = 2; j != 4; j++) {
                assertEquals(board.getTile(i, j).getBackground(), TreasureHuntTile.BLACK);
            }
        }
        board.revealSurrounding(3, 3);
        for (int i = 2; i != 4; i++) {
            for (int j = 2; j != 4; j++) {
                assertEquals(board.getTile(i, j).getBackground(), TreasureHuntTile.BLANK);
            }
        }
    }
}
