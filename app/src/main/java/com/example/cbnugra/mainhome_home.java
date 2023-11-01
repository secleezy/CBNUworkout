package com.example.cbnugra;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class mainhome_home extends Fragment {
    private View view;
    private TextView title;
    private TextView thisistitle;
    private String name;
    private Button workoutbt;

    Spinner yearSpinner, monthSpinner, daySpinner, foodSpinner;
    EditText kcal;
    RadioGroup workoutPartRadioGroup;
    RadioButton chestRadioButton,backRadioButton,armRadioButton,shoulderRadioButton,legRadioButton;
    List<String> years, months;

    String time_log;
//es
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private String getTime() {
        TimeZone tz;
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        tz = TimeZone.getTimeZone("Asia/Seoul");
        dateFormat.setTimeZone(tz);
        String getTime = dateFormat.format(date);

        return getTime;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mainhome_home, container, false);
        title = view.findViewById(R.id.title);
        thisistitle = view.findViewById(R.id.thisistitle);
        thisistitle.setText("\uD83C\uDF1E 오늘의 몸무게 : ");
        title.setText("\uD83D\uDCAA 오늘의 운동 기록하기");
        workoutbt = view.findViewById(R.id.workoutbt);


        yearSpinner = view.findViewById(R.id.yearSpinner);
        monthSpinner = view.findViewById(R.id.monthSpinner);
        daySpinner = view.findViewById(R.id.daySpinner);
        foodSpinner = view.findViewById(R.id.foodSpinner);
        kcal = view.findViewById(R.id.kcal);

        workoutPartRadioGroup = view.findViewById(R.id.workoutPartRadioGroup);
        chestRadioButton = view.findViewById(R.id.chestRadioButton);
        backRadioButton = view.findViewById(R.id.backRadioButton);
        armRadioButton=view.findViewById(R.id.armRadioButton);
        shoulderRadioButton=view.findViewById(R.id.shoulderRadioButton);
        legRadioButton=view.findViewById(R.id.legRadioButton);

        List<String> chestList = new ArrayList<>();
        List<String> backList = new ArrayList<>();
        List<String> armList = new ArrayList<>();
        List<String> shoulderList = new ArrayList<>();
        List<String> legList = new ArrayList<>();
        if (getArguments() != null) {
            name = getArguments().getString("name");
        }

        setUpDateSpinners();
        setUpDaySpinner();

        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        int currentDay = c.get(Calendar.DAY_OF_MONTH) - 1; // list index starts from 0

        yearSpinner.setSelection(years.indexOf(String.valueOf(currentYear)));
        monthSpinner.setSelection(currentMonth);
        daySpinner.setSelection(currentDay);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // You can adjust the daySpinner based on selected month if you want
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        workoutbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year_text = yearSpinner.getSelectedItem().toString();
                String month_text = monthSpinner.getSelectedItem().toString();
                String day_text = daySpinner.getSelectedItem().toString();
                String workoutname_text = foodSpinner.getSelectedItem().toString();
                String kcal_text = kcal.getText().toString();

                if (year_text.isEmpty() || month_text.isEmpty() || day_text.isEmpty() || workoutname_text.isEmpty() || kcal_text.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(), "모든 항목을 입력 해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getActivity(), "성공적으로 저장되었습니다..", Toast.LENGTH_SHORT);
                    toast.show();
                    if(month_text.length()<2){
                        time_log = year_text+"-0"+month_text+"-";
                    }
                    else {
                        time_log = year_text+"-"+month_text+"-";
                    }
                    if(day_text.length()<2){
                        time_log = time_log+"0"+day_text;
                    }
                    else {
                        time_log = time_log+day_text;
                    }

                    addworkout(name, time_log, year_text, month_text, day_text, workoutname_text, kcal_text);
                }
            }
        });

        DatabaseReference foodRef = databaseReference.child("Workout_List");

        foodRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> foodList = new ArrayList<>();

                for (DataSnapshot noSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot foodSnapshot : noSnapshot.getChildren()) {

                        if (foodSnapshot.getKey().equals("운동명")) {
                            String foodName = foodSnapshot.getValue(String.class);
                            foodList.add(foodName);
                        }

                            if (foodSnapshot.getValue().equals("가슴")) {
                                String chest = noSnapshot.child("운동명").getValue(String.class);
                                if (chest != null){chestList.add(chest);}
                            } else if (foodSnapshot.getValue().equals("등")) {
                                String back = noSnapshot.child("운동명").getValue(String.class);
                                if (back != null){backList.add(back);}
                            } else if (foodSnapshot.getValue().equals("팔")) {
                                String arm = noSnapshot.child("운동명").getValue(String.class);
                                if (arm != null){armList.add(arm);}
                            } else if (foodSnapshot.getValue().equals("어깨")) {
                                String shoulder = noSnapshot.child("운동명").getValue(String.class);
                                if (shoulder != null){shoulderList.add(shoulder);}
                            } else if (foodSnapshot.getValue().equals("하체")) {
                                String leg = noSnapshot.child("운동명").getValue(String.class);
                                if (leg != null) {
                                    legList.add(leg);
                                }
                            }

                        }
                    }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, foodList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                foodSpinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        workoutPartRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){

                    case R.id.chestRadioButton:{
                        System.out.println(chestList);
                        ArrayAdapter<String> chestadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, chestList);
                        chestadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        foodSpinner.setAdapter(chestadapter);
                        break;}
                    case R.id.backRadioButton:{
                        System.out.println(backList);
                        ArrayAdapter<String> backadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, backList);
                        backadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        foodSpinner.setAdapter(backadapter);
                        break;}
                    case R.id.armRadioButton:{
                        System.out.println(armList);
                        ArrayAdapter<String> armadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, armList);
                        armadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        foodSpinner.setAdapter(armadapter);
                        break;}
                    case R.id.shoulderRadioButton:{
                        System.out.println(shoulderList);
                        ArrayAdapter<String> shoulderadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, shoulderList);
                        shoulderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        foodSpinner.setAdapter(shoulderadapter);
                        break;}
                    case R.id.legRadioButton:{
                        System.out.println(legList);
                        ArrayAdapter<String> legadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, legList);
                        legadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        foodSpinner.setAdapter(legadapter);
                        break;}
                }
            }
        });
        return view;
    }


    private void setUpDateSpinners() {
        years = new ArrayList<>();
        for (int i = 1930; i <= 2023; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(Integer.toString(i));
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
    }

    private void setUpDaySpinner() {
        List<String> days = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
    }

    public void addworkout(String ID, String time_log, String Year, String Month, String Day, String workoutname, String kcal) {
        userworkout userworkout = new userworkout(ID, time_log, Year, Month, Day, workoutname, kcal);
        databaseReference.child("workout").child(ID).child(time_log).setValue(userworkout);
    }
}
