package com.example.finalapp;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReadyTabatasActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> chosenExercises = new ArrayList<>();
    private List<HashMap<String, String>> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ready_tabatas);

            listView = findViewById(R.id.listview_tabatas);
            HashMap<String, String> nameExeercises = new HashMap<>();
            nameExeercises.put("Strong Legs", "Squats, lunges, side squats, box jumps");
            nameExeercises.put("6-pack", "Plank climbing, sit ups, vertical scissors, side plank");
            nameExeercises.put("Tone arms", "Knee push ups, deeps, burpees, box jumps");
            nameExeercises.put("Whole body workout", "Burpees, lunges, plank climbing, side squats");


            SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
            new String[]{"First Line", "Second Line"}, new int[]{R.id.textView_list_item, R.id.textView_list_sub_item});

            Iterator it = nameExeercises.entrySet().iterator();
            while(it.hasNext()){
                HashMap<String, String> listMap = new HashMap<>();
                Map.Entry pair = (Map.Entry)it.next();
                listMap.put("First Line", pair.getKey().toString());
                listMap.put("Second Line", pair.getValue().toString());
                listItems.add(listMap);

            }

            listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                switch (position) {
                    case 0: {
                        chosenExercises.clear();
                        chosenExercises.add("SQUATS");
                        chosenExercises.add("LUNGES");
                        chosenExercises.add("SIDE SQUATS");
                        chosenExercises.add("BOX JUMPS");
                        passTabata();
                    }
                    case 1: {
                        chosenExercises.clear();
                        chosenExercises.add("PLANK CLIMBING");
                        chosenExercises.add("SIT UPS");
                        chosenExercises.add("VERTICAL SCISSORS");
                        chosenExercises.add("SIDE PLANK");
                        passTabata();
                    }
                    case 2: {
                        chosenExercises.clear();
                        chosenExercises.add("KNEE PUSH UPS");
                        chosenExercises.add("DEEPS");
                        chosenExercises.add("BURPEES");
                        chosenExercises.add("BOX JUMPS");
                        passTabata();
                    }
                    case 3: {
                        chosenExercises.clear();
                        chosenExercises.add("BURPEES");
                        chosenExercises.add("LUNGES");
                        chosenExercises.add("PLANK CLIMBING");
                        chosenExercises.add("SIDE SQUATS");
                        passTabata();
                    }

                    default: {
                    }
                }


            }
        });
    }


    private void passTabata(){
        Bundle args = new Bundle();
        ArrayList toSend = new ArrayList<>(chosenExercises);
        chosenExercises.clear();
        args.putSerializable("ARRAYLIST",(Serializable)toSend);
        final Intent intent1 = new Intent(this, StartActivity.class);
        intent1.putExtra("BUNDLE",args);
        startActivity(intent1);
    }
}
