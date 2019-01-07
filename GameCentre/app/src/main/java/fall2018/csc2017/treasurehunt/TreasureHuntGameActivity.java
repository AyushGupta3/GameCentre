package fall2018.csc2017.treasurehunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;


import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.BoardGameUtilities.CustomAdapter;
import fall2018.csc2017.BoardGameUtilities.GestureDetectGridView;
import fall2018.csc2017.R;

public class TreasureHuntGameActivity extends AppCompatActivity implements Observer {

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;

    /**
     * The width and height of the tiles.
     */
    private static int columnWidth, columnHeight;

    /**
     * The TreasureHuntGameActivity controller
     */
    private TreasureHuntGameActivityController activityController;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setEnabled(activityController.isUndoAvailable());
        activityController.updateTileButtons();
        gridView.setAdapter(new CustomAdapter(activityController.getTileButtons(), columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityController = new TreasureHuntGameActivityController(this);
        setContentView(R.layout.activity_treasurehunt_game);

        // Add View to activity
        gridView = findViewById(R.id.treasureGrid);
        final int boardSize = activityController.getBoardManager().getBoard().getBoardSize();
        gridView.setNumColumns(boardSize);
        gridView.setBoardManager(activityController.getBoardManager());
        addUndoButtonListener();
        activityController.getBoardManager().getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardSize;
                        columnHeight = displayHeight / boardSize;

                        display();
                    }
                });
        updateText();
    }

    /**
     * Activate the undo button
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityController.getBoardManager().undo();
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        activityController.saveToFile(TreasureHuntActivityController.getTempSaveFileName());
    }


    /**
     * Update the display and score and save the game when the tiles are swapped.
     *
     * @param o   the object that this object observes.
     * @param arg the object passed in by the o.
     */
    @Override
    public void update(Observable o, Object arg) {
        activityController.updateGame(arg);
        updateText();
        display();
    }

    /**
     * Helper method to update the score text
     */
    public void updateText() {
        TextView scoreTextBox = findViewById(R.id.treasureHuntScore);
        String scoreText = "Current Score: " + activityController.getCurrentScore().getScore();
        scoreTextBox.setText(scoreText);
    }
}