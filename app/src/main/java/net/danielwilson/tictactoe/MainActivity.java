package net.danielwilson.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //0 = chihuahua, 1 = frenchie
    int activePlayer = 0;
    boolean gameIsActive = true;
    RelativeLayout layout;
    GridLayout playingLayout;
    TextView winnerText;
    ImageView winnerImage;
    int[] boardState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void checkWinner() {
        for(int[] winningPosition : winningPositions) {
            if(boardState[winningPosition[0]] == boardState[winningPosition[1]] &&
                    boardState[winningPosition[1]] == boardState[winningPosition[2]] && boardState[winningPosition[0]] != 2) {
                Log.i("Info","Player " + boardState[winningPosition[0]] + " has won!");

                gameIsActive = false;
                playingLayout.animate().alpha(0.2f).setDuration(500);
                winnerText.setText("PLAYER " + (boardState[winningPosition[0]] + 1) + " WINS!");
                if(boardState[winningPosition[0]] == 0) {
                    winnerImage.setImageResource(R.drawable.chihuahua_icon);
                } else {
                    winnerImage.setImageResource(R.drawable.frenchie_icon);
                }
                layout.setVisibility(View.VISIBLE);
            } else {
                boolean gameIsOver = true;
                for(int counterState : boardState) {
                    if(counterState == 2) gameIsOver = false;
                }
                if(gameIsOver) {
                    playingLayout.animate().alpha(0.2f).setDuration(500);
                    winnerText.setText("TIED GAME!");
                    layout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {

        layout.setVisibility(View.INVISIBLE);
        playingLayout.animate().alpha(1f);
        winnerText.setText("");
        activePlayer = 0;
        gameIsActive = true;
        //reset board
        for (int i = 0; i < boardState.length; i++) {
            boardState[i] = 2;
        }
        //reset images
        for (int i = 0; i < playingLayout.getChildCount(); i++) {
            ((ImageView) playingLayout.getChildAt(i)).setImageResource(0);
        }
        winnerImage.setImageResource(0);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        Log.i("Info","Board placement: " + counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(boardState[tappedCounter] == 2 && gameIsActive) {
            boardState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if(activePlayer == 0) {
                counter.setImageResource(R.drawable.chihuahua_icon);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.frenchie_icon);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).setDuration(300);
            counter.animate().rotation(360).setDuration(1000);
            checkWinner();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (RelativeLayout) findViewById(R.id.winningLayout);
        playingLayout = (GridLayout) findViewById(R.id.playingLayout);
        winnerText = (TextView) findViewById(R.id.winningTextView);
        winnerImage = (ImageView) findViewById(R.id.winningImageView);
    }
}
