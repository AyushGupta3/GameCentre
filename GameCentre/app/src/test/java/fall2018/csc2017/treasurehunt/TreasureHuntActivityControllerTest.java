package fall2018.csc2017.treasurehunt;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the TreasureHuntActivityController class
 */
public class TreasureHuntActivityControllerTest {

    /**
     * Test the getBestScore method
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetBestScore() throws Exception {
        TreasureHuntActivityController controller = new TreasureHuntActivityController();
        assertNotNull(controller.getBestScore());
    }
}


