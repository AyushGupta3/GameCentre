package fall2018.csc2017.GameCentre;

import org.junit.Test;

import fall2018.csc2017.memorymatrix.MemoryMatrixScore;

import static org.junit.Assert.*;

/**
 * Tests for the ScoreBoard
 */
public class ScoreBoardTest {

    /**
     * Test whether ScoreBoard correctly retrieves the score.
     */
    @Test
    public void testGetScore() {
        ScoreBoard scoreBoard = ScoreBoard.getInstance();
        scoreBoard.processScore("Memory Matrix 5x5", new MemoryMatrixScore("Test"));

        assertEquals(0, scoreBoard.getScore("Memory Matrix 5x5").getScore());

    }
}