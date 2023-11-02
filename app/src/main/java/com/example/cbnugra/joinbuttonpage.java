package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONException;
import org.json.JSONObject;


public class joinbuttonpage extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    TextView textView;

    //회원가입 데이터베이스 연결코드
    EditText joinid, joinpw, joinpw_checked, name, call_num, birth;
    TextView sex;
    RadioGroup select_trainer;
    Button go_join, id_checked;
    boolean validate=false;
    AlertDialog dialog;

    String ID, PW;
    String PWcheck;
    String UserName;
    String Sex;
    String Birthday;
    String Phonenum;
    Boolean trainer;
    Boolean trainer_get=true; //라디오 골랐어?
    
    Boolean id_check_success=false;

    //스피너 배열 값
    String[] items={"선택안함","여자","남자" };

    public joinbuttonpage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinbuttonpage);

        //회원가입 데이터베이스 연결코드
        joinid = (EditText) findViewById(R.id.joinid);
        joinpw = (EditText) findViewById(R.id.joinpw);
        go_join = (Button) findViewById(R.id.go_join);
        joinpw_checked=(EditText)findViewById(R.id.joinpw_checked);
        name=(EditText)findViewById(R.id.name);
        sex=(TextView)findViewById(R.id.show_sex);
        call_num=(EditText)findViewById(R.id.call_num);
        birth=(EditText)findViewById(R.id.birth);
        select_trainer=(RadioGroup)findViewById(R.id.select_trainer);



        //아이디 중복 체크(firebase 연동 코드 필요)
        id_checked=(Button)findViewById(R.id.id_checked);
        id_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid=joinid.getText().toString();
                if(validate){
                    return;
                }
                if(userid.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(joinbuttonpage.this);
                    dialog=builder.setMessage("아이디를 입력하세요").setPositiveButton("확인",null).create();
                    dialog.show();
                    return;
                }

                //
                databaseReference.child("user").child(userid).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);

                        if(value!=null){
                            Toast.makeText(getApplicationContext(),"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                            joinid.setEnabled(false);
                            id_check_success=true;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 디비를 가져오던중 에러 발생 시
                        //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }
                });
                //
               //firebase 연동해서 아이디 있는지 확인 코드 필요 - 완료
            }
        });

        //스피너(성별선택 코드)
        Spinner spinner=findViewById(R.id.change_sex);
        //TextView textView=findViewById(R.id.show_sex);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positions, long l) {
                sex.setText(items[positions]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sex.setText("");
            }
        });

        //라디오버튼(트레이너)인식 코드

        select_trainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i)
                {
                    case R.id.code_trainer:
                        trainer_get=true;
                        trainer=true;
                        break;
                    case R.id.code_none:
                        trainer_get=true;
                        trainer=false;
                        break;
                }
            }
        });


        //버튼 클릭시
        go_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = joinid.getText().toString();
                PW = joinpw.getText().toString();
                PWcheck = joinpw_checked.getText().toString();
                UserName = name.getText().toString();

                Sex = spinner.getSelectedItem().toString();

                Birthday=birth.getText().toString();
                Phonenum=call_num.getText().toString();

                if(ID.isEmpty())
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "아이디를 입력 해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(PW.isEmpty())
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "비밀번호를 입력 해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(PWcheck.isEmpty() || !PW.equals(PWcheck))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "비밀번호를 재확인 해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(UserName.isEmpty())
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "이름을 입력 해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(Sex.equals("선택안함"))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "성별을 골라 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(Birthday.isEmpty())
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "생일을 입력 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(Phonenum.isEmpty())
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "전화번호를 입력 해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(!trainer_get)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "회원유형을 골라 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT);
                    toast.show();

                    adduser(ID, PW, UserName, Sex, Birthday, Phonenum, trainer);

                    Intent intent = new Intent(getApplicationContext(), join.class);
                    startActivity(intent);



                }


            }
        });


    }

    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void adduser(String ID, String PW, String UserName, String Sex, String Birthday, String Phonenum, Boolean trainer) {

        userdb userdb = new userdb(ID,PW,UserName,Sex,Birthday,Phonenum,trainer);
        databaseReference.child("user").child(ID).setValue(userdb);


    }


}