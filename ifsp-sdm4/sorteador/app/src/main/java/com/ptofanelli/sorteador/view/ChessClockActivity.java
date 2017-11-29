package com.ptofanelli.sorteador.view;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ptofanelli.sorteador.R;

import java.util.concurrent.TimeUnit;

public class ChessClockActivity extends AppCompatActivity {

    public static final short TIME_MODE_MOVE = 1;
    public static final short TIME_MODE_MATCH = 2;
    public static final String EXTRA_TIME = "time";
    public static final String EXTRA_TIME_MODE = "time-mode";

    private short timeMode;

    private long initialTime;
    private Handler handler;
    private boolean whiteTimerRunning;
    private boolean blackTimerRunning;
    private final long MILLIS_IN_SEC = 1000L;
    private final int SECS_IN_MIN = 60;

    private long duration;
    private long blackDuration;
    private long blackDurationRemaining;
    private long whiteDuration;
    private long whiteDurationRemaining;
    private TextView blackTextView;
    private TextView whiteTextView;
    private Button blackButton;
    private Button whiteButton;
    private Button startStopButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            getSupportActionBar().hide();
        } catch (Exception e) {}

        setContentView(R.layout.activity_chess_clock);

        bindViews();
        handler = new Handler();

        duration = getIntent().getLongExtra(EXTRA_TIME, 0);
        timeMode = getIntent().getShortExtra(EXTRA_TIME_MODE, TIME_MODE_MOVE);
        //duration = TimeUnit.MINUTES.toSeconds(1);

        setDuration();
    }

    private void bindViews() {
        blackTextView = (TextView) findViewById(R.id.black_textview);
        whiteTextView = (TextView) findViewById(R.id.white_textview);

        blackButton = (Button) findViewById(R.id.black_button);
        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopWhiteTimer();
                startStopBlackTimer();
            }
        });

        whiteButton = (Button) findViewById(R.id.white_button);
        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopBlackTimer();
                startStopWhiteTimer();
            }
        });

        startStopButton = (Button) findViewById(R.id.start_stop_button);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!whiteTimerRunning && !blackTimerRunning) {
                    startStopButton.setText(R.string.chess_clock_stop);
                    startStopButton.setBackgroundColor(Color.RED);
                    startStopWhiteTimer();
                } else {
                    startStopButton.setText(R.string.chess_clock_start);
                    startStopButton.setBackgroundColor(Color.GREEN);

                    if(whiteTimerRunning)
                    {
                        startStopWhiteTimer();
                    }

                    if(blackTimerRunning)
                    {
                        startStopBlackTimer();
                    }

                    setDuration();
                }
            }
        });
    }

    private void startStopWhiteTimer()
    {
        if(!whiteTimerRunning) {
            whiteTimerRunning = true;
            initialTime = System.currentTimeMillis();
            handler.postDelayed(whiteTimer, MILLIS_IN_SEC);
            whiteButton.setEnabled(true);
            whiteButton.setTextColor(Color.GREEN);
        }else{
            whiteTimerRunning = false;
            handler.removeCallbacks(whiteTimer);
            whiteButton.setEnabled(false);

            whiteButton.setTextColor(Color.BLACK);

            if(timeMode == TIME_MODE_MOVE) {
                whiteDuration = duration;
                whiteTextView.setText(String.format("%02d:%02d", whiteDuration / SECS_IN_MIN, whiteDuration % SECS_IN_MIN));
            } else {
                whiteDuration = whiteDurationRemaining;
            }
        }
    }

    private void startStopBlackTimer()
    {
        if(!blackTimerRunning) {
            blackTimerRunning = true;
            initialTime = System.currentTimeMillis();
            handler.postDelayed(blackTimer, MILLIS_IN_SEC);
            blackButton.setEnabled(true);
            blackButton.setTextColor(Color.GREEN);
        }else{
            blackTimerRunning = false;
            handler.removeCallbacks(blackTimer);
            blackButton.setEnabled(false);

            blackButton.setTextColor(Color.WHITE);

            if(timeMode == TIME_MODE_MOVE) {
                blackDuration = duration;
                blackTextView.setText(String.format("%02d:%02d", blackDuration / SECS_IN_MIN, blackDuration % SECS_IN_MIN));
            } else {
                blackDuration = blackDurationRemaining;
            }
        }
    }

    private void setDuration()
    {
        String initialTime = String.format("%02d:%02d",
                TimeUnit.SECONDS.toMinutes(duration),
                TimeUnit.SECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(duration))
        );

        blackTextView.setText(initialTime);
        whiteTextView.setText(initialTime);

        whiteDuration = duration;
        blackDuration = duration;
    }

    private final Runnable whiteTimer = new Runnable() {
        @Override
        public void run() {
            if (whiteTimerRunning) {
                long seconds = (System.currentTimeMillis() - initialTime) / MILLIS_IN_SEC;
                whiteDurationRemaining = whiteDuration - seconds;
                if(whiteDurationRemaining >= 0)
                {
                    whiteTextView.setText(String.format("%02d:%02d", whiteDurationRemaining / SECS_IN_MIN, whiteDurationRemaining % SECS_IN_MIN));
                    handler.postDelayed(whiteTimer, MILLIS_IN_SEC);
                } else {
                    // time out
                    startStopWhiteTimer();
                    startStopBlackTimer();
                }
            }
        }
    };


    private final Runnable blackTimer = new Runnable() {
        @Override
        public void run() {
            if (blackTimerRunning) {
                long seconds = (System.currentTimeMillis() - initialTime) / MILLIS_IN_SEC;
                blackDurationRemaining = blackDuration - seconds;
                if(blackDurationRemaining >= 0)
                {
                    blackTextView.setText(String.format("%02d:%02d", blackDurationRemaining / SECS_IN_MIN, blackDurationRemaining % SECS_IN_MIN));
                    handler.postDelayed(blackTimer, MILLIS_IN_SEC);
                } else {
                    // time out
                    startStopBlackTimer();
                    startStopWhiteTimer();
                }
            }
        }
    };
}
