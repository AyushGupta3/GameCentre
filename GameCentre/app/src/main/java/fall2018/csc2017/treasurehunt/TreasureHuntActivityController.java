package fall2018.csc2017.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.test.mock.MockContext;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

import fall2018.csc2017.GameCentre.AccountManager;
import fall2018.csc2017.GameCentre.ScoreBoard;

/**
 * The controller for TreasureHuntActivity.
 */
class TreasureHuntActivityController {

    /**
     * The score board singleton object z
     */
    private ScoreBoard scoreBoard;

    /**
     * The board manager.
     */
    private TreasureHuntBoardManager boardManager;

    /**
     * The current score of the game.
     */
    private TreasureHuntScore score;

    /**
     * The selected size of the board.
     */
    private int boardSize;

    /**
     * The context of the application.
     */
    private Context activity;

    /**
     * Initializes this TreasureHuntActivityListener.
     *
     * @param activity the activity of the application.
     */
    TreasureHuntActivityController(TreasureHuntActivity activity) {
        this.activity = activity;
        scoreBoard = ScoreBoard.getInstance();
        boardSize = 9;
        scoreBoard.loadScores(activity);
    }

    /**
     * Constructor used for Testing the controller
     */
    TreasureHuntActivityController() {
        this.activity = new MockContext();
        score = new TreasureHuntScore("test name");
        scoreBoard = ScoreBoard.getInstance();
    }

    /**
     * Switch to the TreasureHuntGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(activity, TreasureHuntGameActivity.class);
        saveToFile(getTempSaveFileName());
        activity.startActivity(tmp);
    }

    /**
     * Return the name of the file for saving after formatting with username
     *
     * @return save file associated with user
     */
    static String getSaveFileName() {
        return AccountManager.getInstance().getCurrentUser().getUsername() +
                "TreasureHunt_save_file.ser";
    }

    /**
     * Return the name of the file for temporary saving after formatting with username
     *
     * @return temp save file associated with user
     */
    static String getTempSaveFileName() {
        return AccountManager.getInstance().getCurrentUser().getUsername() +
                "TreasureHunt_temp_save_file.ser";
    }

    /**
     * Load the board manager and score from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = activity.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (TreasureHuntBoardManager) input.readObject();
                score = (TreasureHuntScore) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager and score to fileName.
     *
     * @param fileName the name of the file
     */
    private void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    activity.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.writeObject(score);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Start a Treasure Hunt game.
     */
    void startGame() {
        TreasureHuntBoard board = TreasureHuntBoardGenerator.generateSolvableBoard(boardSize);
        boardManager = new TreasureHuntBoardManager(board);
        score = new TreasureHuntScore();
        AccountManager.getInstance().getCurrentUser().addSavedGame("Treasurehunt");
        switchToGame();
    }

    /**
     * Load the last saved Treasure Hunt game.
     */
    void loadGame() {
        if (AccountManager.getInstance().getCurrentUser().checkSavedGame("Treasurehunt")) {
            loadFromFile(getSaveFileName());
            saveToFile(getTempSaveFileName());
            Toast.makeText(activity, "Loaded Game", Toast.LENGTH_SHORT).show();
            switchToGame();
        } else {
            Toast.makeText(activity, "No Game Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Change the selected board size.
     *
     * @param boardSize the board size that the user selected.
     */
    void changeSelectedBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    /**
     * Return a description of the best score, the username of the user with the best score, and
     * the best score for the game Treasure Hunt given the selected board size. The 0th index is
     * a description of the best score, the 1st index is the username of the user with that score,
     * and the 2nd index is the score.
     *
     * @return a description of the best score, the username of the user with the best score, and
     * the best score for the game Treasure Hunt given the selected board size.
     */
    String[] getBestScore() {
        String gameName = "Treasure Hunt " + boardSize + "x" + boardSize;
        String title = "Best Score for " + boardSize + "x" + boardSize;
        TreasureHuntScore currentBestScore = (TreasureHuntScore) scoreBoard.getScore(gameName);
        String userWithBestScore;
        String bestScore;
        if (currentBestScore == null || currentBestScore.getScoreOwner() == null) {
            userWithBestScore = "none";
            bestScore = "none";
        } else {
            userWithBestScore = currentBestScore.getScoreOwner();
            bestScore = String.format(Locale.US, "%d", currentBestScore.getScore());
        }
        return new String[]{title, userWithBestScore, bestScore};
    }

    /**
     * Load the saved game state and update the best score.
     */
    void resume() {
        loadFromFile(getTempSaveFileName());
    }
}
