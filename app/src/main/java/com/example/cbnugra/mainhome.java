package com.example.cbnugra;

import android.os.Bundle;
import android.view.MenuItem;

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

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);

        //처음화면  Framlayout에 fragment.xml 띄우기
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new mainhome_home()).commit();
        //바텀 네비게이션뷰 안의 아이템 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    //item 클릭 시 id 값을 가져와  Framlayout에 fragment.xml 띄우기
                    case R.id.mainpage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new mainhome_home()).commit();
                        break;
                    case R.id.calender:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new mainhome_calendar()).commit();
                        break;
                    case R.id.member_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new mainhome_workout()).commit();
                        break;
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new mainhome_mypage()).commit();
                        break;
                }


                return true;
            }
        });


    }

    //
}
