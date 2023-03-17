package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class mainhome_mypage extends Fragment {

    private View view;
    private TextView title;
    private String name;
    private Button bt_changepassword;
    private Button bt_changememberinfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mainhome_mypage, container, false);
        title = view.findViewById(R.id.title);

        bt_changememberinfo = view.findViewById(R.id.bt_changememberinfo);
        bt_changepassword = view.findViewById(R.id.bt_changepassword);

        if (getArguments() != null)
        {
            name = getArguments().getString("name"); // 프래그먼트1에서 받아온 값 넣기
            title.setText(name);
            //name이 id임. name이 id임.  name이 id임.  name이 id임.
        }

        //운동기록 저장 클릭시
        bt_changememberinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeMemberinfo.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        bt_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        return view;
    }
}