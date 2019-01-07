package fall2018.csc2017.treasurehunt;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the TreasureHuntGameActivityController class
 */
public class TreasureHuntGameActivityControllerTest {

    /**
     * Test the getCurrentScore method at the beginning of the game
     */
    @Test
    public void testGetCurrentScoreAtBeginning() {
        TreasureHuntGameActivityController controller = new TreasureHuntGameActivityController();
        assertEquals(0, controller.getCurrentScore().getScore());
    }
}


