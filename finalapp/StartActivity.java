package com.example.finalapp;

import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class StartActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 240000;

    private TextView mTextViewCountDown;
    private TextView mExerciseName;
    private TextView mTextViewCurrentCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private String phase = "workout";
    private String currentTimetLeftFormatted;
    private int exerciseNumber = 0;
    private ArrayList exercises = new ArrayList();
    private ListView listView;
    private ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        exercises = (ArrayList<Object>) args.getSerializable("ARRAYLIST");
        listView = (ListView)findViewById(R.id.listview);
        listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,exercises);
        listView.setAdapter(listAdapter);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mTextViewCurrentCountDown = findViewById(R.id.text_view_current_countdown);

        mExerciseName = findViewById(R.id.textView_current_exercise);
        mExerciseName.setText(exercises.get(0).toString());

        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);

    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        exerciseNumber=0;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;


        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        if(phase=="workout") {
            currentTimetLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", 0, seconds % 30);
        }else if(phase=="rest"){
            currentTimetLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", 0, seconds % 10);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
        mTextViewCurrentCountDown.setText(currentTimetLeftFormatted);
        mExerciseName.setText(exercises.get(exerciseNumber).toString());

        if(seconds == 0 || seconds == 30){
            phase = "workout";
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        }else if (seconds == 10 || seconds == 40){
            phase = "rest";
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        }else if (seconds == 1 || seconds == 31){
            exerciseNumber++;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}

