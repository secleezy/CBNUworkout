package com.example.cbnugra;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class calendarAdapter extends RecyclerView.Adapter<calendarAdapter.CalendarViewHolder> {
    ArrayList<String> dayList;
    public calendarAdapter(ArrayList<String> dayList){
        this.dayList=dayList;
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
        //날짜 적용
        String day=dayList.get(position);

        holder.dayText.setText(day);

        if((position+1)%7==0){
            holder.dayText.setTextColor(Color.BLUE);
        }else if(position==0 || (position%7)==0){
            holder.dayText.setTextColor(Color.RED);
        }
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

