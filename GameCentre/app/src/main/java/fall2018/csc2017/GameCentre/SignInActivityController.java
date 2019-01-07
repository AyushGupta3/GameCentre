package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.widget.Toast;

/**
 * A controller for SignInActivity.
 */
class SignInActivityController {

    /**
     * The activity that uses this controller.
     */
    private SignInActivity activity;

    /**
     * The account manager object.
     */
    private AccountManager accountManager;

    /**
     * Initializes this SignInActivityController.
     *
     * @param activity the activity that uses this controller.
     */
    SignInActivityController(SignInActivity activity) {
        accountManager = AccountManager.getInstance();
        accountManager.loadUsers(activity);
        this.activity = activity;
    }

    /**
     * Sign in the user.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     */
    void signIn(String username, String password) {
        if (accountManager.isValidAccount(username, password)) {
            accountManager.setCurrentUser(accountManager.getUser(username));
            makeToastSavedText("Login Successful!");
            switchToMenu();
        } else {
            makeToastSavedText("Login Unsuccessful!");
        }
    }

    /**
     * Switch to the GameLauncherActivity view.
     */
    private void switchToMenu() {
        Intent tmp = new Intent(activity, GameLauncherActivity.class);
        activity.startActivity(tmp);
    }

    /**
     * Display a message.
     */
    private void makeToastSavedText(String text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Sign up the user.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     */
    void signUp(String username, String password) {
        int userRegistered = accountManager.registerUser(username, password);
        if (userRegistered == AccountManager.SUCCESSFUL_REGISTER) {
            makeToastSavedText("Registered Successfully!");
            accountManager.saveUsers(activity);
            accountManager.setCurrentUser(accountManager.getUser(username));
            switchToMenu();
        } else if (userRegistered == AccountManager.ALREADY_EXISTS) {
            makeToastSavedText("Username Already Exists!!");
        } else {
            makeToastSavedText("Invalid Username");
        }
    }
}
