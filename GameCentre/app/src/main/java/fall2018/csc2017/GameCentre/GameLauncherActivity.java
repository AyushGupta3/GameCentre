package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc2017.R;


/**
 * The game launcher activity that represents the game options and high scores
 */
public class GameLauncherActivity extends AppCompatActivity {

    /**
     * The GameLauncher controller
     */
    GameLauncherActivityController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new GameLauncherActivityController(this);
        setContentView(R.layout.activity_gamelauncher);
        addSlidingTilesButtonListener();
        addChangePasswordButtonListener();
        addMemoryMatrixButtonListener();
        addTreasureHuntButtonListener();
        updateScores(controller.getNewScores());
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateScores(controller.getNewScores());
    }

    /**
     * Update the scores of the game of the current user
     *
     * @param newScores the new scores of the current user
     */
    public void updateScores(String newScores) {
        TextView textView = findViewById(R.id.scoreDisplayView);
        textView.setText(newScores);
    }

    /**
     * Activate the treasure hunt button.
     */
    private void addTreasureHuntButtonListener() {
        Button startButton = findViewById(R.id.treasureHuntButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.switchToTreasureHunt();
            }
        });
    }

    /**
     * Activate the sliding tiles button.
     */
    private void addSlidingTilesButtonListener() {
        Button startButton = findViewById(R.id.SlidingTilesButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.switchToSlidingTiles();
            }
        });
    }

    /**
     * Activate the memory matrix button.
     */
    private void addMemoryMatrixButtonListener() {
        Button startButton = findViewById(R.id.memoryMatrixButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.switchToMemoryMatrix();
            }
        });
    }

    /**
     * Activate the change password button
     */
    private void addChangePasswordButtonListener() {
        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        final EditText newPassword = findViewById(R.id.newPasswordEditText);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.changePasswordRequest(newPassword.getText().toString());
                makeToastPasswordChange();
            }
        });
    }

    /**
     * Create a toast that alerts user of a successfully changed password
     */
    private void makeToastPasswordChange() {
        Toast.makeText(this, "Password Successfully Changed", Toast.LENGTH_SHORT).show();
    }
}
