package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTilesBoardAndTileTest {

    /**
     * The board manager for testing.
     */
    private SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager(4);

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<SlidingTilesTile> makeTiles() {
        List<SlidingTilesTile> tiles = new ArrayList<>();
        final int numTiles = boardManager.getBoard().getBoardSize() * boardManager.getBoard().getBoardSize();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTilesTile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved SlidingTilesBoard.
     */
    private void setUpCorrect() {
        List<SlidingTilesTile> tiles = makeTiles();
        SlidingTilesBoard board = new SlidingTilesBoard(tiles, boardManager.getBoard().getBoardSize());
        boardManager = new SlidingTilesBoardManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertTrue(boardManager.isValidTap(11));
        assertTrue(boardManager.isValidTap(14));
        assertFalse(boardManager.isValidTap(10));
    }

    /**
     * Test whether undo returns the state of the game to its previous state
     */
    @Test
    public void testSingleMoveUndo() {
        setUpCorrect();
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        assertEquals(12, boardManager.getBoard().getTile(2, 3).getId());
        boardManager.touchMove(11);
        boardManager.undo();
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        assertEquals(12, boardManager.getBoard().getTile(2, 3).getId());
    }


    /**
     * Test whether undo returns the state of the game to a state 2 moves back.
     */
    @Test
    public void testTwoMoveUndo() {
        setUpCorrect();
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        assertEquals(12, boardManager.getBoard().getTile(2, 3).getId());
        boardManager.touchMove(11);
        boardManager.touchMove(10);
        boardManager.undo();
        boardManager.undo();
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        assertEquals(12, boardManager.getBoard().getTile(2, 3).getId());
    }

    /**
     * Test whether touchMove correctly swaps an adjacent tile with the blank tile
     */
    @Test
    public void testTouchMove() {
        setUpCorrect();
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        assertEquals(12, boardManager.getBoard().getTile(2, 3).getId());
        boardManager.touchMove(11);
        assertEquals(16, boardManager.getBoard().getTile(2, 3).getId());
        assertEquals(12, boardManager.getBoard().getTile(3, 3).getId());
    }


    /**
     * Test whether getTileLocation correctly returns the array containing the correct row and array
     * of the tile with ID 16
     */
    @Test
    public void testGetTileLocation() {
        setUpCorrect();
        int blankRow = boardManager.getBoard().getTileLocationByID(16).get(0);
        int blankCol = boardManager.getBoard().getTileLocationByID(16).get(1);
        assertEquals(3, blankRow);
        assertEquals(3, blankCol);
    }

    /**
     * Test whether getTileLocationByID returns consisting of -1 and -1 if the tile does not exist
     */
    @Test
    public void getInvalidTile() {
        setUpCorrect();
        ArrayList<Integer> test = new ArrayList<>();
        test.add(-1);
        test.add(-1);
        assertEquals(test, boardManager.getBoard().getTileLocationByID(100));
    }
}

