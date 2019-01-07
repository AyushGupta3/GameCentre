package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;


import java.io.Serializable;

import fall2018.csc2017.GameCentre.AccountManager;
import fall2018.csc2017.GameCentre.Score;

/**
 * The score of a user for the game Sliding Tiles. Score is calculated by counting the number of
 * moves that the user made to win the game, and the score is lower the better.
 */
public class SlidingTilesScore implements Score, Serializable {

    /**
     * The username of the scoreOwner
     */
    private String scoreOwner;

    /**
     * Retrieve the username of the scoreOwner
     *
     * @return the scoreOwner
     */
    String getScoreOwner() {
        return scoreOwner;
    }

    /**
     * The number of moves that the user made.
     */
    private int numMoves;


    /**
     * Whether or not the user has won the game.
     */
    private boolean hasWon;


    /**
     * A Constructor for SlidingTilesScore.
     */
    SlidingTilesScore(String scoreOwner) {
        numMoves = 0;
        hasWon = false;
        this.scoreOwner = scoreOwner;
    }

    /**
     * A Constructor for SlidingTilesScore.
     */
    SlidingTilesScore() {
        numMoves = 0;
        hasWon = false;
        scoreOwner = AccountManager.getInstance().getCurrentUser().getUsername();
    }

    /**
     * Return the user's score.
     *
     * @return the user's score.
     */
    @Override
    public int getScore() {
        return numMoves;
    }

    /**
     * Increase the number of moves that the user just made by one if the user hasn't won.
     */
    void countMove() {
        if (!hasWon)
            numMoves++;
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
        if (o instanceof SlidingTilesScore)
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
}
