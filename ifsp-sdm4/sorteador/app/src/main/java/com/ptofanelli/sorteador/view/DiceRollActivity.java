package com.ptofanelli.sorteador.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ptofanelli.sorteador.R;
import com.ptofanelli.sorteador.model.DiceRollResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceRollActivity extends AppCompatActivity {

    public static final String EXTRA_DICE_COUNT = "dice_count";
    public static final String EXTRA_DICES_ROLL_RESULT = "dices_rolls_result";

    private Button diceRollButton;
    private TextView diceRollTextView;
    private Integer diceCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        diceCount = getIntent().getIntExtra(EXTRA_DICE_COUNT, 1);

        bindViews();
    }

    private void bindViews(){
        diceRollTextView = (TextView) findViewById(R.id.dice_roll_textview);

        diceRollButton = (Button) findViewById(R.id.dice_roll_button);
        diceRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDices();
            }
        });
    }

    private void rollDices() {
        List<Integer> rollResults = new ArrayList<Integer>();

        for (int i = 0; i < diceCount; i++) {
            Random random = new Random();
            Integer number = random.nextInt(6) + 1;
            rollResults.add(number);
            Log.i("DICE", "number: " + number);
        }

        DiceRollResult diceRollResult = new DiceRollResult();
        diceRollResult.setDicesRollResult(rollResults);

        diceRollTextView.setText(diceRollResult.toString());

        Intent result = new Intent();
        result.putExtra(EXTRA_DICES_ROLL_RESULT, diceRollResult);
        setResult(RESULT_OK, result);
    }
}
