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
    TextView textView;

    //회원가입 데이터베이스 연결코드
    EditText joinid; //1104천지연
    Button go_join;

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
    }

    //버튼 누르면 데이터베이스 연동 코드
    @Override
    protected void onStart() {
        super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                textView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        go_join.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                databaseReference.child("message").push().setValue("2");
            }
        });
    }
}