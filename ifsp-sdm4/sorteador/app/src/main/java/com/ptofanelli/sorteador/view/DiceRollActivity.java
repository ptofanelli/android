package com.ptofanelli.sorteador.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    private ImageView dice1ImageView;
    private ImageView dice2ImageView;
    private ImageView dice3ImageView;
    private ImageView dice4ImageView;
    private ImageView dice5ImageView;
    private ImageView dice6ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        diceCount = getIntent().getIntExtra(EXTRA_DICE_COUNT, 1);

        diceCount = diceCount < 1 ? 1 : diceCount;
        diceCount = diceCount > 6 ? 6 : diceCount;

        bindViews();
    }

    private void bindViews() {
        diceRollTextView = (TextView) findViewById(R.id.dice_roll_textview);

        diceRollButton = (Button) findViewById(R.id.dice_roll_button);
        diceRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDices();
            }
        });

        dice1ImageView = (ImageView) findViewById(R.id.dice1_imageview);
        dice2ImageView = (ImageView) findViewById(R.id.dice2_imageview);
        dice3ImageView = (ImageView) findViewById(R.id.dice3_imageview);
        dice4ImageView = (ImageView) findViewById(R.id.dice4_imageview);
        dice5ImageView = (ImageView) findViewById(R.id.dice5_imageview);
        dice6ImageView = (ImageView) findViewById(R.id.dice6_imageview);
    }

    private void rollDices() {
        List<Integer> rollResults = new ArrayList<Integer>();

        for (int i = 0; i < diceCount; i++) {


            Random random = new Random();
            Integer number = random.nextInt(6) + 1;
            rollResults.add(number);
            Log.i("DICE", "number: " + number);
            setDiceImage(getDiceImageView(i), number);
        }

        DiceRollResult diceRollResult = new DiceRollResult();
        diceRollResult.setDicesRollResult(rollResults);

        diceRollTextView.setText(diceRollResult.toString() + " = " + diceRollResult.getTotal());

        Intent result = new Intent();
        result.putExtra(EXTRA_DICES_ROLL_RESULT, diceRollResult);
        setResult(RESULT_OK, result);
    }

    private void setDiceImage(ImageView imageView, int result) {
        switch (result) {
            case 1:
                imageView.setImageDrawable(getDrawable(R.mipmap.dice_1));
                break;
            case 2:
                imageView.setImageDrawable(getDrawable(R.mipmap.dice_2));
                break;
            case 3:
                imageView.setImageDrawable(getDrawable(R.mipmap.dice_3));
                break;
            case 4:
                imageView.setImageDrawable(getDrawable(R.mipmap.dice_4));
                break;
            case 5:
                imageView.setImageDrawable(getDrawable(R.mipmap.dice_5));
                break;
            case 6:
                imageView.setImageDrawable(getDrawable(R.mipmap.dice_6));
                break;
        }
    }

    private ImageView getDiceImageView(int index) {
        switch (index) {
            case 0:
                return dice1ImageView;
            case 1:
                return dice2ImageView;
            case 2:
                return dice3ImageView;
            case 3:
                return dice4ImageView;
            case 4:
                return dice5ImageView;
            case 5:
                return dice6ImageView;
        }

        return null;
    }
}
