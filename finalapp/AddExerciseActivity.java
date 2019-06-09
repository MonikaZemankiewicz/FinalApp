package com.example.finalapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddExerciseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SQLiteDatabase mDatabase;
    private ExerciseAdapter mAdapter;
    private EditText mEditTextName;;
    DBHelper dbHelper;
    Spinner spinnergroup;
    String[] groupname={"Legs and Butt", "Stomach", "Arms", "Back", "Chest", "Butt", "Full Body Workout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        spinnergroup=(Spinner)findViewById(R.id.spinnergroup);
        spinnergroup.setOnItemSelectedListener(this);
        ArrayAdapter adapterspinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, groupname);
        adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergroup.setAdapter(adapterspinner);

        dbHelper = new DBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        mEditTextName = findViewById(R.id.edittext_name);
        Button buttonAdd = findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

    }

    private void addItem() {

        if (mEditTextName.getText().toString().trim().length() == 0) {
            return;
        }


        String name = mEditTextName.getText().toString();
        String group = spinnergroup.getSelectedItem().toString();
        //ContentValues cv = new ContentValues();
        //cv.put(ExerciseContract.ExerciseEntry.COLUMN_NAME, name);
        //cv.put(ExerciseContract.ExerciseEntry.COLUMN_GROUP, group);
        boolean insertData = dbHelper.addData(name, group);
        if(insertData==true){
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }

        //mDatabase.insert(ExerciseContract.ExerciseEntry.TABLE_NAME, null, cv);
        mEditTextName.getText().clear();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
