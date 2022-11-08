package com.example.cbnugra;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class mainhome extends AppCompatActivity{
    afterloginmainpage afterloginmainpage;
    calender calender;
    memberlist memberlist;
    mypage mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);

        afterloginmainpage=new afterloginmainpage();
        calender=new calender();
        memberlist=new memberlist();
        mypage=new mypage();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers,afterloginmainpage).commit();

        NavigationBarView navigationBarView=findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.mainpage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,afterloginmainpage).commit();
                        return true;
                    case R.id.calender:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,calender).commit();
                        return true;
                    case R.id.member_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,memberlist).commit();
                        return true;
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,mypage).commit();
                        return true;
                }
                return false;
            }
        });
    }


}
