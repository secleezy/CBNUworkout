package com.example.cbnugra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class mainhome_home extends Fragment {
    private View view;
    private TextView title;
    private TextView thisistitle;
    private String name;
    private Button workoutbt;


    EditText year;
    EditText month;
    EditText day;
    EditText workoutname;
    EditText kcal;



    String time_log;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_mainhome_home, container, false);
        title = view.findViewById(R.id.title);
        thisistitle = view.findViewById(R.id.thisistitle);
        thisistitle.setText("\uD83C\uDF1E 오늘의 몸무게 : ");
        title.setText("\uD83D\uDCAA 오늘의 운동 기록하기");
        workoutbt = view.findViewById(R.id.workoutbt);

        year = view.findViewById(R.id.year);
        month = view.findViewById(R.id.month);
        day = view.findViewById(R.id.day);
        //workoutname = view.findViewById(R.id.workoutname);
        kcal = view.findViewById(R.id.kcal);

        if (getArguments() != null)
        {
            name = getArguments().getString("name"); // 프래그먼트1에서 받아온 값 넣기

            //name이 id임. name이 id임.  name이 id임.  name이 id임.
        }

        //운동기록 저장 클릭시
        workoutbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String year_text = year.getText().toString();
                String month_text = month.getText().toString();
                String day_text = day.getText().toString();
                String workoutname_text = workoutname.getText().toString();
                String kcal_text = kcal.getText().toString();

                if(year_text.isEmpty()||month_text.isEmpty()||day_text.isEmpty()||workoutname_text.isEmpty()||kcal_text.isEmpty())
                {
                    Toast toast = Toast.makeText(getActivity(), "모든 항목을 입력 해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getActivity(), "성공적으로 저장되었습니다..", Toast.LENGTH_SHORT);
                    toast.show();
                    time_log=getTime();
                    addworkout(name, time_log, year_text, month_text, day_text, workoutname_text, kcal_text);
                }
            }
        });

        //////////////////////////////////////////////////////////////////////////////////
        Spinner foodSpinner = view.findViewById(R.id.foodSpinner);
        DatabaseReference foodRef = databaseReference.child("Food");


        foodRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> foodList = new ArrayList<>();

                for (DataSnapshot noSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot foodSnapshot : noSnapshot.getChildren()) {

                        if (foodSnapshot.getKey().equals("식품명")) {
                            String foodName = foodSnapshot.getValue(String.class);
                            foodList.add(foodName);


                        }

                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, foodList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                foodSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러 처리를 수행합니다.
            }
        });
        //스피너에 추가

        return view;
    }

    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void addworkout(String ID, String time_log, String Year, String Month, String Day, String workoutname, String kcal) {
        String selectedFood = ((Spinner) view.findViewById(R.id.foodSpinner)).getSelectedItem().toString();

        userworkout userworkout = new userworkout(ID, time_log, Year, Month, Day, workoutname, kcal);
        userworkout.setFood(selectedFood);

        databaseReference.child("workout").child(ID).child(time_log).setValue(userworkout);
    }
}