package com.example.finalapp;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class StartActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 240000;

    private TextView mTextViewCountDown;
    private TextView mTextViewExerciseName;
    private TextView mTextViewCurrentCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private TextView mTextViewPhase;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private String phase = "work";
    private String currentTimetLeftFormatted;
    private int exerciseNumber = 8;
    private ArrayList exercises = new ArrayList();
    private ListView listView;
    private ListAdapter listAdapter;
    private String currentExercise;
    private ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);



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
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mTextViewPhase = findViewById(R.id.textView_phase);
        mTextViewPhase.setText(phase);

        mTextViewExerciseName = findViewById(R.id.textView_current_exercise);
        currentExercise = exercises.get(0).toString();
        mTextViewExerciseName.setText(currentExercise);
        exercises.add(exercises.get(0));
        exercises.remove(0);




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
                phase = "work";
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
        exerciseNumber=8;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        int seconds30 = (int) (mTimeLeftInMillis / 1000) % 30;


        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        if(phase=="work") {
            if(seconds == 0){
                currentTimetLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", 0, seconds30);
            }else {
                currentTimetLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", 0, seconds30 - 10);
            }
        }else if(phase=="rest"){
            currentTimetLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", 0, seconds30);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
        mTextViewCurrentCountDown.setText(currentTimetLeftFormatted);
        mTextViewExerciseName.setText(currentExercise);
        mTextViewPhase.setText(phase);


        if(seconds == 0 || seconds == 30){
            phase = "work";
            //ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        }else if (seconds == 10 || seconds == 40){
            phase = "rest";
            //ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        }else if (seconds == 1 || seconds == 31){
            currentExercise=exercises.get(0).toString();
            exercises.add(exercises.get(0));
            exercises.remove(0);
            listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,exercises);
            listView.setAdapter(listAdapter);
            exerciseNumber--;
            if(exerciseNumber==0){
                exerciseNumber=8;
            }
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        toneGen1 = null;

    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();

    }
}

