package com.example.cbnugra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangeMemberinfo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changememberinfo);
        Button gochangepassword = (Button) findViewById(R.id.go_change_password);
        Button finishchangememberinfo = (Button) findViewById(R.id.finish_change_memberinfo);
        gochangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
    finishchangememberinfo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), mainhome.class);
            startActivity(intent);
        }
    });
    }

}
