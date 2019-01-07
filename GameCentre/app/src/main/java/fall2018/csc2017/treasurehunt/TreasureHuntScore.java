package fall2018.csc2017.treasurehunt;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc2017.GameCentre.AccountManager;
import fall2018.csc2017.GameCentre.Score;

/**
 * The score of a Treasure Hunt game. The score is calculated by counting the number of moves that
 * the user made. Lower scores are better.
 */
class TreasureHuntScore implements Score, Serializable {

    /**
     * The owner of the score
     */
    private String scoreOwner;

    /**
     * The number of moves that the user made.
     */
    private int numMoves;

    /**
     * Whether or not the user has won the game.
     */
    private boolean hasWon;

    /**
     * A Constructor for TreasureHuntScore.
     */
    TreasureHuntScore() {
        numMoves = 0;
        hasWon = false;
        scoreOwner = AccountManager.getInstance().getCurrentUser().getUsername();
    }

    /**
     * A constructor meant for testing purposes
     *
     * @param owner the string used for testing purposes
     */
    TreasureHuntScore(String owner) {
        numMoves = 0;
        hasWon = false;
        scoreOwner = owner;
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
        if (o instanceof TreasureHuntScore)
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
     * Return the owner of the score
     *
     * @return a string of the username holding that score
     */
    String getScoreOwner() {
        return scoreOwner;
    }
}
