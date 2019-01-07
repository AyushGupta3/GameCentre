package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingtilesScoreTest {

    /**
     * Test whether getScoreOwner correctly returns the username of the scoreowner
     */
    @Test
    public void testGetScoreOwner() {
        SlidingTilesScore score = new SlidingTilesScore("test");
        assertEquals(score.getScoreOwner(), "test");
    }

    /**
     * Test whether getScore correctly returns the score
     */
    @Test
    public void testGetScore() {
        SlidingTilesScore score = new SlidingTilesScore("test");
        assertEquals(score.getScore(), 0);
    }

    /**
     * Test whether countMove correctly counts the moves.
     */
    @Test
    public void testCountMove() {
        SlidingTilesScore score = new SlidingTilesScore("test");
        assertEquals(true, score.getScore() == 0);
        score.countMove();
        assertEquals(true, score.getScore() == 1);
        score.hasWon();
        score.countMove();
        assertEquals(true, score.getScore() == 1);
    }

    /**
     * Test whether compareTo correctly compares two SlidingTileScore objects
     */
    @Test
    public void testCompareTo() {
        SlidingTilesScore score1 = new SlidingTilesScore("test");
        score1.countMove();
        score1.countMove();
        SlidingTilesScore score2 = new SlidingTilesScore("test");
        score2.countMove();
        assertEquals(true, score1.compareTo(score2) > 0);
        assertEquals(true, score2.compareTo(score1) < 0);
        score2.countMove();
        assertEquals(true, score1.compareTo(score2) == 0);
        assertEquals(true, score2.compareTo(score1) == 0);
    }
}

