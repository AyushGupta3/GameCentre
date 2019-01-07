package fall2018.csc2017.memorymatrix;

import org.junit.Test;


import static org.junit.Assert.assertNotNull;

/**
 * Tests for the MemoryMatrixActivityController
 */
public class MemoryMatrixActivityControllerTest {

    /**
     * Controller being tested
     */
    private MemoryMatrixActivityController controller;

    /**
     * Set up the controller for testing
     */
    private void setUpController() {
        controller = new MemoryMatrixActivityController();
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
