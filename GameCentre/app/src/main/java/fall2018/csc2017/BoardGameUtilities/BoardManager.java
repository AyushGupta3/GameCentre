package fall2018.csc2017.BoardGameUtilities;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public interface BoardManager {

    /**
     * Return's a boolean representing the completion of the game.
     *
     * @return whether the puzzle has been completed.
     */
    boolean puzzleSolved();

    /**
     * Return whether the tile tapped at position is valid.
     *
     * @param position the position which was tapped.
     * @return whether the tile tapped at position is valid.
     */
    boolean isValidTap(int position);

    /**
     * Process a touch that was made at position
     *
     * @param position the position which was tapped.
     */
    void touchMove(int position);

}
