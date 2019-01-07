package fall2018.csc2017.memorymatrix;

import android.content.Context;
import android.content.Intent;
import android.test.mock.MockContext;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

import fall2018.csc2017.GameCentre.AccountManager;
import fall2018.csc2017.GameCentre.ScoreBoard;

/**
 * The controller for MemoryMatrixActivity
 */
class MemoryMatrixActivityController {

    /**
     * The board manager.
     */
    private MemoryMatrixBoardManager boardManager;

    /**
     * the size of the board that will be created
     */
    private int boardSize = 6;

    /**
     * The current score of the game.
     */
    private MemoryMatrixScore score;

    /**
     * The Activity Controller
     */
    private Context activity;

    /**
     * Initializes this MemoryMatrixActivityController.
     *
     * @param activity the activity of the application.
     */
    MemoryMatrixActivityController(MemoryMatrixActivity activity) {
        this.activity = activity;
        boardManager = new MemoryMatrixBoardManager(boardSize);
        score = new MemoryMatrixScore();
        saveToFile(getTempSaveFileName());
        ScoreBoard scoreboard = ScoreBoard.getInstance();
        scoreboard.loadScores(activity);
    }

    /**
     * Constructor used for testing the controller
     */
    MemoryMatrixActivityController() {
        this.activity = new MockContext();
        score = new MemoryMatrixScore("test name");
    }

    /**
     * Switch to the MemoryMatrixGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(activity, MemoryMatrixGameActivity.class);
        tmp.putExtra("BOARD_SIZE", this.boardSize);
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
                "Memorymatrix_save_file.ser";
    }

    /**
     * Return the name of the file for temporary saving after formatting with username
     *
     * @return temp save file associated with user
     */
    static String getTempSaveFileName() {
        return AccountManager.getInstance().getCurrentUser().getUsername() +
                "MemoryMatrix_temp_save_file.ser";
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
                boardManager = (MemoryMatrixBoardManager) input.readObject();
                score = (MemoryMatrixScore) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("MemoryMatrix Activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("MemoryMatrix Activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("MemoryMatrix Activity", "File contained unexpected data type: " +
                    e.toString());
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
     * Start a Memory Matrix game.
     */
    void startGame() {
        boardManager = new MemoryMatrixBoardManager(boardSize);
        AccountManager.getInstance().getCurrentUser().addSavedGame("Memorymatrix");
        switchToGame();
    }

    /**
     * Changed the selected board size.
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
        String title = "Best Score for " + boardSize + "x" + boardSize;
        ScoreBoard scoreboard = ScoreBoard.getInstance();
        String userWithBestScore;
        String bestScore;
        if (scoreboard.getScore("Memory Matrix " + boardSize + "x" + boardSize) == null) {
            userWithBestScore = "none";
            bestScore = "none";
        } else {
            MemoryMatrixScore score = (MemoryMatrixScore) scoreboard.getScore("Memory Matrix " + boardSize + "x" + boardSize);
            userWithBestScore = score.getScoreOwner();
            bestScore = String.format(Locale.US, "%d", score.getScore());
        }
        return new String[]{title, userWithBestScore, bestScore};
    }

    /**
     * Load the saved game state
     */
    void resume() {
        loadFromFile(getTempSaveFileName());
    }
}
