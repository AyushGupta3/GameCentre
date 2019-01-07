package fall2018.csc2017.memorymatrix;

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
import fall2018.csc2017.GameCentre.Score;
import fall2018.csc2017.GameCentre.ScoreBoard;

import static android.content.Context.MODE_PRIVATE;

/**
 * The controller for the game activity for Memory Matrix
 */
class MemoryMatrixGameActivityController {

    /**
     * The board manager.
     */
    private MemoryMatrixBoardManager boardManager;

    /**
     * The buttons to display.
     */
    private List<Button> tileButtons;

    /**
     * Used to show the board at the start of the game
     */
    private boolean showSolved;

    /**
     * Score object
     */
    private Score score;

    /**
     * The context of the gameActivity for MemoryMatrix
     */
    private Context activity;

    /**
     * Board size for the game
     */
    private int boardSize;

    /**
     * Constructor which takes in the context of the MemoryMatrixGameActivity
     *
     * @param activity the context represented by the MemoryMatrixGameActivity
     */
    MemoryMatrixGameActivityController(MemoryMatrixGameActivity activity) {
        this.activity = activity;
        showSolved = true;
        loadFromFile(MemoryMatrixActivityController.getTempSaveFileName());
        score = boardManager.getScore();
        createTileButtons();

        Bundle bundle = activity.getIntent().getExtras();
        if (bundle != null) {
            boardSize = bundle.getInt("BOARD_SIZE");
        }
    }

    /**
     * Default Constructor used for testing.
     */
    MemoryMatrixGameActivityController() {
        this.activity = new MockContext();
        score = new MemoryMatrixScore("Test name");
    }

    /**
     * Create the buttons for displaying the tiles.
     */
    private void createTileButtons() {
        MemoryMatrixBoard board = boardManager.getBoard();
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
     * Update the backgrounds on the buttons to match the tiles.
     */
    void updateTileButtons() {
        MemoryMatrixBoard board = boardManager.getBoard();
        int boardSize = board.getBoardSize();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardSize;
            int col = nextPos % boardSize;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
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
            Log.e("MemoryGameActivity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("MemoryGameActivity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("MemoryGameActivity", "File contained unexpected data type: " +
                    e.toString());
        }
    }

    /**
     * Save the board manager and score to fileName.
     *
     * @param fileName the name of the file
     */
    void saveToFile(String fileName) {
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
     * Updates and saves values if the game is over
     */
    void updateIfGameFinished() {
        if (this.boardManager.puzzleSolved()) {
            MemoryMatrixBoard board = boardManager.getBoard();
            int boardSize = board.getBoardSize();
            score.hasWon();
            ScoreBoard scoreboard = ScoreBoard.getInstance();
            scoreboard.processScore("Memory Matrix " + boardSize + "x" + boardSize, score);
            scoreboard.saveScores(activity);
            String gameName = "Memory Matrix " + boardSize + "x" + boardSize;
            AccountManager.getInstance().getCurrentUser().updateScore(gameName, score);
            saveToFile(MemoryMatrixActivityController.getSaveFileName());
            saveToFile(MemoryMatrixActivityController.getTempSaveFileName());
            AccountManager.getInstance().saveUsers(activity);
        }
    }

    /**
     * Returns the boardmanager for this game
     *
     * @return the boardmanager for the game
     */
    MemoryMatrixBoardManager getBoardManager() {
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
     * Sets showSolved to false
     */
    void setShowSolvedFalse() {
        showSolved = false;
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
     * Getter method for the showSolvedVariable
     *
     * @return returns the boolean showSolved
     */
    boolean getShowSolved() {
        return showSolved;
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
}
