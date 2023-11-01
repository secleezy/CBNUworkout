package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class record extends AppCompatActivity {

    ListView listView1;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;
    TextView YYMMDD,txt1,txt2,txt3,txt4,txt5,txt6;
    EditText exercisename;
    EditText editText1;
    Button addbutton;
    String itemname;

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
        exercisename = findViewById(R.id.exercisename);
        editText1 = findViewById(R.id.editText1);
        addbutton = findViewById(R.id.addbutton);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);

        foodRecordReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (userSnapshot.getKey().equals(UserID)) {
                        for(DataSnapshot daySnapshot : userSnapshot.getChildren()){
                            if(daySnapshot.getKey().equals(yearMonDay)) {
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
        List<String> userworklist = new ArrayList<>();
        workRecordReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (userSnapshot.getKey().equals(UserID)) {
                        for(DataSnapshot daySnapshot : userSnapshot.getChildren()){
                            if(daySnapshot.getKey().equals(yearMonDay)) {

                                    String workoutname;
                                    String kcal;
                                    workoutname = daySnapshot.child("workoutname").getValue(String.class);
                                    kcal = daySnapshot.child("kcal").getValue(String.class);
                                    if (workoutname != null && kcal != null){
                                    userworklist.add(workoutname +"-"+ kcal);}

                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listItem = new ArrayList<String>();
        for(int i = 0; i < userworklist.size(); i++){
            listItem.add(userworklist.get(i));
            adapter.notifyDataSetChanged();
        }


        // 아이템 추가한다. [DB작업필요]!!!!!!
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //
                //addworkout(ID, getTime(), year_text, month_text, day_text, exercisename.getText().toString(), editText1.getText().toString());
                //

                itemname=exercisename.getText().toString() + "  -  " + exercisename.getText().toString();
                listItem.add(itemname);
                adapter.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
                exercisename.setText("");
                editText1.setText("");




            }
        });


        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listItem);
        listView1 = findViewById(R.id.list_view_exerciseroutine);
        listView1.setAdapter(adapter);

        // 각 아이템 클릭시 해당 아이템 삭제한다. [DB작업필요]!!!!!!
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 콜백매개변수는 순서대로 어댑터뷰, 해당 아이템의 뷰, 클릭한 순번, 항목의 아이디
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(), listItem.get(i).toString() + " 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                listItem.remove(i);
                adapter.notifyDataSetChanged();
            }
        });

        //리스트 뷰 높이 지정
        int totalHieght = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView1);
            listItem.measure(0, 0);
            totalHieght += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView1.getLayoutParams();
        params.height = totalHieght + (listView1.getDividerHeight() * (adapter.getCount() - 1));
        listView1.setLayoutParams(params);
        listView1.setAdapter(adapter);
    }

    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void addworkout(String ID, String time_log, String Year, String Month, String Day, String workoutname, String kcal) {


        userworkout userworkout = new userworkout(ID, time_log, Year, Month, Day, workoutname, kcal);
        databaseReference.child("workout").child(ID).child(time_log).setValue(userworkout);


    }
}
