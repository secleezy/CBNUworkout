package com.example.cbnugra;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class joinbuttonpage extends AppCompatActivity {
    TextView textView;

    //회원가입 데이터베이스 연결코드
    EditText joinid; //1104천지연
    EditText joinpw;
    Button go_join;

    String ID;
    String PW;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


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



        //스피너(성별선택 코드)
        Spinner spinner=findViewById(R.id.change_sex);
        TextView textView=findViewById(R.id.show_sex);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items
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

        //버튼 클릭시
        go_join.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ID = joinid.getText().toString();
                PW = joinpw.getText().toString();

                if(ID.isEmpty() || PW.isEmpty())
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "짧은 토스트 메시지입니다.",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                    adduser(ID, PW);

            }
        });
    }

    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void adduser(String ID, String PW) {

        //여기에서 직접 변수를 만들어서 값을 직접 넣는것도 가능합니다.
        // ex) 갓 태어난 동물만 입력해서 int age=1; 등을 넣는 경우

        //animal.java에서 선언했던 함수.
        userdb userdb = new userdb(ID,PW);

        //child는 해당 키 위치로 이동하는 함수입니다.
        //키가 없는데 "zoo"와 name같이 값을 지정한 경우 자동으로 생성합니다.
        databaseReference.child("user").child(ID).setValue(userdb);


    }

}