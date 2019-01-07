package fall2018.csc2017.BoardGameUtilities;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import android.annotation.SuppressLint;

/**
 * The controller for determining whether a user's tap corressponds to a valid move or not
 */
class MovementController {

    /**
     * the current BoardManager
     */
    private BoardManager boardManager = null;

    /**
     * default constructor
     */
    MovementController() {
    }

    /**
     * Sets this MovementController's boardManager
     *
     * @param boardManager the BoardManager to be set
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Processes whether a tap is a valid move or not
     *
     * @param context  the context of the app
     * @param position the position that was tapped
     */
    void processTapMovement(Context context, int position) {
        if (!boardManager.puzzleSolved() && !boardManager.isValidTap(position)) {
            makeToastInvalidTap(context);
        } else if (!boardManager.puzzleSolved()) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN! ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * generate and display the toast for an invalid tap
     *
     * @param context the context of the app
     */
    @SuppressLint("RtlHardcoded")
    private void makeToastInvalidTap(Context context) {
        Toast toast = Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 20, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
