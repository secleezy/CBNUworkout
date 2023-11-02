package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
Button workoutbt;
    EditText workoutname,kcal;
    RadioGroup workoutPartRadioGroup;
    ListView listViewwork;
    ArrayAdapter<String> adapter;

    String time_log;

    private DatabaseReference workRef;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
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

        Intent intent = getIntent();
        String UserID = intent.getStringExtra("user"); //id
        String yearMonDay = intent.getStringExtra("ymd");
        String[] dateParts = yearMonDay.split("-");
        String year = dateParts[0].replaceFirst("^0+(?!$)", "");; // 연도 (yyyy)
        String month = dateParts[1].replaceFirst("^0+(?!$)", "");; // 월 (mm)
        String day = dateParts[2].replaceFirst("^0+(?!$)", "");; // 일 (dd)


        listViewwork = findViewById(R.id.listViewwork);
        workoutPartRadioGroup = findViewById(R.id.workoutPartRadioGroup);
        workoutname=findViewById(R.id.workoutname);
        kcal=findViewById(R.id.kcal);
        workoutbt= findViewById(R.id.workoutbt);
        RadioGroup radioGroup = findViewById(R.id.workoutPartRadioGroup);
        RadioButton radioButton = findViewById(R.id.RadioButton);

        List<String> chestList = new ArrayList<>();
        List<String> backList = new ArrayList<>();
        List<String> armList = new ArrayList<>();
        List<String> shoulderList = new ArrayList<>();
        List<String> legList = new ArrayList<>();
        List<String> workList = new ArrayList<>();
        workRef = FirebaseDatabase.getInstance().getReference("Workout_List");

        workRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


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
                        radioButton.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        workoutPartRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.RadioButton:{
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(addwork.this, android.R.layout.simple_list_item_1, workList);
                        listViewwork.setAdapter(adapter);

                        break;
                    }

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

        listViewwork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                if (checkedRadioButtonId == R.id.RadioButton) {
                    String selectedWorkout = workList.get(position);
                    workoutname.setText(selectedWorkout);
                } else if (checkedRadioButtonId == R.id.chestRadioButton) {
                    String selectedWorkout = chestList.get(position);
                    workoutname.setText(selectedWorkout);
                } else if (checkedRadioButtonId == R.id.backRadioButton) {
                    String selectedWorkout = backList.get(position);
                    workoutname.setText(selectedWorkout);
                }else if (checkedRadioButtonId == R.id.armRadioButton) {
                    String selectedWorkout = armList.get(position);
                    workoutname.setText(selectedWorkout);
                } else if (checkedRadioButtonId == R.id.shoulderRadioButton) {
                    String selectedWorkout = shoulderList.get(position);
                    workoutname.setText(selectedWorkout);
                }else if (checkedRadioButtonId == R.id.legRadioButton) {
                    String selectedWorkout = legList.get(position);
                    workoutname.setText(selectedWorkout);
                }
*/

            }
        });
        workoutbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year_text = year;
                String month_text = month;
                String day_text = day;
                String workoutname_text = workoutname.getText().toString();
                String kcal_text = kcal.getText().toString();

                if (workoutname_text.isEmpty() || kcal_text.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "모든 항목을 입력 해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "성공적으로 저장되었습니다..", Toast.LENGTH_SHORT);
                    toast.show();
                    time_log = getTime();

                    addworkout(UserID,yearMonDay, time_log, year_text, month_text, day_text, workoutname_text, kcal_text);
                }
            }
        });
    }
    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void addworkout(String ID,String selectday, String time_log, String Year, String Month, String Day, String workoutname, String kcal) {
        userworkout userworkout = new userworkout(ID, selectday, Year, Month, Day, workoutname, kcal);
        databaseReference.child("workout").child(ID).child(time_log).setValue(userworkout);
    }
}


