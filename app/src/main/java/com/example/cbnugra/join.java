package com.example.cbnugra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class join extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    public void onBackPressed() { //뒤로가기 금지코드
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);



        EditText inputid =(EditText) findViewById(R.id.inputid);
        EditText inputpw =(EditText) findViewById(R.id.inputpw);

        Button joinbuttton = (Button) findViewById(R.id.joinbt);
        Button loginbtton = (Button) findViewById(R.id.loginbt);

        //로그인 후 homepage로 이동
        loginbtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=inputid.getText().toString();
                String userpw=inputpw.getText().toString();

                if (userid.isEmpty()&&userpw.isEmpty()) // 아디 비번 둘다 채워넣어야함
                {
                    Toast.makeText(getApplicationContext(),"아이디와 비밀번호를 입력 해 주세요.",Toast.LENGTH_SHORT).show();//토스메세지
                }
                else if(userid.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"아이디를 입력 해 주세요.",Toast.LENGTH_SHORT).show();//토스메세지
                }
                else if(userpw.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력 해 주세요.",Toast.LENGTH_SHORT).show();//토스메세지
                }
                else
                {
                    databaseReference.child("user").child(userid).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            String value = datasnapshot.getValue(String.class);


                            {
                                if(value!=null){
                                    //
                                    databaseReference.child("user").child(userid).child("pw").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                            String value2 = snapshot2.getValue(String.class);

                                            if(value2.equals(userpw)){

                                                Intent intent = new Intent(getApplicationContext(), mainhome.class);
                                                intent.putExtra("user",userid);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(),"비밀번호 오류입니다.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // 디비를 가져오던중 에러 발생 시
                                            //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                                        }
                                    });
                                    //
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"아이디를 확인해주세요.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // 디비를 가져오던중 에러 발생 시
                            //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                        }
                    });
                    //
                }

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