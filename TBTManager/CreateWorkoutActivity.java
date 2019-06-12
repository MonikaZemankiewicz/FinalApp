package com.example.TBTManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class CreateWorkoutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mEditTextFilter;
    private ArrayList<String> chosenExercises = new ArrayList<>();
    int i=0;
    private ArrayList<String> theList = new ArrayList<>();
    private Cursor data;
    private ListView listView;
    private ArrayAdapter listAdapter;
    private String[] groupname={"All", "Legs and Butt", "Stomach", "Arms", "Back", "Chest", "Butt", "Full Body Workout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);


        DBHelper dbHelper = new DBHelper(this);
        data = dbHelper.getListContents();
        listView = (ListView)findViewById(R.id.listview);
        listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
        listView.setAdapter(listAdapter);

        mEditTextFilter = findViewById(R.id.editText_filter);

        mEditTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (CreateWorkoutActivity.this).listAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if(data.getCount()==0){
            Toast.makeText(this, "The exercise list is empty", Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1) + " | " + data.getString(2));

            }
            listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
            listView.setAdapter(listAdapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CreateWorkoutActivity.this, theList.get(position), Toast.LENGTH_SHORT).show();
                chosenExercises.add(theList.get(position).split("[|]")[0]);
                i++;
                if(i==8){
                    i=0;
                    finishAdding();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {

                Toast.makeText(CreateWorkoutActivity.this,"Long clicked", Toast.LENGTH_LONG).show();
                return false;
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