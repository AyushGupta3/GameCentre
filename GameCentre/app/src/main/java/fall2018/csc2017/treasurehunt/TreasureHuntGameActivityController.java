package fall2018.csc2017.treasurehunt;

import android.content.Context;
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

/**
 * The controller for the game activity for Treasure Hunt
 */
class TreasureHuntGameActivityController {

    /**
     * The score board singleton object
     */
    private ScoreBoard scoreBoard;

    /**
     * The object that manages the board
     */
    private TreasureHuntBoardManager boardManager;

    /**
     * The score of the current player
     */
    private TreasureHuntScore score;

    /**
     * The account manager singleton object
     */
    private AccountManager accountManager;

    /**
     * The activity controller to separate view from controller
     */
    private Context activity;

    /**
     * The array of tile buttons
     */
    private List<Button> tileButtons;

    /**
     * The constructor for the controller used by the activity
     *
     * @param activity the TreasureHuntGameActivity
     */
    TreasureHuntGameActivityController(TreasureHuntGameActivity activity) {
        this.activity = activity;
        scoreBoard = ScoreBoard.getInstance();
        accountManager = AccountManager.getInstance();
        loadFromFile(TreasureHuntActivityController.getTempSaveFileName());
        createTileButtons(activity);
        tileButtons = createTileButtons(activity);
        autoSave();
    }

    /**
     * Default Constructor used for testing.
     */
    TreasureHuntGameActivityController() {
        this.activity = new MockContext();
        score = new TreasureHuntScore("Test name");
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
            Log.e("TreasureHuntGameActivity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("TreasureHuntGameActivity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("TreasureHuntGameActivity", "File contained unexpected data type: " +
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
                    activity.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.writeObject(score);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Updates the current state of the game
     *
     * @param arg the argument
     */
    void updateGame(Object arg) {
        if (arg instanceof String && arg.equals("swapped tiles")) {
            if (this.boardManager.puzzleSolved()) {
                score.countMove();
                score.hasWon();
                String gameName = "Treasure Hunt " + boardManager.getBoard().getBoardSize() + "x" + boardManager.getBoard().getBoardSize();
                scoreBoard.processScore(gameName, score);
                scoreBoard.saveScores(activity);
                AccountManager.getInstance().getCurrentUser().updateScore(gameName, score);
                saveToFile(TreasureHuntActivityController.getSaveFileName());
                saveToFile(TreasureHuntActivityController.getTempSaveFileName());
            } else {
                score.countMove();
            }
        }
        autoSave();
    }

    /**
     * Getter for board size
     *
     * @return the size of board
     */
    public int getBoardSize() {
        return boardManager.getBoard().getBoardSize();
    }

    /**
     * Getter for board manager
     *
     * @return the board manager
     */
    public TreasureHuntBoardManager getBoardManager() {
        return boardManager;
    }

    /**
     * Getter for the current score of the game
     *
     * @return the score
     */
    TreasureHuntScore getCurrentScore() {
        return score;
    }


    /**
     * Update the backgrounds on the buttons to match the tiles.
     *
     * @return the updated tile buttons for the board
     */
    List<Button> updateTileButtons() {
        TreasureHuntBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / board.getBoardSize();
            int col = nextPos % board.getBoardSize();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        return tileButtons;
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     * @return the newly created tile buttons for the board
     */
    private List<Button> createTileButtons(Context context) {
        TreasureHuntBoard board = boardManager.getBoard();
        List<Button> tileButtons = new ArrayList<>();
        for (int row = 0; row != board.getBoardSize(); row++) {
            for (int col = 0; col != board.getBoardSize(); col++) {
                Button tmp = new Button(context);

                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                tileButtons.add(tmp);
            }
        }
        return tileButtons;
    }

    /**
     * Method used to save the game
     */
    private void autoSave() {
        saveToFile(TreasureHuntActivityController.getSaveFileName());
        saveToFile(TreasureHuntActivityController.getTempSaveFileName());
        accountManager.saveUsers(activity);
    }

    /**
     * Check whether or not the undo button should be available
     *
     * @return whether or not the undo button is available
     */
    boolean isUndoAvailable() {
        return !this.boardManager.getUndoStack().empty() && !this.boardManager.puzzleSolved();

    }

    /**
     * Getter for the tile buttons
     *
     * @return the tile buttons
     */
    List<Button> getTileButtons() {
        return this.tileButtons;
    }
}
