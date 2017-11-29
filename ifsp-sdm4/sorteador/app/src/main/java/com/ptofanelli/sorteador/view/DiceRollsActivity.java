package com.ptofanelli.sorteador.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.ptofanelli.sorteador.R;
import com.ptofanelli.sorteador.adapter.DiceRollArrayAdapter;
import com.ptofanelli.sorteador.model.DiceRollResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DiceRollsActivity extends AppCompatActivity {

    private static final int DICE_ROLL_REQUEST_CODE = 1;

    private DiceRollArrayAdapter diceRollArrayAdapter;
    private List<DiceRollResult> diceRollResultList;

    private ListView diceRollsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_rolls);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiceRollSetupDialog();
            }
        });

        diceRollResultList = new ArrayList<DiceRollResult>();
        diceRollArrayAdapter = new DiceRollArrayAdapter(this, diceRollResultList);

        bindViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DICE_ROLL_REQUEST_CODE:
                if(resultCode == RESULT_OK) {
                    DiceRollResult rollResult = (DiceRollResult) data.getSerializableExtra(DiceRollActivity.EXTRA_DICES_ROLL_RESULT);
                    diceRollArrayAdapter.add(rollResult);
                }
                break;
        }
    }

    private void bindViews(){
        diceRollsListView = (ListView) findViewById(R.id.dice_rolls_listview);
        diceRollsListView.setAdapter(diceRollArrayAdapter);
    }

    public void showDiceRollSetupDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dice_roll_setup_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText diceCountEditText = (EditText) dialogView.findViewById(R.id.dice_count_edittext);

        dialogBuilder.setTitle("Roll Dice Setup");
        dialogBuilder.setMessage("Set the values below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                Intent diceRollIntent = new Intent(DiceRollsActivity.this, DiceRollActivity.class);


                int diceCount = 1;
                try {
                    diceCount = Integer.parseInt(diceCountEditText.getText().toString());
                } catch (Exception e)
                {

                }

                diceRollIntent.putExtra(DiceRollActivity.EXTRA_DICE_COUNT, diceCount);
                startActivityForResult(diceRollIntent, DICE_ROLL_REQUEST_CODE);
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
