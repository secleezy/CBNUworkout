package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class record extends AppCompatActivity {

    ListView listView1;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;
    TextView YYMMDD;
    EditText exercisename;
    EditText editText1;
    Button addbutton;
    String itemname;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private String getTime() { //현재 시간 구하는 함수, getTime()으로 사용
        TimeZone tz;
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        tz = TimeZone.getTimeZone("Asia/Seoul");
        dateFormat.setTimeZone(tz);
        String getTime = dateFormat.format(date);

        return getTime;
    }
    private String getDate() { //현재 시간 구하는 함수, getTime()으로 사용
        TimeZone tz;
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormatonlydate = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        tz = TimeZone.getTimeZone("Asia/Seoul");
        dateFormatonlydate.setTimeZone(tz);
        String getDate = dateFormatonlydate.format(date);

        return getDate;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent intent = getIntent();
        String ID = intent.getStringExtra("user"); //idid
        YYMMDD = findViewById(R.id.YYMMDD);
        YYMMDD.setText(getDate());
        exercisename = findViewById(R.id.exercisename);
        editText1 = findViewById(R.id.editText1);
        addbutton = findViewById(R.id.addbutton);

        listItem = new ArrayList<String>();
        //listItem.add("예1");
        //listItem.add("예2");
        //listItem.add("예3");
        //listItem.add("예4");


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
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        listView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
    }

    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void addworkout(String ID, String time_log, String Year, String Month, String Day, String workoutname, String kcal) {


        userworkout userworkout = new userworkout(ID, time_log, Year, Month, Day, workoutname, kcal);
        databaseReference.child("workout").child(ID).child(time_log).setValue(userworkout);


    }
}
