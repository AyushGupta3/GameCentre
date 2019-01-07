package fall2018.csc2017.memorymatrix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import fall2018.csc2017.R;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class MemoryMatrixActivity extends AppCompatActivity {

    /**
     * MemoryMatrixActivity controller
     */
    private MemoryMatrixActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new MemoryMatrixActivityController(this);
        setContentView(R.layout.activity_memorymatrix);
        addStartButtonListener();
        addSizeSelectorListener();
        updateBestScore();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.memoryMatrixNew);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startGame();
            }
        });
    }

    /**
     * Activate the selector for selecting board size.
     */
    private void addSizeSelectorListener() {
        NumberPicker sizeSelector = findViewById(R.id.memoryMatrixBoardSizePicker);
        sizeSelector.setWrapSelectorWheel(false);

        sizeSelector.setMinValue(5);
        sizeSelector.setMaxValue(8);
        sizeSelector.setValue(6);
        sizeSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                controller.changeSelectedBoardSize(newVal);
                updateBestScore();
            }
        });
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        controller.resume();
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

    /**
     * Update the display after this activity is resumed.
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateBestScore();
    }
}