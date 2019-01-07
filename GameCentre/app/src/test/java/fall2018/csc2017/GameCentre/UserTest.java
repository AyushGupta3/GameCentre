package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 tests for the user class
 */
public class UserTest {

    /**
     * The User object used for testing.
     */
    private User dummyUser;

    /**
     * A helper to reset the state of dummyUser before every test.
     */
    private void setUpNewAccount() {
        dummyUser = new User("username", "password");
    }

    /**
     * Test to see if the correct username string is returned.
     */
    @Test
    public void testGetUsername() {
        setUpNewAccount();
        assertEquals("username", dummyUser.getUsername());
    }

    /**
     * Test to see if the correct string representing password is returned.
     */
    @Test
    public void testGetPassword() {
        setUpNewAccount();
        assertEquals("password", dummyUser.getPassword());
    }

    /**
     * Test to see if a saved game gets added properly.
     */
    @Test
    public void testAddSavedGameAndCheckSavedGame() {
        setUpNewAccount();
        assertFalse(dummyUser.checkSavedGame("game"));
        dummyUser.addSavedGame("game");
        assertTrue(dummyUser.checkSavedGame("game"));
    }

    /**
     * Testing whether the password is set properly.
     */
    @Test
    public void testSetPassword() {
        setUpNewAccount();
        assertEquals("password", dummyUser.getPassword());
        dummyUser.setPassword("123456");
        assertEquals("123456", dummyUser.getPassword());
    }
}
