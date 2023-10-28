package com.example.cbnugra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class mainhome_calendar extends Fragment implements OnItemListener{

    TextView MonthYearText; //년월 텍스트뷰
    LocalDate selectedDate; //년월 변수
    RecyclerView recyclerView;

    private TextView title;
    private String name;

    GestureDetector detector;
    TextView textView;

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

        ArrayList<String> dayList =daysInMonthArray(selectedDate);
        calendarAdapter adater=new calendarAdapter(dayList ,mainhome_calendar.this);
        //레이아웃 설정(열 7개)
        //fragment 에서는 getContext가 안될경우 getActivity().getContext 사용
        RecyclerView.LayoutManager manager=new GridLayoutManager(getActivity().getApplicationContext(),7);
        //레이아웃 적용
        recyclerView.setLayoutManager(manager);
        //어댑터 적용
        recyclerView.setAdapter(adater);
    }
    private ArrayList<String> daysInMonthArray(LocalDate date){
        ArrayList<String> dayList=new ArrayList<>();
        YearMonth yearMonth=YearMonth.from(date);
        //해당 월 마지막 날짜 가져오기
        int lastDay=yearMonth.lengthOfMonth();
        //해당 월 첫 번째 날짜 가져오기
        LocalDate firstDay=selectedDate.withDayOfMonth(1);
        //첫 번째 날 요일 가져오기
        int dayOfWeek=firstDay.getDayOfWeek().getValue();
        for(int i=1;i<42;i++){
            if(i<=dayOfWeek || i>lastDay+dayOfWeek){
                dayList.add("");
            }else{
                dayList.add(String.valueOf(i-dayOfWeek));
            }
        }
        return dayList;
    }

    //날짜 클릭시 이벤트
    @Override
    public void onItemClick(String dayText) {
        String yearMonDay = YearMonthFromDate(selectedDate)+ " " + dayText + "일";
        Intent intent = new Intent(getActivity(), record.class);
        intent.putExtra("user",name);
        startActivity(intent);


    }



}
