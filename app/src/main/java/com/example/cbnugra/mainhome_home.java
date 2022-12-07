package com.example.cbnugra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class mainhome_home extends Fragment {
    private View view;
    private TextView title;
    private String name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mainhome_home, container, false);
        title = view.findViewById(R.id.title);

        if (getArguments() != null)
        {
            name = getArguments().getString("name"); // 프래그먼트1에서 받아온 값 넣기
            title.setText(name + "님 안녕하세요. 오늘은 어떤 운동을 하셨나요?");
            //name이 id임. name이 id임.  name이 id임.  name이 id임.
        }

        return view;
    }
}