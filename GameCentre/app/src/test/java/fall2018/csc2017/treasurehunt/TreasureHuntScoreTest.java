package fall2018.csc2017.treasurehunt;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the TreasureHuntScore class
 */
public class TreasureHuntScoreTest {

    /**
     * Test the getScoreOwner() method
     */
    @Test
    public void testGetScoreOwner() {
        TreasureHuntScore score = new TreasureHuntScore("owner");
        assertEquals(score.getScoreOwner(), "owner");
    }

    /**
     * Test the getScore() method
     */
    @Test
    public void testGetScore() {
        TreasureHuntScore score = new TreasureHuntScore("owner");
        assertEquals(score.getScore(), 0);
    }

    /**
     * Test the getScore() method when they haven't won
     */
    @Test
    public void testNonWinningCountMove() {
        TreasureHuntScore score = new TreasureHuntScore("owner");
        assertEquals(0, score.getScore());
        score.countMove();
        assertEquals(1, score.getScore());
    }

    /**
     * Test the getScore() method when they have won
     */
    @Test
    public void testWinningCountMove() {
        TreasureHuntScore score = new TreasureHuntScore("owner");
        assertEquals(0, score.getScore());
        score.hasWon();
        score.countMove();
        assertEquals(0, score.getScore());
    }

    /**
     * Test the toCompare() method when its unequal
     */
    @Test
    public void testCompareToInequality() {
        TreasureHuntScore score1 = new TreasureHuntScore("owner");
        TreasureHuntScore score2 = new TreasureHuntScore("owner");

        score1.countMove();
        score1.countMove();
        score2.countMove();

        assertTrue(score1.compareTo(score2) > 0);
        assertTrue(score2.compareTo(score1) < 0);
    }

    /**
     * Test the toCompare() method when its equal
     */
    @Test
    public void testCompareToEquality() {
        TreasureHuntScore score1 = new TreasureHuntScore("owner");
        TreasureHuntScore score2 = new TreasureHuntScore("owner");

        score1.countMove();
        score2.countMove();

        assertEquals(0, score1.compareTo(score2));
        assertEquals(0, score2.compareTo(score1));
    }
}
