package com.example.cbnugra;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class FoodSearchActivity extends AppCompatActivity  {

    private EditText editTextSearch;
    private Button buttonSearch;
    private ListView listViewFoods;
    private ArrayList<String> foodList;
    private ArrayAdapter<String> adapter;

    // Firebase reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        listViewFoods = findViewById(R.id.listViewFoods);

        foodList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList);
        listViewFoods.setAdapter(adapter);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Food");

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFoods(editTextSearch.getText().toString());
            }
        });

        listViewFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFood = foodList.get(position);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedFood", selectedFood);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        //아이템 반환
    }

    private void searchFoods(String query) {
        if (TextUtils.isEmpty(query)) {
            return;
        }

        databaseReference.orderByChild("식품명").startAt(query).endAt(query + "\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String foodName = dataSnapshot.child("식품명").getValue(String.class);
                    if (foodName != null) {
                        foodList.add(foodName);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }


}
