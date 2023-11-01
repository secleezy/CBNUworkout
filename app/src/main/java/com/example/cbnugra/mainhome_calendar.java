package com.example.cbnugra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class mainhome_calendar extends Fragment implements OnItemListener{

    TextView MonthYearText; //년월 텍스트뷰
    LocalDate selectedDate; //년월 변수
    RecyclerView recyclerView;

    TextView title, viewFit, viewDiet;
    private String name;
    private String UserID;
    GestureDetector detector;
    TextView textView;
    private DatabaseReference foodRecordReference = FirebaseDatabase.getInstance().getReference("Food_Record");
    private DatabaseReference workRecordReference = FirebaseDatabase.getInstance().getReference("workout");

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    //0712


    //fragment에는 setContentView가 없음
    //이 경우 그리고자 하는 View를 onCreateView 메소드에서 return해주면 된다.
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fragment_mainhome_calendar, container, false);

        if (getArguments() != null)
        {
            UserID = getArguments().getString("name"); // 프래그먼트1에서 받아온 값 넣기

        }
        foodRecordReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                viewDiet = (TextView) view.findViewById(R.id.WriteData);
                //List<String> workList = new ArrayList<>();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (userSnapshot.getKey().equals(UserID)) {
                        //이 이후로 필요 추가한 거 코딩하면 될듯 ㅇㅇ
                        //userSnapsho : key = userID 인 key의 식단 기록 나열
                        //다음 입력시 어떤 형식인지 알 수 있음
                        //System.out.println(userSnapshot);
                        for(DataSnapshot dateSnapshot : userSnapshot.getChildren()){
                            if(dateSnapshot.getKey().equals(selectedDate)){
                                /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                                String todayDate = sdf.format(Calendar.getInstance(Locale.KOREA).getTime());*/
                                Double energy = dateSnapshot.child("Energy").getValue(Double.class);
                                String foodEnergy = String.valueOf(energy);
                                viewDiet.setText(foodEnergy);

                            }
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        workRecordReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (userSnapshot.getKey().equals(UserID)) {
                        //System.out.println(userSnapshot);
                        //userSnapsho : key = userID 인 key의 운동 기록 나열
                        // {key = userid, value ={yyyy-mm-dd={time-log =yyyy-mm-dd , kcal= int, month = int, year= month, workoutname =Stinrg, id = userid , day = int}, yyyy-mm-dd...반복}
                        //이 이후로 필요 추가한 거 코딩하면 될듯 ㅇㅇ
                        viewFit = (TextView) view.findViewById(R.id.WriteFit);
                        if(snapshot.exists()){

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //초기화
        MonthYearText=(TextView)view.findViewById(R.id.MonthYearText);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        //현재 날짜
        selectedDate=(LocalDate) LocalDate.now();
        //화면 설정
        setMonthView();
        //이전달 버튼 이벤트



        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });

        title = view.findViewById(R.id.title);

        if (getArguments() != null)
        {
            name = getArguments().getString("name"); // 프래그먼트1에서 받아온 값 넣기
            //name이 id임. name이 id임.  name이 id임.  name이 id임.
        }

        /////////////////////////////////////////////////////////////////////////////

        detector = new GestureDetector(getActivity().getApplicationContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null || e2 == null) {
                    return false;
                }
                float deltaX = e2.getX() - e1.getX();
                float deltaY = e2.getY() - e1.getY();

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // 좌우로 스와이프된 경우
                    if (Math.abs(deltaX) > 100 && Math.abs(velocityX) > 100) {
                        if (deltaX > 0) {
                            // 오른쪽으로 스와이프
                            selectedDate=selectedDate.minusMonths(1);
                            setMonthView();
                        } else {
                            // 왼쪽으로 스와이프
                            selectedDate=selectedDate.plusMonths(1);
                            setMonthView();
                        }
                    }
                }

                return true;
            }
        });

        ViewGroup parentViewGroup = (ViewGroup) view.getParent();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });


        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });




        return view;
    }



    //날짜 타입 설정 (MM월 YY년)
    private String MonthYearFromDate(LocalDate date){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MM월 yyyy");
        return date.format(formatter);
    }
    //날짜 타입 설정 (YY년 MM월)
    private String YearMonthFromDate(LocalDate date){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyy년 MM월");
        return date.format(formatter);
    }
    //화면 설정
    private void setMonthView() {
        //년월 텍스트뷰 셋팅
        MonthYearText.setText(MonthYearFromDate(selectedDate));

        ArrayList<LocalDate> dayList =daysInMonthArray(selectedDate);
        calendarAdapter adater = new calendarAdapter(dayList, mainhome_calendar.this);
        //레이아웃 설정(열 7개)
        //fragment 에서는 getContext가 안될경우 getActivity().getContext 사용
        RecyclerView.LayoutManager manager=new GridLayoutManager(getActivity().getApplicationContext(),7);
        //레이아웃 적용
        recyclerView.setLayoutManager(manager);
        //어댑터 적용
        recyclerView.setAdapter(adater);
    }
    private ArrayList<LocalDate> daysInMonthArray(LocalDate date){
        ArrayList<LocalDate> dayList=new ArrayList<>();
        YearMonth yearMonth=YearMonth.from(date);
        //해당 월 마지막 날짜 가져오기
        int lastDay=yearMonth.lengthOfMonth();
        //해당 월 첫 번째 날짜 가져오기
        LocalDate firstDay=selectedDate.withDayOfMonth(1);
        //첫 번째 날 요일 가져오기
        int dayOfWeek=firstDay.getDayOfWeek().getValue();
        for(int i=1;i<42;i++){
            if(i<=dayOfWeek || i>lastDay+dayOfWeek){
                dayList.add(null);
            }else{
                dayList.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek));
            }
        }
        return dayList;
    }

    //날짜 클릭시 이벤트
    @Override
    public void onItemClick(LocalDate dayText) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식 지정
        String dateString = dayText.format(formatter);

        if(dayText != null) {

            if (dateString != "") {
                String yearMonDay;
                if (dateString.length() < 2) {
                    yearMonDay = YearMonthFromDate(selectedDate) + "-0" + dayText;
                } else {
                    yearMonDay = YearMonthFromDate(selectedDate) + "-" + dayText;
                }
                Intent intent = new Intent(getActivity(), record.class);
                intent.putExtra("ymd", yearMonDay);
                intent.putExtra("user", UserID);
                startActivity(intent);


            }
        }


    }}
