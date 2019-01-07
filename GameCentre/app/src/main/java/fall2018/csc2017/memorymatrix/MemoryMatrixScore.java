package fall2018.csc2017.memorymatrix;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc2017.GameCentre.AccountManager;
import fall2018.csc2017.GameCentre.Score;

/**
 * The score of a user for the game Memory Matrix. Score is calculated by counting the number of
 * wrong tiles selected before the user wins the game. Lower scores are better.
 */
public class MemoryMatrixScore implements Score, Serializable {

    /**
     * The number of wrong moves that the user made.
     */
    private int numWrongMoves;

    /**
     * the string username of the user who this score belong to
     */
    private String scoreOwner;

    /**
     * Whether the score is for a completed game or not
     */
    private boolean hasWon;


    /**
     * A Constructor for MemoryMatrixScore
     */
    MemoryMatrixScore() {
        numWrongMoves = 0;
        hasWon = false;
        scoreOwner = AccountManager.getInstance().getCurrentUser().getUsername();
    }

    /**
     * A Constructor for MemoryMatrixScore used for testing
     */
    public MemoryMatrixScore(String scoreOwner) {
        numWrongMoves = 0;
        hasWon = false;
        this.scoreOwner = scoreOwner;
    }

    /**
     * Return the user's score.
     *
     * @return the user's score.
     */
    @Override
    public int getScore() {
        return numWrongMoves;
    }

    /**
     * Increase the number of wrong moves that the user just made by one if the user hasn't won.
     */
    void countMove() {
        if (!hasWon)
            numWrongMoves++;
    }

    /**
     * Return a negative integer, zero, or a positive integer if the score that this object
     * represents is better than, equal to, or worse than the o's score.
     *
     * @param o the score object to be compared.
     * @return a negative integer, zero, or a positive integer as this object's score is better
     * than, equal to, or worse than the o's score.
     */
    @Override
    public int compareTo(@NonNull Score o) {
        if (o instanceof MemoryMatrixScore)
            return Integer.compare(getScore(), o.getScore());
        else
            return 0;
    }

    /**
     * Call this method when the user has won the game to update the best score and stop this score
     * from changing.
     */
    public void hasWon() {
        this.hasWon = true;
    }

    /**
     * Retrieve the username of the owner of the score
     *
     * @return the string username of the owner of this score
     */
    String getScoreOwner() {
        return scoreOwner;
    }
}