package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.os.Bundle;
import android.test.mock.MockContext;
import android.util.Log;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCentre.AccountManager;
import fall2018.csc2017.GameCentre.ScoreBoard;

import static android.content.Context.MODE_PRIVATE;

/**
 * The controller for the game activity for Sliding Tiles.
 */
class SlidingTilesGameActivityController {

    /**
     * The board manager.
     */
    private SlidingTilesBoardManager boardManager;

    /**
     * The buttons to display.
     */
    private List<Button> tileButtons;

    /**
     * The current score of the game.
     */
    private SlidingTilesScore score;

    /**
     * The account manager necessary for modifying user info.
     */
    private AccountManager accountManager;

    /**
     * The context of the gameActivity for Sliding Tiles
     */
    private Context activity;

    /**
     * Board size for the game
     */
    private int boardSize;


    /**
     * Constructor which takes in the context of SlidingTilesGameActivity
     *
     * @param activity the SlidingTilesGameActivity
     */
    SlidingTilesGameActivityController(SlidingTilesGameActivity activity) {
        this.activity = activity;
        accountManager = AccountManager.getInstance();
        loadFromFile(SlidingTilesActivityController.getTempSaveFileName());
        createTileButtons();
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle != null) {
            boardSize = bundle.getInt("BOARD_SIZE");
        }
        autoSave();
    }

    /**
     * Default Constructor used for testing.
     */
    SlidingTilesGameActivityController() {
        this.activity = new MockContext();
        score = new SlidingTilesScore("Test name");
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
                boardManager = (SlidingTilesBoardManager) input.readObject();
                score = (SlidingTilesScore) input.readObject();
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
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    activity.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.writeObject(score);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the game when called.
     */
    void autoSave() {
        saveToFile(SlidingTilesActivityController.getSaveFileName());
        saveToFile(SlidingTilesActivityController.getTempSaveFileName());
        accountManager.saveUsers(activity.getApplicationContext());
    }


    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    void updateTileButtons() {
        SlidingTilesBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getBoardSize();
            int col = nextPos % boardManager.getBoard().getBoardSize();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Create the buttons for displaying the tiles.
     */
    private void createTileButtons() {
        SlidingTilesBoard board = boardManager.getBoard();
        int boardSize = board.getBoardSize();
        List<Button> tileButtons = new ArrayList<>();
        for (int row = 0; row != boardSize; row++) {
            for (int col = 0; col != boardSize; col++) {
                Button tmp = new Button(activity);

                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                tileButtons.add(tmp);
            }
        }
        this.tileButtons = tileButtons;
    }

    /**
     * Getter method for column width
     *
     * @param displayWidth the display width of the gridView
     * @return an int representing the column width
     */
    int getColumnWidth(int displayWidth) {
        return displayWidth / boardSize;
    }

    /**
     * Getter method for column height
     *
     * @param displayHeight the display height of the gridview
     * @return an int representing the column height
     */
    int getColumnHeight(int displayHeight) {
        return displayHeight / boardSize;
    }

    /**
     * Getter for tileButtons
     *
     * @return the array list representing the tileButtons for the gridView
     */
    List<Button> getTileButtons() {
        return tileButtons;
    }

    /**
     * Getter for the boardSize
     *
     * @return returns the size of the board, boardSize
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Returns the boardmanager for this game
     *
     * @return the boardmanager for the game
     */
    SlidingTilesBoardManager getBoardManager() {
        return boardManager;
    }

    /**
     * Getter the score for the game
     *
     * @return an int representing score of the game
     */
    int getScore() {
        return score.getScore();
    }

    /**
     * Updates and saves values if the game is over
     */
    void updateGame() {
        if (this.boardManager.puzzleSolved()) {
            SlidingTilesBoard board = boardManager.getBoard();
            int boardSize = board.getBoardSize();
            score.countMove();
            score.hasWon();
            ScoreBoard scoreboard = ScoreBoard.getInstance();
            scoreboard.processScore("Sliding Tiles " + boardSize + "x" + boardSize, score);
            scoreboard.saveScores(activity);
            String gameName = "Sliding Tiles " + boardSize + "x" + boardSize;
            AccountManager.getInstance().getCurrentUser().updateScore(gameName, score);
            saveToFile(SlidingTilesActivityController.getSaveFileName());
            saveToFile(SlidingTilesActivityController.getTempSaveFileName());
            AccountManager.getInstance().saveUsers(activity);
        } else {
            score.countMove();
        }
        autoSave();
    }
}

