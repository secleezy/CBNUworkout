package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class mainhome extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    @Override
    public void onBackPressed() { //뒤로가기 금지코드
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);
        Intent intent = getIntent();

        String userid = intent.getExtras().getString("user");
        Toast.makeText(getApplicationContext(),userid+"님 환영합니다.",Toast.LENGTH_SHORT).show();
        //데이터넘겨받기
        Bundle bundle = new Bundle(); // 번들을 통해 값 전달
        bundle.putString("name",userid);

        mainhome_home mainhome_home = new mainhome_home();//프래그먼트2 선언
        mainhome_home.setArguments(bundle);
        mainhome_calendar mainhome_calendar = new mainhome_calendar();//프래그먼트2 선언
        mainhome_calendar.setArguments(bundle);
        mainhome_list mainhome_workout = new mainhome_list();//프래그먼트2 선언
        mainhome_workout.setArguments(bundle);
        mainhome_mypage mainhome_mypage = new mainhome_mypage();//프래그먼트2 선언
        mainhome_mypage.setArguments(bundle);
        //데이터넘기기

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);

        //처음화면  Framlayout에 fragment.xml 띄우기
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, mainhome_home).commit();


        //바텀 네비게이션뷰 안의 아이템 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    //item 클릭 시 id 값을 가져와  Framlayout에 fragment.xml 띄우기
                    case R.id.mainpage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainhome_home).commit();
                        break;
                    case R.id.calender:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainhome_calendar).commit();
                        break;
                    case R.id.member_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainhome_workout).commit();
                        break;
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainhome_mypage).commit();
                        break;
                }


                return true;
            }
        });


    }

    //
}
