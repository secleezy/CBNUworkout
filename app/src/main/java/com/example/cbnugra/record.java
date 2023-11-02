package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class record extends AppCompatActivity {

    ListView listView1;
    TextView YYMMDD;

    Button addbutton, txt1, txt2, txt3, txt4, txt5, txt6;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private DatabaseReference foodRecordReference = FirebaseDatabase.getInstance().getReference("Food_Record");
    private DatabaseReference workRecordReference = FirebaseDatabase.getInstance().getReference("workout");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent intent = getIntent();
        String UserID = intent.getStringExtra("user"); //id
        String yearMonDay = intent.getStringExtra("ymd");
        YYMMDD = findViewById(R.id.YYMMDD);
        YYMMDD.setText(yearMonDay);
        addbutton = findViewById(R.id.addbutton);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        listView1 = findViewById(R.id.list_view_exerciseroutine);
        foodRecordReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (userSnapshot.getKey().equals(UserID)) {
                        for (DataSnapshot daySnapshot : userSnapshot.getChildren()) {

                            if (daySnapshot.getKey().equals(yearMonDay)) {
                                String str1, str2, str3, str4, str5, str6;
                                for (DataSnapshot foodSnapshot : daySnapshot.getChildren()) {
                                    if (foodSnapshot.getKey().equals("아침")) {
                                        str1 = foodSnapshot.getValue(String.class);
                                        txt1.setText(str1);
                                    }

                                    if (foodSnapshot.getKey().equals("오전간식")) {
                                        str2 = foodSnapshot.getValue(String.class);
                                        txt2.setText(str2);
                                    }
                                    if (foodSnapshot.getKey().equals("아침")) {
                                        str3 = foodSnapshot.getValue(String.class);
                                        txt3.setText(str3);
                                    }
                                    if (foodSnapshot.getKey().equals("오후간식")) {
                                        str4 = foodSnapshot.getValue(String.class);
                                        txt4.setText(str4);
                                    }
                                    if (foodSnapshot.getKey().equals("저녁")) {
                                        str5 = foodSnapshot.getValue(String.class);
                                        txt5.setText(str5);
                                    }
                                    if (foodSnapshot.getKey().equals("야식")) {
                                        str6 = foodSnapshot.getValue(String.class);
                                        txt6.setText(str6);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayList<ListData> listViewData = new ArrayList<>();
        workRecordReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (userSnapshot.getKey().equals(UserID)) {
                        for (DataSnapshot timeSnapshot : userSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot : timeSnapshot.getChildren()) {

                                if (dataSnapshot.getValue().equals(yearMonDay)) {
                                    ListData listData = new ListData();

                                    listData.title = timeSnapshot.child("workoutname").getValue(String.class);
                                    listData.body_1 = timeSnapshot.child("kcal").getValue(String.class);
                                    listData.body_2 = "kcal";

                                    listViewData.add(listData);
                                    //"work",timeSnapshot.child("workoutname").getValue(String.class)
                                    //"kcal",timeSnapshot.child("kcal").getValue(String.class)
                                }
                            }

                        }
                    }
                }
                ListAdapter oAdapter = new CustomListView(listViewData);
                listView1.setAdapter(oAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
txt1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {/*
        String foodname = (String) txt1.getText();
        Intent intent1 = new Intent(getApplicationContext(), food_info.class);
        if(foodname != "아침식사"){
        intent1.putExtra("ymd", yearMonDay);
        intent1.putExtra("food", foodname);
            startActivity(intent1);
    }*/
    }
});
addbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent1 = new Intent(getApplicationContext(), addwork.class);
            intent1.putExtra("ymd", yearMonDay);
            intent1.putExtra("user", UserID);
            startActivity(intent1);
    }
});
        }

    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void addworkout(String ID, String time_log, String Year, String Month, String Day, String workoutname, String kcal) {


        userworkout userworkout = new userworkout(ID, time_log, Year, Month, Day, workoutname, kcal);
        databaseReference.child("workout").child(ID).child(time_log).setValue(userworkout);


    }

}
