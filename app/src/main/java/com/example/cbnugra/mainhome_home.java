package com.example.cbnugra;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.ArrayAdapter;
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

    List<String> years, months;

    String time_log;

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
                // Do nothing for now
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
                    time_log = getTime();
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

        return view;
    }

    private void setUpDateSpinners() {
        years = new ArrayList<>();
        for (int i = 1930; i <= 2023; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(yearAdapter);

        months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(Integer.toString(i));
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(monthAdapter);
    }

    private void setUpDaySpinner() {
        List<String> days = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, days);
        daySpinner.setAdapter(dayAdapter);
    }

    public void addworkout(String ID, String time_log, String Year, String Month, String Day, String workoutname, String kcal) {
        userworkout userworkout = new userworkout(ID, time_log, Year, Month, Day, workoutname, kcal);
        databaseReference.child("workout").child(ID).child(time_log).setValue(userworkout);
    }
}
