package e.hippi.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartGameActivity extends AppCompatActivity {

    private int bestResult = 0;
    private TextView resultView;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        resultView = findViewById(R.id.bestResultTextView);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("?", "onClick: Start Game");
                Intent i = new Intent(StartGameActivity.this, GameScreenActivity.class);
                startActivityForResult(i, 100);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                int result = data.getIntExtra("result", 0);
                if (result > bestResult) {
                    bestResult = result;
                    resultView.setText("Your best result is:" + result);
                }
            }
        }
    }

}

