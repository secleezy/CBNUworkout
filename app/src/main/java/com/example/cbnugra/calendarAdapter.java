package com.example.cbnugra;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class calendarAdapter extends RecyclerView.Adapter<calendarAdapter.CalendarViewHolder> {
    ArrayList<LocalDate> dayList;
    OnItemListener onItemListener;




    public calendarAdapter(ArrayList<LocalDate> dayList, OnItemListener onItemListener, List<CalendarEventData> dateDataList){

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
        LocalDate day = dayList.get(position);


        if(day == null){

            holder.dayText.setText("");
        }
        else{ holder.dayText.setText(String.valueOf(day.getDayOfMonth()));
        //여기다가 써야 textview로 들어감 ㅎ...
        //holder.viewDiet.setText("dkdkdkdkdk");
        }

        if((position+1)%7==0){
            holder.dayText.setTextColor(Color.BLUE);
        }else if(position==0 || (position%7)==0) {
            holder.dayText.setTextColor(Color.RED);
        }

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //인터페이스를 통해 날짜를 넘겨준다.
              if( day != null){
                onItemListener.onItemClick(day);

                int iYear = day.getYear();
                int iMonth= day.getMonthValue();
                int iDay = day.getDayOfMonth();


                String toastday = iYear + "-" + iMonth + "-" + iDay;
                Toast.makeText(holder.itemView.getContext(), toastday, Toast.LENGTH_LONG).show();}
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
        TextView viewDiet, viewFit;
        public CalendarViewHolder(@NonNull View itemView){
            super(itemView);
            dayText=itemView.findViewById(R.id.dayText);
            viewDiet = (TextView) itemView.findViewById(R.id.WriteData);
        }
    }


}

