package fall2018.csc2017.slidingtiles;

import org.junit.Test;


import static org.junit.Assert.assertNotNull;

public class SlidingTilesActivityControllerTest {
    /**
     * Controller being tested
     */
    private SlidingTilesActivityController controller;

    /**
     * Set up the controller for testing
     */
    private void setUpController() {
        controller = new SlidingTilesActivityController();
    }

    /**
     * Testing if the getBestScore is returning a String[]
     */
    @Test
    public void testGetBestScore() {
        setUpController();
        assertNotNull(controller.getBestScore());
    }
}
