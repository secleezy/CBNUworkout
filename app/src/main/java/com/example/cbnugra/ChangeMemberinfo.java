package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeMemberinfo extends AppCompatActivity {

    String[] items = {"선택안함", "여자", "남자"};
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changememberinfo);

        Button gochangepassword = findViewById(R.id.go_change_password);
        Button finishchangememberinfo = findViewById(R.id.finish_change_memberinfo);
        final TextView textView = findViewById(R.id.show_sex);

        Intent intent = getIntent();
        final String name = intent.getExtras().getString("name");

        DatabaseReference userRef = databaseReference.child("user").child(name);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EditText joinpwEditText = findViewById(R.id.joinpw);
                EditText nameEditText = findViewById(R.id.name);
                EditText birthEditText = findViewById(R.id.birth);
                EditText callNumEditText = findViewById(R.id.call_num);

                String pw = dataSnapshot.child("pw").getValue(String.class);
                String userName = dataSnapshot.child("userName").getValue(String.class);
                String birthday = dataSnapshot.child("birthday").getValue(String.class);
                String phonenum = dataSnapshot.child("phonenum").getValue(String.class);
                String sex = dataSnapshot.child("sex").getValue(String.class);

                joinpwEditText.setText(pw);
                nameEditText.setText(userName);
                birthEditText.setText(birthday);
                callNumEditText.setText(phonenum);

                // 여기에서 sex 값을 기반으로 textView에 값을 설정합니다.
                if ("여자".equals(sex)) {
                    textView.setText("여자");
                } else if ("남자".equals(sex)) {
                    textView.setText("남자");
                } else {
                    textView.setText("");  // sex 필드의 값이 여자나 남자가 아닌 다른 값일 경우
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error handling if needed
            }
        });

        gochangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                intent.putExtra("user", name);
                startActivity(intent);
            }
        });

        Spinner spinner = findViewById(R.id.change_sex);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ChangeMemberinfo.this, android.R.layout.simple_spinner_item, items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positions, long l) {
                textView.setText(items[positions]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("");
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
