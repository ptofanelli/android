package com.ptofanelli.sorteador.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.ptofanelli.sorteador.R;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ImageView chessImageView;
    private ImageView diceImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
    }

    private void bindViews()
    {
        chessImageView = (ImageView) findViewById(R.id.chess_imageview);
        chessImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLangDialog();
            }
        });

        diceImageView = (ImageView) findViewById(R.id.dice_imageview);
        diceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent diceRollsIntent = new Intent(MainActivity.this, DiceRollsActivity.class);
                startActivity(diceRollsIntent);
            }
        });


    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText minutesEditText = (EditText) dialogView.findViewById(R.id.minutes_edittext);
        final EditText secondsEditText = (EditText) dialogView.findViewById(R.id.seconds_edittext);
        final RadioButton timeByMove = (RadioButton)  dialogView.findViewById(R.id.time_by_move_radio);
        final RadioButton timeByMatch = (RadioButton)  dialogView.findViewById(R.id.time_by_match_radio);

        dialogBuilder.setTitle("Chess Clock");
        dialogBuilder.setMessage("Set the values below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                Intent chessClockIntent = new Intent(MainActivity.this, ChessClockActivity.class);

                if(timeByMove.isChecked()) {
                    chessClockIntent.putExtra(ChessClockActivity.EXTRA_TIME, ChessClockActivity.TIME_MODE_MOVE);
                } else {
                    chessClockIntent.putExtra(ChessClockActivity.EXTRA_TIME_MODE, ChessClockActivity.TIME_MODE_MATCH);
                }

                long seconds = 0;
                try {
                    seconds = TimeUnit.MINUTES.toSeconds(Integer.parseInt(minutesEditText.getText().toString()));
                } catch (Exception e) { }

                try {
                    seconds += Integer.parseInt(secondsEditText.getText().toString());
                } catch (Exception e) { }

                chessClockIntent.putExtra(ChessClockActivity.EXTRA_TIME, seconds);

                startActivity(chessClockIntent);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //TODO
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


}
