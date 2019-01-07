package fall2018.csc2017.memorymatrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class used to test the Controller for Memorymatrixgameactivity
 */
public class MemoryMatrixGameActivityControllerTest {

    /**
     * The controller being tested
     */
    private MemoryMatrixGameActivityController controller;

    /**
     * Setup the controller for testing
     */
    private void setUp() {
        controller = new MemoryMatrixGameActivityController();
    }

    /**
     * Checking that getScore returns the right int value
     */
    @Test
    public void testGetScore() {
        setUp();
        assertEquals(0, controller.getScore());
    }
}
