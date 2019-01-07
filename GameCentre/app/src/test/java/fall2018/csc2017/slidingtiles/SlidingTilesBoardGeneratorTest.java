package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SlidingTilesBoardGeneratorTest {

    /**
     * Test whether SlidingTilesBoardGenerator can generate a predetermined board.
     */
    @Test
    public void testGenerateDeterminedBoard() {
        SlidingTilesBoardGenerator boardGenerator = new SlidingTilesBoardGenerator();
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(15);
        moves.add(16);
        SlidingTilesBoard board = boardGenerator.generateDeterminedBoard(moves);
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager(board);
        assertTrue(boardManager.puzzleSolved());
    }
}