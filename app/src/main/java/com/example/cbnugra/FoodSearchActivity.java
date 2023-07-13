package com.example.cbnugra;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodSearchActivity extends AppCompatActivity {
    private EditText searchEditText;
    private ListView foodListView;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        searchEditText = findViewById(R.id.searchEditText);
        foodListView = findViewById(R.id.foodListView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Food");

        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 리스트뷰에서 선택된 식품을 처리하는 로직 추가
                String selectedFood = (String) parent.getItemAtPosition(position);
                Toast.makeText(FoodSearchActivity.this, "선택된 식품: " + selectedFood, Toast.LENGTH_SHORT).show();
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();

                searchFood(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void searchFood(String searchText) {
        Query query = databaseReference.orderByChild("Food").startAt(searchText).endAt(searchText + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> foodList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String foodName = snapshot.child("식품명").getValue(String.class);
                    if (foodName != null) {
                        foodList.add(foodName);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(FoodSearchActivity.this, android.R.layout.simple_list_item_1, foodList);
                foodListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러 처리를 수행합니다.
            }
        });
    }
}
