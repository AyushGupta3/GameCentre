package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A user of GameCentre
 */
public class User implements Serializable {

    /**
     * The user name of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;
    /**
     * The highest scores that the user has achieved. The key is the name of a game, and the value
     * corresponding to the key is the highest score that the user achieved in that game.
     */
    private Map<String, Score> scores;

    /**
     * The name of the games that the user has saved.
     */
    private Set<String> hasSavedGame;

    /**
     * Constructor for user
     *
     * @param username Username of the User
     * @param password Password of the User
     */
    User(String username, String password) {
        this.username = username;
        this.password = password;
        scores = new HashMap<>();
        hasSavedGame = new HashSet<>();
    }

    /**
     * Returns the user's username
     *
     * @return the username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the user's password
     *
     * @return the password of the user
     */
    String getPassword() {
        return this.password;
    }

    /**
     * Update user's highest score in game.
     *
     * @param game  the name of the game.
     * @param score the score that the user achieved in a game.
     */
    public void updateScore(String game, Score score) {
        if (scores.get(game) == null || scores.get(game).compareTo(score) > 0)
            scores.put(game, score);
    }

    /**
     * Return a list of user's scores.
     *
     * @return a list of user's scores.
     */
    String getListOfScores() {
        StringBuilder list = new StringBuilder();
        for (Map.Entry<String, Score> score : scores.entrySet()) {
            list.append(score.getKey()).append(": ").append(score.getValue().getScore()).append("\n");
        }
        return list.toString();
    }

    /**
     * Add a game to the list of the games that the user has saved files for.
     */
    public void addSavedGame(String gameName) {
        hasSavedGame.add(gameName);
    }

    /**
     * Check if the user has saved files for a game.
     *
     * @return true if the user has saved files for a game and false otherwise.
     */
    public boolean checkSavedGame(String gameName) {
        return hasSavedGame.contains(gameName);
    }

    /**
     * Sets the value of password
     */
    void setPassword(String password) {
        this.password = password;
    }
}