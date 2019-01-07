package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountManagerTest {

    /**
     * Test whether accountManager can successfully a user, avoiding duplicate usernames and
     * usernames containing '_'.
     */
    @Test
    public void registerUser() {
        AccountManager accountManager = AccountManager.getInstance();
        assertEquals(2, accountManager.registerUser("username", "password"));
        assertEquals(0, accountManager.registerUser("username", "password"));
        assertEquals(1, accountManager.registerUser("user_name", "password"));

    }

    /**
     * Test whether accountManager correctly checks if the username and password exist in the
     * database, returning false if it does not exist in the database.
     */
    @Test
    public void isValidAccount() {
        AccountManager accountManager = AccountManager.getInstance();
        accountManager.registerUser("username", "password");
        assertTrue(accountManager.isValidAccount("username", "password"));
        assertFalse(accountManager.isValidAccount("user", "pass"));
    }
}