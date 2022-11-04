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

import org.w3c.dom.Text;

public class joinbuttonpage extends AppCompatActivity {
    TextView textView; //스피너(textView)
    Button id_checked; //중복확인버튼(id_checked)
    EditText joinid, joinpw, joinname, birth, phon_num;

    //스피너 배열 값
    String[] items={"선택안함","여자","남자" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinbuttonpage);

        //입력 값 아이디(아이디, 비밀번호, 전화번호, 생일, 이름)
        joinid=findViewById(R.id.joinid);
        joinpw=findViewById(R.id.joinpw);
        joinname=findViewById(R.id.name);
        birth=findViewById(R.id.birth);
        phon_num=findViewById(R.id.call_num);

        //아이디 중복확인
        id_checked=findViewById(R.id.joinid_checked);



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
    }
}