package fall2018.csc2017.BoardGameUtilities;


import org.junit.Test;


import android.app.Activity;
import android.content.Context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A set of tests for MovementController.
 */
public class MovementControllerTest {

    /**
     * A board manager that has one valid tap position, and the board is solved if that position is
     * tapped.
     */
    private class TestBoardManager implements BoardManager {

        /**
         * Whether or not the board is solved.
         */
        boolean solved;

        /**
         * The only valid tap position.
         */
        int position;

        /**
         * Initialize this board manager.
         *
         * @param position the only valid tap position of this board.
         */
        TestBoardManager(int position) {
            solved = false;
            this.position = position;
        }

        /**
         * Return's a boolean representing the completion of the game.
         *
         * @return whether the puzzle has been completed.
         */
        @Override
        public boolean puzzleSolved() {
            return solved;
        }

        /**
         * Return whether the tile tapped at position is valid.
         *
         * @param position the position which was tapped.
         * @return whether the tile tapped at position is valid.
         */
        @Override
        public boolean isValidTap(int position) {
            return position == this.position;
        }

        /**
         * Process a touch that was made at position
         *
         * @param position the position which was tapped.
         */
        @Override
        public void touchMove(int position) {
            if (position == this.position)
                solved = true;
        }
    }

    /**
     * Test the method processTapMovement.
     */
    @Test
    public void testProcessTapMovement() {
        Context context = new Activity();
        MovementController controller = new MovementController();
        int randomPosition = (int) (Math.random() * 40);
        TestBoardManager boardManager = new TestBoardManager(randomPosition);
        controller.setBoardManager(boardManager);
        assertFalse(boardManager.puzzleSolved());
        String exceptionMessage = "Method makeText in android.widget.Toast not mocked. See http:/" +
                "/g.co/androidstudio/not-mocked for details.";
        try {
            controller.processTapMovement(context, randomPosition + 1);
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), exceptionMessage);
        }
        assertFalse(boardManager.puzzleSolved());
        try {
            controller.processTapMovement(context, randomPosition);
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), exceptionMessage);
        }
        assertTrue(boardManager.puzzleSolved());
    }
}
