package fall2018.csc2017.treasurehunt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A set of tests for TreasureHuntBoardManager.
 */
public class TreasureHuntBoardManagerTest {

    /**
     * Return a 4x4 board with person at the bottom left corner and treasure at the upper right
     * corner. Also, a brick at (3,1) In this method, only the TreasureHuntBoard's constructor is
     * called to create the board, and no other method in TreasureHuntBoard is called to alter the
     * board.
     *
     * @return a 4x4 board with person at the bottom left corner and treasure at the upper right
     * corner, and a brick at (3, 1).
     */
    private TreasureHuntBoard generateBoard() {
        TreasureHuntTile[][] tiles = new TreasureHuntTile[4][4];
        for (int i = 0; i != 4; i++) {
            for (int j = 0; j != 4; j++) {
                if (i == 3 && j == 0) {
                    tiles[i][j] = new TreasureHuntTile(TreasureHuntTile.PERSON);
                } else if (i == 0 && j == 3) {
                    tiles[i][j] = new TreasureHuntTile(TreasureHuntTile.TREASURE);
                } else if (i == 3 && j == 1) {
                    tiles[i][j] = new TreasureHuntTile(TreasureHuntTile.BRICK);
                } else {
                    tiles[i][j] = new TreasureHuntTile(TreasureHuntTile.BLANK);
                }
            }
        }
        return new TreasureHuntBoard(tiles);
    }

    /**
     * Test the method isValidTap in TreasureHuntBoardManager.
     */
    @Test
    public void testIsValidTap() {
        TreasureHuntBoard board = generateBoard();
        TreasureHuntBoardManager boardManager = new TreasureHuntBoardManager(board);
        assertFalse(boardManager.isValidTap(13));
        assertTrue(boardManager.isValidTap(8));
    }

    /**
     * Test the methods touchMove and undo in TreasureHuntBoardManager.
     */
    @Test
    public void testTouchMoveAndUndo() {
        TreasureHuntBoard board = generateBoard();
        TreasureHuntBoardManager boardManager = new TreasureHuntBoardManager(board);

        //Test touchMove
        assertEquals(boardManager.getBoard().getTile(3, 0).getType(),
                TreasureHuntTile.PERSON);
        boardManager.touchMove(8);
        assertEquals(boardManager.getBoard().getTile(2, 0).getType(),
                TreasureHuntTile.PERSON);
        boardManager.touchMove(9);
        assertEquals(boardManager.getBoard().getTile(2, 1).getType(),
                TreasureHuntTile.PERSON);
        boardManager.touchMove(8);
        assertEquals(boardManager.getBoard().getTile(2, 0).getType(),
                TreasureHuntTile.PERSON);
        boardManager.touchMove(12);
        assertEquals(boardManager.getBoard().getTile(3, 0).getType(),
                TreasureHuntTile.PERSON);
        boardManager.touchMove(13);
        assertEquals(boardManager.getBoard().getTile(3, 0).getType(),
                TreasureHuntTile.PERSON);

        //Test undo
        assertFalse(boardManager.getUndoStack().empty());
        boardManager.undo();
        assertEquals(boardManager.getBoard().getTile(2, 0).getType(),
                TreasureHuntTile.PERSON);
        boardManager.undo();
        assertEquals(boardManager.getBoard().getTile(2, 1).getType(),
                TreasureHuntTile.PERSON);
        boardManager.undo();
        assertEquals(boardManager.getBoard().getTile(2, 0).getType(),
                TreasureHuntTile.PERSON);
        boardManager.undo();
        assertEquals(boardManager.getBoard().getTile(3, 0).getType(),
                TreasureHuntTile.PERSON);
        assertTrue(boardManager.getUndoStack().empty());
    }

    /**
     * Test the method puzzleSolved in TreasureHuntBoardManager.
     */
    @Test
    public void testPuzzleSolved() {
        //Test a board that is solved.
        TreasureHuntTile[][] tiles = new TreasureHuntTile[2][2];
        tiles[0][0] = new TreasureHuntTile(TreasureHuntTile.BLANK);
        tiles[0][1] = new TreasureHuntTile(TreasureHuntTile.PERSON);
        tiles[1][0] = new TreasureHuntTile(TreasureHuntTile.BLANK);
        tiles[1][1] = new TreasureHuntTile(TreasureHuntTile.BLANK);
        TreasureHuntBoard board = new TreasureHuntBoard(tiles);
        TreasureHuntBoardManager boardManager = new TreasureHuntBoardManager(board);
        assertTrue(boardManager.puzzleSolved());

        //Test a board that is not not solved
        TreasureHuntTile[][] tiles2 = new TreasureHuntTile[2][2];
        tiles2[0][0] = new TreasureHuntTile(TreasureHuntTile.BLANK);
        tiles2[0][1] = new TreasureHuntTile(TreasureHuntTile.BLANK);
        tiles2[1][0] = new TreasureHuntTile(TreasureHuntTile.PERSON);
        tiles2[1][1] = new TreasureHuntTile(TreasureHuntTile.BLANK);
        TreasureHuntBoard board2 = new TreasureHuntBoard(tiles2);
        TreasureHuntBoardManager boardManager2 = new TreasureHuntBoardManager(board2);
        assertFalse(boardManager2.puzzleSolved());

    }
}
