package com.example.finalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent1 = new Intent(this, AddExerciseActivity.class);
        Button button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                startActivity(intent1);
            }
        });

        final Intent intent2 = new Intent(this,CreateWorkoutActivity.class);
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                startActivity(intent2);
            }
        });


    }
}
