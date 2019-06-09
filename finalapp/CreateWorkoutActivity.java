package com.example.finalapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class CreateWorkoutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mEditTextName;
    private TextView mTextViewAmount;
    private String mGroup;
    ArrayList<String> chosenExercises = new ArrayList<>();
    int i=0;
    ArrayList<String> theList = new ArrayList<>();
    Spinner spinnergroup;
    String[] groupname={"All", "Legs and Butt", "Stomach", "Arms", "Back", "Chest", "Butt", "Full Body Workout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        spinnergroup=(Spinner)findViewById(R.id.spinnergroup);
        spinnergroup.setOnItemSelectedListener(this);
        ArrayAdapter adapterspinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, groupname);
        adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergroup.setAdapter(adapterspinner);
        String group = spinnergroup.getSelectedItem().toString();


        DBHelper dbHelper = new DBHelper(this);
        ListView listView = (ListView)findViewById(R.id.listview);
        Cursor data = dbHelper.getListContents();
        if(data.getCount()==0){
            Toast.makeText(this, "The exercise list is empty", Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));

            }
            ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
            listView.setAdapter(listAdapter);
        }

        Button buttonFilter = findViewById(R.id.button_add);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CreateWorkoutActivity.this, theList.get(position), Toast.LENGTH_SHORT).show();
                chosenExercises.add(theList.get(position));
                i++;
                if(i==8){
                    i=0;
                    finishAdding();
                }
            }
        });


    }

    private void finishAdding(){
        Bundle args = new Bundle();
        ArrayList toSend = new ArrayList<>(chosenExercises);
        chosenExercises.clear();
        args.putSerializable("ARRAYLIST",(Serializable)toSend);
        final Intent intent1 = new Intent(this, StartActivity.class);
        intent1.putExtra("BUNDLE",args);
        startActivity(intent1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}