package fall2018.csc2017.slidingtiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.BoardGameUtilities.CustomAdapter;
import fall2018.csc2017.BoardGameUtilities.GestureDetectGridView;
import fall2018.csc2017.R;

/**
 * The game activity.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer {

    /**
     * The SlidingTilesGameActivity controller
     */
    private SlidingTilesGameActivityController controller;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;

    /**
     * The width and height of the tiles.
     */
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        Button undoButton = findViewById(R.id.undoButton);
        if (controller.getBoardManager().getUndoStack().empty() || controller.getBoardManager().puzzleSolved()) {
            undoButton.setEnabled(false);
        } else {
            undoButton.setEnabled(true);
        }
        controller.updateTileButtons();
        gridView.setAdapter(new CustomAdapter(controller.getTileButtons(), columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingtiles_game);
        controller = new SlidingTilesGameActivityController(this);
        controller.getBoardManager().getBoard().addObserver(this);
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(controller.getBoardSize());
        gridView.setBoardManager(controller.getBoardManager());
        addUndoButtonListener();

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
        controller.autoSave();
        updateScoreText();
    }


    /**
     * Activate the undo button
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.getBoardManager().undo();
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        controller.saveToFile(SlidingTilesActivityController.getTempSaveFileName());
    }

    /**
     * Update the display and score and save the game when the tiles are swapped.
     *
     * @param o   the object that this object observes.
     * @param arg the object passed in by the o.
     */
    @Override
    public void update(Observable o, Object arg) {
        controller.updateGame();
        updateScoreText();
        controller.autoSave();
        display();
    }

    /**
     * update the displayed score.
     */
    void updateScoreText() {
        TextView scoreTextBox = findViewById(R.id.currentScore);
        String scoreText = "Current Score: " + controller.getScore();
        scoreTextBox.setText(scoreText);
    }

}

