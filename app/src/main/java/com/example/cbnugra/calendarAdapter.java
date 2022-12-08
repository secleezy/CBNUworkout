package com.example.cbnugra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class calendarAdapter extends RecyclerView.Adapter<calendarAdapter.CalendarViewHolder> {
    ArrayList<String> dayList;
    OnItemListener onItemListener;
    public calendarAdapter(ArrayList<String> dayList, OnItemListener onItemListener){
        this.dayList=dayList;
        this.onItemListener = onItemListener;
    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_calendarcell,parent,false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        //날짜 변수에 담기
        String day = dayList.get(position);
        holder.dayText.setText(day);

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //인터페이스를 통해 날짜를 넘겨준다.
                onItemListener.onItemClick(day);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder {
        //초기화
        TextView dayText;
        public CalendarViewHolder(@NonNull View itemView){
            super(itemView);
            dayText=itemView.findViewById(R.id.dayText);
        }
    }


}

