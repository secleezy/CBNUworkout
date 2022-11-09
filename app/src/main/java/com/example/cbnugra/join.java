package com.example.cbnugra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class join extends AppCompatActivity {

    @Override
    public void onBackPressed() { //뒤로가기 금지코드
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Button joinbuttton = (Button) findViewById(R.id.joinbt);
        Button loginbtton = (Button) findViewById(R.id.loginbt);

        //로그인 후 homepage로 이동, DB로 로그인 확인 연동 아직 안됌. 페이지 넘김만 확인 가능
        loginbtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mainhome.class);
                startActivity(intent);
            }
        });
        joinbuttton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), joinbuttonpage.class);
                startActivity(intent);
            }
        });

    }


}