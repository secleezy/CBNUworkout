package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class mypage extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mypage, container, false);

        Button changememberinfobuttton = (Button) view.findViewById(R.id.go_change_memberinfo); //fragment에서 findViewByid는 view.을 이용해서 사용

        changememberinfobuttton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangeMemberinfo.class);
                startActivity(intent);
            }
        });
        return view;
    }
}