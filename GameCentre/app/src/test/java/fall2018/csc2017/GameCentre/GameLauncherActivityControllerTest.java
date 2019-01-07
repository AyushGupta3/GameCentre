package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the GameLauncherActivityController
 */
public class GameLauncherActivityControllerTest {

    /**
     * Test the getNewScores method so that it is non null
     */
    @Test
    public void testGetNewScoresNonNull() {
        AccountManager.getInstance().setCurrentUser(new User("user", "pass"));
        GameLauncherActivityController controller = new GameLauncherActivityController(
                new GameLauncherActivity());
        assertNotNull(controller.getNewScores());
    }
}
