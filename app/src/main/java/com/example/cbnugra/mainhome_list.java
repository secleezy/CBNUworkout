package com.example.cbnugra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class mainhome_list extends Fragment {

    private View view;
    private String name;
    private static final int REQUEST_CODE_FOOD_SEARCH = 100;
    private TextView selectedTextView;

    private TextView textView1, textView2, textView3, textView4, textView5, textView6;

    private TextView carbsValue;
    private TextView proteinValue;
    private TextView fatValue;
    private TextView sugarValue;
    private TextView energyValue;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");
    private DatabaseReference foodRecordReference = FirebaseDatabase.getInstance().getReference("Food_Record");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mainhome_list, container, false);

        if (getArguments() != null) {
            name = getArguments().getString("name");
        }

        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        textView5 = view.findViewById(R.id.textView5);
        textView6 = view.findViewById(R.id.textView6);

        textView1.setOnClickListener(v -> {
            selectedTextView = textView1;
            startFoodSearchActivity();
        });

        textView2.setOnClickListener(v -> {
            selectedTextView = textView2;
            startFoodSearchActivity();
        });

        textView3.setOnClickListener(v -> {
            selectedTextView = textView3;
            startFoodSearchActivity();
        });

        textView4.setOnClickListener(v -> {
            selectedTextView = textView4;
            startFoodSearchActivity();
        });

        textView5.setOnClickListener(v -> {
            selectedTextView = textView5;
            startFoodSearchActivity();
        });

        textView6.setOnClickListener(v -> {
            selectedTextView = textView6;
            startFoodSearchActivity();
        });

        carbsValue = view.findViewById(R.id.carbsValue);
        proteinValue = view.findViewById(R.id.proteinValue);
        fatValue = view.findViewById(R.id.fatValue);
        sugarValue = view.findViewById(R.id.sugarValue);
        energyValue = view.findViewById(R.id.energyValue);

        return view;
    }

    private void startFoodSearchActivity() {
        Intent intent = new Intent(getActivity(), FoodSearchActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FOOD_SEARCH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FOOD_SEARCH && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String foodName = data.getStringExtra("selectedFood");
                if (selectedTextView != null && foodName != null) {
                    selectedTextView.setText(foodName);
                    fetchNutritionValues(foodName);
                }
            }
        }
    }

    private void fetchNutritionValues(String foodName) {
        databaseReference.orderByChild("식품명").equalTo(foodName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String carbs = dataSnapshot.child("탄수화물(g)").getValue(String.class);
                    String protein = dataSnapshot.child("단백질(g)").getValue(String.class);
                    String fat = dataSnapshot.child("지방(g)").getValue(String.class);
                    String sugar = dataSnapshot.child("총당류(g)").getValue(String.class);
                    String energy = dataSnapshot.child("에너지(㎉)").getValue(String.class);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    String todayDate = sdf.format(Calendar.getInstance(Locale.KOREA).getTime());

                    if (name != null && todayDate != null) {
                        if (carbs != null) {
                            double currentCarbs = Double.parseDouble(carbsValue.getText().toString());
                            carbsValue.setText(String.format("%.2f", currentCarbs + Double.parseDouble(carbs)));
                            foodRecordReference.child(name).child(todayDate).child("Carb").setValue(carbsValue.getText());
                        }
                        if (protein != null) {
                            double currentProtein = Double.parseDouble(proteinValue.getText().toString());
                            proteinValue.setText(String.format("%.2f", currentProtein + Double.parseDouble(protein)));
                            foodRecordReference.child(name).child(todayDate).child("Protein").setValue(proteinValue.getText());
                        }
                        if (fat != null) {
                            double currentFat = Double.parseDouble(fatValue.getText().toString());
                            fatValue.setText(String.format("%.2f", currentFat + Double.parseDouble(fat)));
                            foodRecordReference.child(name).child(todayDate).child("Fat").setValue(fatValue.getText());
                        }
                        if (sugar != null) {
                            double currentSugar = Double.parseDouble(sugarValue.getText().toString());
                            sugarValue.setText(String.format("%.2f", currentSugar + Double.parseDouble(sugar)));
                            foodRecordReference.child(name).child(todayDate).child("Sugar").setValue(sugarValue.getText());
                        }
                        if (energy != null) {
                            double currentEnergy = Double.parseDouble(energyValue.getText().toString());
                            energyValue.setText(String.format("%.2f", currentEnergy + Double.parseDouble(energy)));
                            foodRecordReference.child(name).child(todayDate).child("Energy").setValue(energyValue.getText());
                        }

                        if (selectedTextView == textView1) {
                            foodRecordReference.child(name).child(todayDate).child("아침").setValue(foodName);
                        } else if (selectedTextView == textView2) {
                            foodRecordReference.child(name).child(todayDate).child("오전간식").setValue(foodName);
                        } else if (selectedTextView == textView3) {
                            foodRecordReference.child(name).child(todayDate).child("점심").setValue(foodName);
                        } else if (selectedTextView == textView4) {
                            foodRecordReference.child(name).child(todayDate).child("오후간식").setValue(foodName);
                        } else if (selectedTextView == textView5) {
                            foodRecordReference.child(name).child(todayDate).child("저녁").setValue(foodName);
                        } else if (selectedTextView == textView6) {
                            foodRecordReference.child(name).child(todayDate).child("야식").setValue(foodName);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}
