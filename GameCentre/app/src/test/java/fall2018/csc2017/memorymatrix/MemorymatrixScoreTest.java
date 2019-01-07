package fall2018.csc2017.memorymatrix;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test MemoryMatrixScore
 */
public class MemorymatrixScoreTest {

    /**
     * Tests the getScoreOwner method
     */
    @Test
    public void testGetScoreOwner() {
        MemoryMatrixScore score = new MemoryMatrixScore("test");
        assertEquals(score.getScoreOwner(), "test");
    }

    /**
     * Tests the getScore method
     */
    @Test
    public void testGetScore() {
        MemoryMatrixScore score = new MemoryMatrixScore("test");
        assertEquals(score.getScore(), 0);
    }

    /**
     * Tests the countMove method
     */
    @Test
    public void testCountMove() {
        MemoryMatrixScore score = new MemoryMatrixScore("test");
        assertEquals(true, score.getScore() == 0);
        score.countMove();
        assertEquals(true, score.getScore() == 1);
        score.hasWon();
        score.countMove();
        assertEquals(true, score.getScore() == 1);
    }

    /**
     * Tests the compareTo method
     */
    @Test
    public void testCompareTo() {
        MemoryMatrixScore score1 = new MemoryMatrixScore("test");
        score1.countMove();
        score1.countMove();
        MemoryMatrixScore score2 = new MemoryMatrixScore("test");
        score2.countMove();
        assertEquals(true, score1.compareTo(score2) > 0);
        assertEquals(true, score2.compareTo(score1) < 0);
        score2.countMove();
        assertEquals(true, score1.compareTo(score2) == 0);
        assertEquals(true, score2.compareTo(score1) == 0);
    }
}