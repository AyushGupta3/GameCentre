package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The ScoreBoard class handles the best scores across all players
 */
public class ScoreBoard {

    /**
     * A static variable storing an instance of the ScoreBoard following
     * the singleton design pattern
     */
    private static ScoreBoard scoreBoardInstance;

    /**
     * A map that maps game titles to the best score for each respective game
     */
    private Map<String, Score> scoreList = new HashMap<>();

    /**
     * The name of the file that saves the ScoreBoard object
     */
    private final String fileName = "ScoreBoard_save_file.ser";

    /**
     * Private default constructor so that no new instance can be created
     */
    private ScoreBoard() {
    }

    /**
     * A public static method meant to return the instance of the ScoreBoard object in
     * accordance to the singleton design pattern
     *
     * @return ScoreBoard instance
     */
    public static ScoreBoard getInstance() {
        if (null == scoreBoardInstance) {
            scoreBoardInstance = new ScoreBoard();
        }
        return scoreBoardInstance;
    }

    /**
     * Given a game title and a new score, processes the score and decides whether it is the
     * new best score
     *
     * @param game     the name of the game
     * @param newScore the new score
     */
    public void processScore(String game, Score newScore) {
        if (scoreList.containsKey(game)) {
            if (scoreList.get(game).getScore() > newScore.getScore()) {
                scoreList.put(game, newScore);
            }
        } else {
            scoreList.put(game, newScore);
        }
    }

    /**
     * Return the best score given a game title
     *
     * @param game the name of the game
     * @return best score for game
     */
    public Score getScore(String game) {
        return scoreList.get(game);
    }

    /**
     * Saves the instance of ScoreBoard
     *
     * @param context the context of the current application.
     */
    public void saveScores(Context context) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(scoreList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Loads the instance of ScoreBoard onto the instance
     *
     * @param context the context of the current application.
     */
    @SuppressWarnings("unchecked")
    public void loadScores(Context context) {
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                scoreList = (Map<String, Score>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("ScoreBoard", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("ScoreBoard", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("ScoreBoard", "File contained unexpected data type: " +
                    e.toString());
        }
    }
}
