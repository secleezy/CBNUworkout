package com.example.cbnugra;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class calendarAdapter extends RecyclerView.Adapter<calendarAdapter.CalendarViewHolder> {
    ArrayList<LocalDate> dayList;
    OnItemListener onItemListener;

    LocalDate day;
    String UserID;
    String date, fitdate;
    String foodEnergy;
    String fitEnergy;

    TextView dayText;
    TextView viewFit, viewDiet;

    private List<ItemView> dateDataList = new ArrayList<>();
    private List<String> foodDataList = new ArrayList<String>();
    private List<String> fitDataList = new ArrayList<String>();

    private DatabaseReference foodRecordReference = FirebaseDatabase.getInstance().getReference("Food_Record");
    private DatabaseReference workRecordReference = FirebaseDatabase.getInstance().getReference("workout");

    public calendarAdapter(ArrayList<LocalDate> dayList, OnItemListener onItemListener, List<ItemView> dateDataList){

        this.dayList=dayList;
        this.onItemListener = onItemListener;
        this.dateDataList = dateDataList;
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

        mainhome_calendar Fragment = new mainhome_calendar();

        //날짜 변수에 담기
        day = dayList.get(position);

        if(day == null){

            holder.dayText.setText("");
        }
        else{ holder.dayText.setText(String.valueOf(day.getDayOfMonth()));
            String daySt = day.toString();
            //여기다가 써야 textview로 들어감 ㅎ...

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
        if( dayList != null){

            return dayList.size();
        }
        else return 0;

    }

    class CalendarViewHolder extends RecyclerView.ViewHolder {

        //초기화
        TextView dayText;
        TextView viewFit, viewDiet;

        public CalendarViewHolder(@NonNull View itemView){
            super(itemView);
            dayText=itemView.findViewById(R.id.dayText);
            viewDiet = itemView.findViewById(R.id.WriteData);
            viewFit = itemView.findViewById(R.id.WriteFit);

        }
    }
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fragment_mainhome_calendar, container, false);




    }}




