package fall2018.csc2017.memorymatrix;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.ViewTreeObserver;
import android.widget.TextView;


import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.BoardGameUtilities.CustomAdapter;
import fall2018.csc2017.BoardGameUtilities.GestureDetectGridView;

import fall2018.csc2017.R;

/**
 * The game activity.
 */
public class MemoryMatrixGameActivity extends AppCompatActivity implements Observer {

    /**
     * MemoryMatrixGameActivity controller
     */
    private MemoryMatrixGameActivityController controller;

    /**
     * A gridview object, essentially the display of the game
     */
    private GestureDetectGridView gridView;

    /**
     * columnWidth and columnHeight of the gridview
     */
    private int columnWidth, columnHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memorymatrix_game);
        controller = new MemoryMatrixGameActivityController(this);

        // Add View to activity
        gridView = findViewById(R.id.memoryGrid);
        gridView.setNumColumns(controller.getBoardSize());
        gridView.setBoardManager(controller.getBoardManager());
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();
                        columnWidth = controller.getColumnWidth(displayWidth);
                        columnHeight = controller.getColumnHeight(displayHeight);
                        display();
                    }
                });
        controller.getBoardManager().getBoard().addObserver(this);
        controller.getBoardManager().addObserver(this);

        updateScoreText();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        controller.saveToFile(MemoryMatrixActivityController.getTempSaveFileName());
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        if (controller.getShowSolved()) {
            controller.getBoardManager().getBoard().swapBoardsForDisplay();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    controller.getBoardManager().getBoard().swapBoardsForDisplay();
                    display();
                }
            }, 5000);
            controller.setShowSolvedFalse();
        }
        controller.updateTileButtons();
        gridView.setAdapter(new CustomAdapter(controller.getTileButtons(), columnWidth, columnHeight));
    }

    @Override
    public void update(Observable o, Object arg) {
        controller.updateIfGameFinished();
        updateScoreText();
        display();
    }

    /**
     * Helper to update the displayed score.
     */
    private void updateScoreText() {
        TextView scoreTextBox = findViewById(R.id.currentScore);
        String scoreText = "Current Score: " + controller.getScore();
        scoreTextBox.setText(scoreText);
    }
}

