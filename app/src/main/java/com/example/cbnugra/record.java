package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class record extends AppCompatActivity {

    ListView listView1;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;
    TextView YYMMDD;
    EditText editText1;
    Button addbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent intent = getIntent();

        String text = intent.getStringExtra("YYMMDD");
        YYMMDD = findViewById(R.id.YYMMDD);
        YYMMDD.setText(text);
        editText1 = findViewById(R.id.exercisename);

        addbutton = findViewById(R.id.addbutton);

        listItem = new ArrayList<String>();
        listItem.add("예1");
        listItem.add("예2");
        listItem.add("예3");
        listItem.add("예4");


        // 아이템 추가한다.
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listItem.add(editText1.getText().toString());
                adapter.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
                editText1.setText("");
            }
        });


        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listItem);
        listView1 = findViewById(R.id.list_view_exerciseroutine);
        listView1.setAdapter(adapter);

        // 각 아이템 클릭시 해당 아이템 삭제한다.
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 콜백매개변수는 순서대로 어댑터뷰, 해당 아이템의 뷰, 클릭한 순번, 항목의 아이디
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(), listItem.get(i).toString() + " 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                listItem.remove(i);
                adapter.notifyDataSetChanged();
            }
        });

        //리스트 뷰 높이 지정
        int totalHieght = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView1);
            listItem.measure(0, 0);
            totalHieght += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView1.getLayoutParams();
        params.height = totalHieght + (listView1.getDividerHeight() * (adapter.getCount() - 1));
        listView1.setLayoutParams(params);
        listView1.setAdapter(adapter);
    }
}
