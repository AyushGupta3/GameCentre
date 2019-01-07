package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fall2018.csc2017.R;


/**
 * Represents the sign in activity.
 */
public class SignInActivity extends AppCompatActivity {

    /**
     * The controller for this activity.
     */
    private SignInActivityController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new SignInActivityController(this);
        setContentView(R.layout.activity_signin);
        addSignInButtonListener();
        addSignUpButtonListener();
    }

    /**
     * Activate the sign in button.
     */
    private void addSignInButtonListener() {
        Button saveButton = findViewById(R.id.signIn);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.signIn(username.getText().toString(), password.getText().toString());
            }
        });
    }

    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        Button saveButton = findViewById(R.id.signUp);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.signUp(username.getText().toString(), password.getText().toString());
            }
        });
    }
}
