package fall2018.csc2017.treasurehunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import fall2018.csc2017.R;

/**
 * The activity for the menu of the game Treasure Hunt.
 */
public class TreasureHuntActivity extends AppCompatActivity {

    /**
     * The controller for this activity.
     */
    private TreasureHuntActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new TreasureHuntActivityController(this);
        setContentView(R.layout.activity_treasurehunt);
        addStartButtonListener();
        addLoadButtonListener();
        addSizeSelectorListener();
        updateBestScore();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.newTreasureHunt);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.loadTreasureHuntButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.loadGame();
            }
        });
    }

    /**
     * Activate the selector for selecting board size.
     */
    private void addSizeSelectorListener() {
        NumberPicker sizeSelector = findViewById(R.id.treasureboardSizePicker);
        sizeSelector.setWrapSelectorWheel(false);
        sizeSelector.setMinValue(8);
        sizeSelector.setMaxValue(10);
        sizeSelector.setValue(9);
        sizeSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                controller.changeSelectedBoardSize(newVal);
                updateBestScore();
            }
        });
    }

    /**
     * Read the temporary board from disk and update the best score.
     */
    @Override
    protected void onResume() {
        super.onResume();
        controller.resume();
        updateBestScore();
    }

    /**
     * Update the text boxes that display the best score for this game.
     */
    void updateBestScore() {
        TextView userWithBestScore = findViewById(R.id.userWithBestScore);
        TextView bestScore = findViewById(R.id.bestScore);
        TextView bestScoreDescription = findViewById(R.id.bestScoreDescription);
        String[] texts = controller.getBestScore();
        bestScoreDescription.setText(texts[0]);
        userWithBestScore.setText(texts[1]);
        bestScore.setText(texts[2]);
    }
}
