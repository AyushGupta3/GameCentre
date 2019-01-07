package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import fall2018.csc2017.R;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTilesActivity extends AppCompatActivity {

    /**
     * The sliding tiles controller.
     */
    SlidingTilesActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new SlidingTilesActivityController(this);
        setContentView(R.layout.activity_slidingtiles);
        addStartButtonListener();
        addLoadButtonListener();
        addSizeSelectorListener();
        updateBestScore();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {

        Button startButton = findViewById(R.id.StartButton);
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
        Button loadButton = findViewById(R.id.LoadButton);
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
        NumberPicker sizeSelector = findViewById(R.id.boardSizePicker);
        sizeSelector.setWrapSelectorWheel(false);
        sizeSelector.setMinValue(3);
        sizeSelector.setMaxValue(5);
        sizeSelector.setValue(4);
        controller.changeSelectedBoardSize(4);
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
