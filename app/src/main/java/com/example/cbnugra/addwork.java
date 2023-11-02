package com.example.cbnugra;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class addwork extends AppCompatActivity {

    EditText workoutname;
    RadioGroup workoutPartRadioGroup;
    ListView listViewwork;
    Button buttonSearch;
    private DatabaseReference workRef;
    private ArrayList<String> workList = new ArrayList<>();;
    private ArrayAdapter<String> adapter;


    private String getTime() {
        TimeZone tz;
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        tz = TimeZone.getTimeZone("Asia/Seoul");
        dateFormat.setTimeZone(tz);
        String getTime = dateFormat.format(date);

        return getTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwork);

        workoutname = findViewById(R.id.workoutname);
        listViewwork = findViewById(R.id.listViewwork);
        workoutPartRadioGroup = findViewById(R.id.workoutPartRadioGroup);
        buttonSearch = findViewById(R.id.buttonSearch);
        List<String> chestList = new ArrayList<>();
        List<String> backList = new ArrayList<>();
        List<String> armList = new ArrayList<>();
        List<String> shoulderList = new ArrayList<>();
        List<String> legList = new ArrayList<>();

        workRef = FirebaseDatabase.getInstance().getReference("Workout_List");

        workRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> workList = new ArrayList<>();

                for (DataSnapshot noSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot foodSnapshot : noSnapshot.getChildren()) {

                        if (foodSnapshot.getKey().equals("운동명")) {
                            String foodName = foodSnapshot.getValue(String.class);
                            workList.add(foodName);
                        }

                        if (foodSnapshot.getValue().equals("가슴")) {
                            String chest = noSnapshot.child("운동명").getValue(String.class);
                            if (chest != null) {
                                chestList.add(chest);
                            }
                        } else if (foodSnapshot.getValue().equals("등")) {
                            String back = noSnapshot.child("운동명").getValue(String.class);
                            if (back != null) {
                                backList.add(back);
                            }
                        } else if (foodSnapshot.getValue().equals("팔")) {
                            String arm = noSnapshot.child("운동명").getValue(String.class);
                            if (arm != null) {
                                armList.add(arm);
                            }
                        } else if (foodSnapshot.getValue().equals("어깨")) {
                            String shoulder = noSnapshot.child("운동명").getValue(String.class);
                            if (shoulder != null) {
                                shoulderList.add(shoulder);
                            }
                        } else if (foodSnapshot.getValue().equals("하체")) {
                            String leg = noSnapshot.child("운동명").getValue(String.class);
                            if (leg != null) {
                                legList.add(leg);
                            }
                        }

                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(addwork.this, android.R.layout.simple_list_item_1, workList);
                listViewwork.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        workoutPartRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {

                    case R.id.chestRadioButton: {

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(addwork.this, android.R.layout.simple_list_item_1, chestList);
                        listViewwork.setAdapter(adapter);
                        break;
                    }
                    case R.id.backRadioButton: {

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(addwork.this, android.R.layout.simple_list_item_1, backList);
                        listViewwork.setAdapter(adapter);
                        break;
                    }
                    case R.id.armRadioButton: {

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(addwork.this, android.R.layout.simple_list_item_1, armList);
                        listViewwork.setAdapter(adapter);
                        break;
                    }
                    case R.id.shoulderRadioButton: {

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(addwork.this, android.R.layout.simple_list_item_1, shoulderList);
                        listViewwork.setAdapter(adapter);
                        break;
                    }
                    case R.id.legRadioButton: {

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(addwork.this, android.R.layout.simple_list_item_1, legList);
                        listViewwork.setAdapter(adapter);
                        break;
                    }
                }
            }
        });
    }
}

/*
    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void addworkout(String ID, String time_log, String Year, String Month, String Day, String workoutname, String kcal) {


        userworkout userworkout = new userworkout(ID, time_log, Year, Month, Day, workoutname, kcal);
        databaseReference.child("workout").child(ID).child(time_log).setValue(userworkout);


    }*/
