package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private String name;
    private EditText Currentpw;
    private EditText changepw, changepw_checked;
    private Button finishchangepassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        Currentpw=(EditText)findViewById(R.id.Currentpw);
        finishchangepassword= (Button)findViewById(R.id.finish_change_password);

        changepw=(EditText)findViewById(R.id.changepw);
        changepw_checked=(EditText)findViewById(R.id.changepw_checked);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        //name이 id임. name이 id임.  name이 id임.  name이 id임.

        finishchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Currentpw.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "현재 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();//토스메세지 출력
                    return;
                }
                if(changepw.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "새 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();//토스메세지 출력
                    return;
                }
                if(changepw_checked.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "새 비밀번호 확인을 입력하세요.", Toast.LENGTH_SHORT).show();//토스메세지 출력
                    return;
                }


                databaseReference.child("user").child(name).child("pw").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String originPW = dataSnapshot.getValue(String.class);
                        originPW= originPW;


                        if (Currentpw.getText().toString().equals(originPW)) {
                            if (changepw.getText().toString().equals(changepw_checked.getText().toString()))
                            {
                                String PW=changepw.getText().toString();
                                changepw(PW);
                                Toast.makeText(getApplicationContext(), "비밀번호가 변경 되었습니다.", Toast.LENGTH_SHORT).show();//토스메세지 출력
                                Intent intent = new Intent(getApplicationContext(), mainhome.class);
                                intent.putExtra("user",name);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "비밀번호 재확인 오류입니다.", Toast.LENGTH_SHORT).show();//토스메세지 출력
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();//토스메세지 출력
                            Currentpw.setText("");
                            changepw.setText("");
                            changepw_checked.setText("");
                            Currentpw.getFocusable();
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
        });
    }

    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void changepw(String PW) {
        changepw changepw = new changepw(PW);
        databaseReference.child("user").child(name).child("pw").setValue(PW);


    }
}
