package fall2018.csc2017.memorymatrix;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MemoryMatrixBoardAndTileTest {

    /**
     * The board manager for testing.
     */
    private MemoryMatrixBoardManager boardManager;

    /**
     * Board Size being tested
     */
    private final int BOARDSIZE = 6;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<MemoryMatrixTile> makeBlankTiles() {
        List<MemoryMatrixTile> blankTiles = new ArrayList<>();
        float numTiles = BOARDSIZE * BOARDSIZE;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            blankTiles.add(new MemoryMatrixTile(MemoryMatrixTile.GRAY));
        }
        return blankTiles;
    }

    /**
     * Helper method to create a list of complete tiles.
     *
     * @return the complete tiles
     */
    private List<MemoryMatrixTile> makeCompleteTiles() {
        List<MemoryMatrixTile> solvedTiles = new ArrayList<>();
        float numTiles = BOARDSIZE * BOARDSIZE;
        for (int tileNum = 0; tileNum != Math.ceil(numTiles / 2); tileNum++) {
            solvedTiles.add(new MemoryMatrixTile(MemoryMatrixTile.GRAY));
        }
        for (int tileNum = 0; tileNum != Math.floor(numTiles / 2); tileNum++) {
            solvedTiles.add(new MemoryMatrixTile(MemoryMatrixTile.BLUE));
        }
        return solvedTiles;
    }

    /**
     * Make a solved SlidingTilesBoard.
     */
    private void setUpCorrect() {
        //Both Lists are equal in a solved board
        List<MemoryMatrixTile> solutionTiles = makeCompleteTiles();
        List<MemoryMatrixTile> userUnsolvedTiles = makeCompleteTiles();
        MemoryMatrixBoard board = new MemoryMatrixBoard(solutionTiles, userUnsolvedTiles, BOARDSIZE);
        boardManager = new MemoryMatrixBoardManager(board);
    }

    /**
     * Tap First Two Tiles
     */
    private void TapFirstTwoTiles() {
        boardManager.touchMove(0);
        boardManager.touchMove(1);
    }

    /**
     * Test whether tapping the first two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());
        TapFirstTwoTiles();
        assertTrue(boardManager.puzzleSolved());
    }

    /**
     * Test whether tapping the first two tiles works.
     */
    @Test
    public void testSwapTileImageFirstTwo() {
        setUpCorrect();
        assertEquals(MemoryMatrixTile.GRAY, boardManager.getBoard().getTile(0, 0).getBackground());
        assertEquals(MemoryMatrixTile.GRAY, boardManager.getBoard().getTile(0, 1).getBackground());
        boardManager.getBoard().swapTileImage(0, false);
        boardManager.getBoard().swapTileImage(1, false);
        assertEquals(MemoryMatrixTile.RED, boardManager.getBoard().getTile(0, 0).getBackground());
        assertEquals(MemoryMatrixTile.RED, boardManager.getBoard().getTile(0, 1).getBackground());
    }

    /**
     * Test whether swapping the image of the last two tiles works.
     */
    @Test
    public void testSwapTileImageLastTwo() {
        setUpCorrect();
        assertEquals(MemoryMatrixTile.BLUE, boardManager.getBoard().getTile(5, 4).getBackground());
        assertEquals(MemoryMatrixTile.BLUE, boardManager.getBoard().getTile(5, 5).getBackground());
        boardManager.getBoard().swapTileImage(35, false);
        boardManager.getBoard().swapTileImage(34, false);
        assertEquals(MemoryMatrixTile.RED, boardManager.getBoard().getTile(5, 4).getBackground());
        assertEquals(MemoryMatrixTile.RED, boardManager.getBoard().getTile(5, 5).getBackground());
    }

    /**
     * Test whether isValidTap works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertTrue(boardManager.isValidTap(0));
        assertTrue(boardManager.isValidTap(5));
        assertFalse(boardManager.isValidTap(35));
    }

    /**
     * Test whether touchMove correctly swaps the tapped tile's image
     */
    @Test
    public void testTouchMove() {
        setUpCorrect();
        assertEquals(MemoryMatrixTile.GRAY, boardManager.getBoard().getTile(0, 0).getBackground());
        boardManager.touchMove(0);
        assertEquals(MemoryMatrixTile.RED, boardManager.getBoard().getTile(0, 0).getBackground());
    }

    /**
     * Test whether swapBoardsForDisplay works by swapping two the
     * two complete set of Tiles the puzzle should still be solved.
     */
    @Test
    public void testSwapBoardsForDisplay() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());
        boardManager.getBoard().swapBoardsForDisplay();
        assertTrue(boardManager.puzzleSolved());
    }

    /**
     * Test whether getFinished returns the solution set of Tiles, modifying
     * this should make the game incomplete.
     */
    @Test
    public void testGetSolution() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());

        MemoryMatrixTile[][] solution = boardManager.getBoard().getFinished();
        solution[0][0].setBackground(MemoryMatrixTile.BLUE);
        MemoryMatrixBoard board = boardManager.getBoard();

        //Cannot use the puzzleSolved method since a COPY of the actual solution is given
        boolean solved = true;
        for (int row = 0; row != BOARDSIZE; row++) {
            for (int col = 0; col != BOARDSIZE; col++)
                if (solution[row][col].getBackground() != board.getTile(row, col).getBackground() &&
                        solution[row][col].getBackground() == MemoryMatrixTile.BLUE) {
                    solved = false;
                }
        }
        assertFalse(solved);
    }

    /**
     * Testing whether the correct value is returned by getBoardSize.
     */
    @Test
    public void testGetBoardSize() {
        setUpCorrect();
        assertEquals(BOARDSIZE, boardManager.getBoard().getBoardSize());
    }

    /**
     * Testing if setBoardSize sets the value correctly.
     */
    @Test
    public void testSetBoardSize() {
        setUpCorrect();
        assertEquals(BOARDSIZE, boardManager.getBoard().getBoardSize());
        boardManager.getBoard().setBoardSize(BOARDSIZE + 1);
        assertEquals(BOARDSIZE + 1, boardManager.getBoard().getBoardSize());
    }

    /**
     * Test whether constructor for board correctly sets up 2D array of tiles
     */
    @Test
    public void testBoardConstructor() {
        List<MemoryMatrixTile> tiles = makeBlankTiles();
        MemoryMatrixBoard board = new MemoryMatrixBoard(tiles, tiles, BOARDSIZE);
        assertEquals(BOARDSIZE, board.getFinished().length);
    }
}
