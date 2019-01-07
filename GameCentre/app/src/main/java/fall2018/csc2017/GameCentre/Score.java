package fall2018.csc2017.GameCentre;

/**
 * Score stores user's score in a game.
 */
public interface Score extends Comparable<Score> {

    /**
     * Return the user's score.
     *
     * @return the user's score.
     */
    int getScore();

    /**
     * Return whether the user has won.
     */
    void hasWon();
}
