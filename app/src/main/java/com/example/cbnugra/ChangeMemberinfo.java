package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeMemberinfo extends AppCompatActivity {

    String[] items={"선택안함","여자","남자" };
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changememberinfo);
        Button gochangepassword = (Button) findViewById(R.id.go_change_password);
        Button finishchangememberinfo = (Button) findViewById(R.id.finish_change_memberinfo);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        //name이 id임. name이 id임.  name이 id임.  name이 id임.


        gochangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                intent.putExtra("user",name);
                startActivity(intent);
            }
        });
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
    finishchangememberinfo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), mainhome.class);
            startActivity(intent);
        }
    });
    }

}
