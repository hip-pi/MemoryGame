package e.hippi.memorygame;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isShowing = false, gameLost = false;
    private int currentScore, currentIndex = 0;
    private List<Integer> buttonList = new ArrayList<>();
    private Button[] buttons;
    private int[] button_ids = {R.id.firstButton, R.id.secondButton, R.id.thirdButton, R.id.fourthButton, R.id.fifthButton,
                                R.id.sixthButton, R.id.seventhButton, R.id.eighthButton, R.id.ninethButton};
    private int delayValue = 300;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttons = new Button[9];
        setContentView(R.layout.activity_game_screen);
        for (int i = 0; i < 9; i++) {
            buttons[i] = findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }
        random = new Random();
        for (int i = 0; i < 3; i++) {
            createNextNumber();
        }
        setResult(RESULT_CANCELED);
        GameLogic temp = new GameLogic();
        temp.execute(null,null,null);
    }

    private void createNextNumber() {
        int temp = random.nextInt(9) + 1;
        buttonList.add(temp);
    }

    @Override
    public void onClick(View v) {
        if (isShowing) {
            return;
        }

        if (v.getId() != button_ids[buttonList.get(currentIndex)-1]) {
            gameLost = true;
            Toast.makeText(this, "Sorry, you lost!!! Your score:"+currentScore, Toast.LENGTH_SHORT).show();
            Intent data = new Intent();
            data.putExtra("result", currentScore);
            setResult(RESULT_OK, data);
            finish();
        } else {
            currentIndex++;
            if (currentIndex == buttonList.size()) {
                currentScore++;
                createNextNumber();
                currentIndex = 0;
                Toast.makeText(this, "Next round", Toast.LENGTH_SHORT).show();
                GameLogic temp = new GameLogic();
                temp.execute(null, null, null);
            }
        }
    }

    private class GameLogic extends AsyncTask<Void, Void, Void> {

        private boolean isRed = false;
        private int currentButton = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                finish();
            }

            for (int i = 0; i < buttonList.size(); i++) {
                currentButton = buttonList.get(i);
                isRed = true;
                publishProgress();
                try {
                    Thread.sleep(delayValue);
                } catch (InterruptedException e) {
                    finish();
                }
                isRed = false;
                publishProgress();
                try {
                    Thread.sleep(delayValue);
                } catch (InterruptedException e) {
                    finish();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Button temp = findViewById(button_ids[currentButton-1]);
            if (isRed) {
                temp.setBackgroundColor(getResources().getColor(R.color.gameRed, null));
            } else {
                temp.setBackgroundColor(getResources().getColor(R.color.gameGreen, null));
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isShowing = true;
            Toast.makeText(GameScreenActivity.this, "Memorize this", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isShowing = false;
            Toast.makeText(GameScreenActivity.this, "Your turn", Toast.LENGTH_SHORT).show();
        }
    }
}

