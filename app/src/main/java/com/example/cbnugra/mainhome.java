package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class mainhome extends AppCompatActivity{

    //
    @Override
    public void onBackPressed() { //뒤로가기 금지코드
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);

        Button bt1 = (Button) findViewById(R.id.bt1);
        Button bt2 = (Button) findViewById(R.id.bt2);
        Button bt3 = (Button) findViewById(R.id.bt3);
        Button bt4 = (Button) findViewById(R.id.bt4);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mainhome.class);
                startActivity(intent);
                overridePendingTransition(0, 0); //animation제거
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), mainhome_meal.class);
                startActivity(intent2);
                overridePendingTransition(0, 0); //animation제거
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), mainhome_workout.class);
                startActivity(intent3);
                overridePendingTransition(0, 0); //animation제거
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getApplicationContext(), mainhome_mypage.class);
                startActivity(intent4);
                overridePendingTransition(0, 0); //animation제거
            }
        });

    }

    //
}
