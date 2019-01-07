package fall2018.csc2017.GameCentre;

import android.content.Intent;

import fall2018.csc2017.memorymatrix.MemoryMatrixActivity;
import fall2018.csc2017.slidingtiles.SlidingTilesActivity;
import fall2018.csc2017.treasurehunt.TreasureHuntActivity;

/**
 * A controller for GameLauncherActivity.
 */
class GameLauncherActivityController {

    /**
     * The context of the application.
     */
    private GameLauncherActivity activity;

    /**
     * Constructor that takes assigns the activity variable a context.
     *
     * @param activity the context of the application
     */
    GameLauncherActivityController(GameLauncherActivity activity) {
        this.activity = activity;
    }

    /**
     * Switch to the MemoryMatrix view
     */
    void switchToMemoryMatrix() {
        Intent tmp = new Intent(activity, MemoryMatrixActivity.class);
        activity.startActivity(tmp);
    }

    /**
     * Switch to the SlidingTilesActivity view
     */
    void switchToSlidingTiles() {
        Intent tmp = new Intent(activity, SlidingTilesActivity.class);
        activity.startActivity(tmp);
    }

    /**
     * Switch to the TreasureHuntActivity view
     */
    void switchToTreasureHunt() {
        Intent tmp = new Intent(activity, TreasureHuntActivity.class);
        activity.startActivity(tmp);
    }

    /**
     * Retrieve the current user's scores
     *
     * @return currentUser's list of scores
     */
    String getNewScores() {
        if (AccountManager.getInstance().getCurrentUser().getListOfScores().equals("")) {
            return "Oops, there aren't any to be displayed";
        } else {
            return AccountManager.getInstance().getCurrentUser().getListOfScores();
        }
    }

    /**
     * Changes password for the User who is logged in.
     *
     * @param newPassword the password to change to
     */
    void changePasswordRequest(String newPassword) {
        AccountManager.getInstance().getCurrentUser().setPassword(newPassword);
        AccountManager.getInstance().saveUsers(activity);
    }

}
