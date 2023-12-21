package com.miniproject.metromate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FareCalculator extends AppCompatActivity {
    AutoCompleteTextView actvSource;
    AutoCompleteTextView actvDestination;
    Button Click, r;
    Button back;
    TextView v1;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fare_calculator);

        back = findViewById(R.id.back_btn10);
        back.setOnClickListener(v -> finish());

        v1 = findViewById(R.id.fareText);

        actvSource = findViewById(R.id.actv1_calc);
        actvDestination = findViewById(R.id.actv2_calc);
        Click = findViewById(R.id.btn_calc);
        r = findViewById(R.id.reset_calc);
        String[] stations_s = getResources().getStringArray(R.array.station_array_source);
        String[] stations_d = getResources().getStringArray(R.array.station_array_source);
        actvSource.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, stations_s));
        actvDestination.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, stations_d));
        Click.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            // Hide the soft keyboard
            imm.hideSoftInputFromWindow(Click.getWindowToken(), 0);
            int f = fare(actvSource.getText().toString().trim(),actvDestination.getText().toString().trim());
            if(f!=0)
                v1.setText(new StringBuilder().append("Fare : ").append(f).toString());
        });
        r.setOnClickListener(v -> {
            actvSource.setText("");
            actvDestination.setText("");
            v1.setText("");
            Toast.makeText(FareCalculator.this, "Reset Complete", Toast.LENGTH_SHORT).show();
        });
    }

    public int fare(String start,String stop) {
        int[][] fare_chart =
                new int[][]
                        {{10 ,10, 15, 15, 25, 25, 30, 35, 40, 40, 40, 40, 45, 45, 45, 50, 50, 50, 50, 55, 55, 55, 60, 60, 60, 55, 55, 50, 50, 50, 50, 50, 45, 45, 45, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 55, 55, 55, 60, 40, 40, 40, 40, 45, 45, 45, 50, 50},
                                {10, 10, 10, 15, 15, 25, 25, 30, 35, 40, 40 ,40 ,45 ,45 ,45 ,45 ,50 ,50 ,50 ,50 ,55 ,55 ,55 ,60 ,55 ,55 ,55 ,50 ,50 ,50 ,50 ,45 ,45 ,45 ,45 ,45 ,50 ,50 ,50 ,50 ,50 ,50 ,45 ,50 ,50 ,50, 55, 55, 55, 60,35 ,40 ,40 ,40 ,45 ,45 ,45 ,45 ,45},
                                {15, 10, 10, 10, 15, 15, 25, 30, 35, 35, 40, 40, 40, 45, 45, 45 ,45, 50, 50, 50, 55, 55, 55 ,60, 55, 55, 50, 50, 50, 50, 45, 45, 45 ,45, 45, 45, 45, 50, 50, 50, 50, 45, 45, 45, 50, 50, 50, 55, 55, 55, 35, 35, 40, 40, 40, 40, 45, 45, 45},
                                {15, 15, 10, 10, 10, 15, 15, 25, 30, 35, 35, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 50, 55, 55, 55, 50, 50 ,50, 50, 45, 45, 45, 45, 40, 45, 45, 45, 45, 50, 45 ,45 ,45, 45, 45, 45, 50, 50, 50, 55, 55, 30, 35, 35, 40, 40, 40, 40, 45, 45},
                                {25, 15, 15, 10, 10, 10, 15, 25, 30, 30, 35, 35, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 55, 55, 55, 50, 50, 45, 45, 45, 45, 45, 40, 40, 40, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 50, 50, 50, 55, 30, 30, 35, 35, 40, 40, 40, 40, 45},
                                {25, 25 ,15 ,15, 10, 10, 10, 15, 25, 25, 30, 35, 35, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 55 ,50, 50, 45, 45, 45, 45, 40, 40, 40, 40, 40, 40, 45, 45, 45 ,45 ,45 ,40, 40, 45, 45, 45, 45, 50, 50, 50, 25, 30, 30, 35, 35, 40, 40, 40, 40},
                                {30 ,25 ,25, 15, 15, 10, 10, 10, 15, 25, 25, 30, 35, 35, 40, 40, 40, 45, 45, 45, 45 ,50, 50, 50, 50, 45, 45, 45, 45, 40, 40, 40, 40, 35, 40, 40, 40, 45, 45, 45, 40, 40, 40, 40, 40, 45, 45, 45, 50, 50, 15, 25, 25, 30, 35, 35, 35, 40, 40},
                                {35, 30, 30, 25, 25, 15, 10, 10, 10, 15, 15, 25, 30, 30, 35, 40, 40, 40, 40, 40, 45, 45, 45, 50, 45, 45, 45, 40, 40, 40, 40, 35, 35, 30, 35, 40, 40, 40, 40, 40, 40, 40 ,40, 40, 40, 40, 45, 45, 45, 50, 10, 15, 25, 25, 30, 30, 30, 35, 40},
                                {40, 35, 35, 30, 30, 25, 15, 10, 10, 10, 15, 15, 25, 30, 30, 35, 35, 40, 40, 40, 45, 45, 45, 45, 45, 45, 40, 40, 40, 35, 35, 30, 30, 30, 30, 35, 35, 40, 40, 40, 40 ,35, 35, 35, 40, 40, 40, 45, 45, 45, 10, 10, 15, 15, 25, 25, 30, 30, 35},
                                {40, 40, 35, 35, 30, 25, 25, 15, 10, 10 ,10 ,15 ,15 ,25, 25, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 40, 40, 40, 35, 35, 30, 30, 25 ,25, 30, 30, 35, 35, 40, 40, 40, 40, 35, 40 ,40 ,40, 45, 45, 45, 45, 10, 15, 15, 25, 25, 30, 30, 35, 35},
                                {40, 40, 40, 35, 35, 30, 25, 15, 15, 10, 10, 10, 15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 45, 45, 40, 40, 40, 35, 35, 30, 30, 25, 25, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 40, 40, 40, 45, 45, 45, 50, 15, 15, 25, 25, 30, 30, 35, 35, 40},
                                {40, 40, 40, 40, 35, 35, 30, 25, 15, 15, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 40, 40, 40, 45, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 25, 25, 30, 30, 35, 35, 35, 40, 40, 40, 40, 45, 45, 45 ,50, 50, 15, 25, 25, 30, 35, 35, 35, 40, 40},
                                {45, 45, 40, 40, 40, 35, 35, 30, 25, 15, 15, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 40, 40, 40, 40, 35, 35, 30 ,30, 25, 25, 15, 15, 10, 15, 15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 50, 25, 30, 30, 35, 35, 40, 40, 40, 40},
                                {45, 45, 45, 40, 40, 40, 35, 30, 30, 25, 25, 15, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 35, 40, 35, 35, 30, 25, 25, 15, 15, 10, 10, 10, 10, 15, 15, 25, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 45, 45, 30, 30, 35, 35, 40, 40, 40, 35, 35},
                                {45, 45, 45, 45, 40, 40, 40, 35, 30, 25, 25, 15, 15, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 40, 40, 35, 30, 25, 25, 25, 15, 15, 10, 10, 10, 15, 15, 25, 30, 30, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 30, 35, 35, 40, 40, 40, 40, 35, 35},
                                {50, 45, 45, 45, 45, 40, 40, 40, 35, 30, 30, 25, 15 ,15, 10, 10, 10, 15, 15, 15, 25, 30, 30, 40, 40, 40, 35, 30 ,30 ,25, 25, 15, 15, 15, 15, 25 ,25, 30, 30, 35, 35, 35, 40, 40, 40, 40, 45, 45, 45, 50, 35, 35, 40, 40, 40, 40, 40, 40, 40},
                                {50, 50, 45, 45, 45, 45, 40, 40, 35, 35, 30, 25, 25, 15, 15, 10, 10, 10, 10, 15, 25, 25, 30, 45, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 25, 25, 30, 30, 35, 35, 35, 40, 40, 40, 40, 45, 45, 45, 50, 50, 35, 40, 40, 40, 40, 45, 40, 40, 40},
                                {50, 50, 50, 45, 45, 45, 45, 40, 40, 35, 35, 30, 25, 25, 15, 15, 10, 10, 10, 10, 15, 25, 25, 45, 40, 40, 40, 35, 35, 30, 30, 25, 25, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 40, 45, 45, 45, 50, 50, 50, 40, 40, 40, 45, 45, 45, 45, 40, 40},
                                {50, 50, 50, 50, 45, 45, 45, 40, 40, 40, 35, 30, 30, 25, 25, 15, 10, 10, 10, 10, 15, 15, 25, 45, 45, 40, 40, 40, 35, 35, 30, 30, 25, 25, 25, 30, 35, 35, 40, 40, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 40, 40, 40, 45, 45 ,45 ,45 ,40 ,40},
                                {55, 50, 50, 50 ,50 ,45 ,45 ,40 ,40 ,40 ,40 ,35 ,30 ,30 ,25 ,15 ,15 ,10, 10, 10, 10, 15, 15, 45, 45, 45, 40, 40, 40, 35, 35, 30, 30, 30, 30, 35, 35, 40, 40, 40, 40, 40, 45 ,45, 45, 45, 50, 50, 50, 55, 40, 40, 45, 45, 45, 45, 45, 45, 45},
                                {55, 55, 55, 50, 50, 50, 45, 45, 45, 40, 40, 40, 35, 30, 30, 25, 25, 15, 15, 10, 10, 10, 15, 50, 45, 45, 45, 40, 40, 40, 40, 35, 35, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 50, 55, 55, 45, 45, 45, 45, 50, 50, 50, 45, 45},
                                {55, 55, 55, 50, 50, 50, 50, 45, 45, 40, 40, 40, 40, 35, 30 ,30, 25 ,25, 15, 15, 10, 10, 10, 50, 50, 45, 45, 40, 40, 40, 40, 35, 35, 35, 35, 40, 40, 40, 40, 45, 45 ,45, 45, 45, 50, 50, 50, 55, 55, 55, 45, 45, 45, 50, 50, 50, 50, 45, 45},
                                {60, 55, 55, 55 ,55 ,50, 50, 45, 45 ,45, 45, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 10, 10, 50, 50, 50, 45, 45, 45, 40, 40, 40, 40, 35, 40, 40, 40, 45, 45, 45, 45, 45, 50, 50, 50, 50, 55, 55, 55, 60, 45, 45, 50, 50, 50, 50, 50, 50 ,50},
                                {60, 60, 60, 55, 55, 55, 50, 50, 45, 45, 45 ,45 ,40 ,40 ,40, 40, 45, 45, 45, 45, 50, 50, 50, 10, 10, 15, 25, 30, 30, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 50, 50, 55, 55, 55, 60 ,60 ,45 ,50, 50, 50, 55, 55, 50, 50, 50},
                                {60, 55, 55, 55, 55, 50, 50, 45, 45, 45, 40, 40, 40, 35, 40 ,40, 40, 40, 45, 45, 45, 50, 50, 10, 10, 10, 15, 25, 25, 30, 30, 35, 35, 35, 40, 40, 40, 40, 45, 45, 45, 45, 45, 50, 50, 50, 55, 55, 55, 60, 45, 45, 50, 50, 50, 50, 50, 50, 45},
                                {55, 55, 55, 50, 50, 50, 45, 45, 45, 40, 40, 40, 35, 35, 35, 40, 40, 40, 40, 45, 45, 45, 50, 15, 10, 10 ,10 ,15 ,15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 45, 45, 50, 50, 50, 55, 55, 55, 45, 45, 45, 45, 50, 50, 50, 45, 45},
                                {55, 55, 50, 50, 50, 45, 45, 45, 40, 40, 40, 35, 35, 30, 30, 35, 35, 40, 40, 40, 45, 45, 45, 25, 15, 10, 10, 10, 15, 15, 15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 50, 55, 40, 45, 45, 45, 45, 50, 45, 45, 45},
                                {50, 50, 50 ,50 ,45 ,45, 45, 40, 40, 40, 35, 35, 30, 25, 25, 30, 35, 35, 40, 40, 40, 40, 45, 30, 25, 15, 10, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 40, 45, 45, 45, 50, 50, 50, 50, 40, 40, 45, 45, 45, 45, 45, 45, 40},
                                {50, 50, 50, 50, 45, 45, 45, 40, 40, 35, 35, 30, 30, 25, 25, 30, 30, 35, 35, 40, 40, 40, 45, 30, 25, 15, 15, 10, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 35, 40, 40 ,40 ,40 ,40, 45, 45, 45, 50, 50, 50, 40, 40, 40, 45, 45, 45, 45, 40, 40},
                                {50, 50, 50, 45, 45, 45, 40, 40, 35, 35, 30, 30, 25, 15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 30, 30, 25, 15, 10, 10, 10, 10, 15, 15, 15, 25, 25, 30, 30, 35, 35, 40 ,40, 40, 40, 40, 45, 45, 45, 50, 50, 35, 40, 40, 40, 45, 45, 40, 40, 40},
                                {50, 50, 45, 45, 45, 40, 40, 40, 35, 30, 30, 25, 25, 15, 15, 25, 25, 30, 30, 35, 40, 40, 40, 35, 30, 25, 15, 15, 10, 10, 10, 10, 10, 15, 15, 25, 25, 30, 35, 35, 35, 40, 40, 40, 40, 45, 45, 45, 45, 50, 35, 40, 40, 40, 40, 45, 40, 40, 40},
                                {50 ,45, 45, 45, 45, 40, 40, 35, 30, 30, 25, 25, 15, 10, 15, 15, 25, 25, 30, 30, 35, 35, 40, 35, 35, 30, 25, 15 ,15, 15 ,10, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 35, 35, 40, 40, 40, 45, 45, 45, 50, 30, 35, 40, 40, 40, 40, 40, 40, 35},
                                {45, 45, 45, 45, 40, 40, 40, 35, 30, 25, 25, 15, 15, 10, 10, 15, 15, 25, 25, 30, 35, 35, 40, 40, 35, 30, 25, 25, 15, 15, 10, 10, 10, 10, 10, 15, 15, 25, 30, 30, 30, 35 ,35 ,40 ,40 ,40, 40, 45, 45, 45, 30, 35, 35, 40, 40, 40, 40, 35, 35},
                                {45, 45, 45, 40, 40, 40, 35, 30, 30, 25 ,25 ,15, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 35, 40, 35, 35, 30, 25, 25, 15, 15, 10, 10, 10, 10, 15, 15, 25, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 45, 45, 30, 30, 35, 35, 40, 40, 40, 35, 35},
                                {45, 45, 45, 45, 40, 40, 40, 35, 30, 30, 25, 25, 15, 10, 10, 15, 25, 25, 25, 30, 35, 35, 40, 40, 40, 35, 30, 30, 25, 25, 15, 15, 10, 10, 10, 10, 15, 15, 25, 25, 25, 30, 30, 35, 35, 40, 40, 40, 45, 45, 30, 35, 40, 40, 40, 40, 35, 30, 30},
                                {50, 45, 45, 45, 45, 40, 40, 40, 35, 30, 30, 25, 15, 15, 15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 40, 35, 30, 30, 25, 25, 15, 15, 15, 10, 10, 10, 15, 15, 15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 45, 35, 35, 40, 40, 40, 35, 35, 30, 30},
                                {50, 50, 45, 45, 45, 45, 40, 40, 35, 35, 30, 30, 25, 15, 15, 25 ,30, 30, 35, 35, 40, 40, 40, 45, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 15, 10, 10, 10, 15, 15, 15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 35, 40, 40, 40, 35, 35, 30, 25, 25},
                                {50, 50, 50, 45, 45, 45, 45, 40, 40, 35, 35, 30, 25, 25, 25, 30, 30, 35, 35, 40, 40, 40, 45, 45, 40, 40, 40, 35, 35, 30, 30, 25, 25, 25, 15, 15, 10, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 35, 40, 40, 40, 40, 35, 35, 30, 30, 30, 25, 25},
                                {50 ,50, 50, 50, 45, 45, 45, 40, 40, 40, 35, 35, 30, 25, 30, 30, 35, 35, 40, 40 ,40, 40, 45, 45, 45, 40, 40, 40, 35, 35, 35, 30, 30, 25, 25, 15, 15, 10, 10, 10, 10, 15, 15, 15, 25, 30, 30, 35, 35, 40, 40, 40, 35, 35, 30, 30, 25, 15, 15},
                                {50, 50, 50, 45, 45, 45, 45, 40, 40, 40, 40, 35, 30, 25, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 45, 45, 40, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 10, 10, 10, 10, 10, 15, 15, 25, 25, 30, 30, 35, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15},
                                {50, 50, 50, 45, 45, 45, 40, 40, 40, 40, 40, 35, 35, 30, 30, 35, 35, 40, 40, 40, 45, 45, 45 ,50 ,45, 45, 40, 40, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 10 ,10 ,10 ,10, 10, 15, 15, 25, 30, 30, 35, 35, 40, 35, 30, 30, 25, 25, 15, 15, 10},
                                {50, 50 ,45, 45, 45, 40, 40, 40, 35, 40, 40, 40, 35, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 50, 45, 45, 45, 40, 40, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 10, 10, 10, 10, 10, 15, 15, 25, 30, 30, 35, 35, 30, 30, 25, 25, 15, 15, 10, 10},
                                {50, 45, 45, 45, 45, 40, 40, 40, 35, 35, 40, 40, 40, 35, 35, 40, 40, 40, 40, 45, 45, 45, 50, 50, 45, 45, 45, 40, 40, 40, 40, 35, 35, 35, 30, 30, 25, 25, 15, 15, 10, 10, 10, 10, 15, 15, 25, 25, 30, 35, 35, 30, 25, 25, 15, 15, 15, 10, 10},
                                {50, 50, 45, 45, 45, 45, 40, 40, 35, 40, 40, 40, 40, 35, 40, 40, 40, 40, 45, 45, 45, 45, 50 ,50, 50, 45, 45, 45, 40, 40, 40, 40, 40, 35, 35, 30, 30, 25, 15, 15, 15, 10, 10, 10, 10, 15, 15, 25, 25, 30, 35, 35, 30, 25, 25, 15, 15, 10, 10},
                                {50, 50, 50, 45, 45, 45, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 45, 45, 45, 50, 50, 50, 50, 50, 50, 45, 45, 45, 40, 40 ,40 ,40 ,40 ,35 ,35, 30, 30, 25, 25, 15 ,15 ,15, 10, 10, 10, 15, 15, 25, 30, 40, 35, 30, 30, 25, 25, 25, 15, 15},
                                {50, 50, 50 ,50 ,45 ,45, 45, 40, 40, 40, 40, 45, 40, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 55, 50, 50, 50, 45, 45, 45, 45, 40, 40, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 15, 10, 10, 10, 15, 15, 25, 40, 40, 35, 35, 30, 25, 25, 15, 15},
                                {55, 55, 50, 50, 50, 45, 45, 45, 40, 45, 45, 45, 45, 40, 40, 45, 45, 45, 45, 50, 50, 50, 55, 55, 55, 50, 50, 50, 45, 45, 45, 45, 40, 40, 40, 40, 35, 35, 30, 30, 30, 25, 25, 15, 15, 10, 10, 10, 15, 15, 40, 40, 40, 35, 35, 30, 30, 25, 25},
                                {55, 55, 55, 50, 50, 50, 45, 45, 45, 45, 45, 45, 45, 40, 45, 45, 45, 50, 50, 50, 50, 55, 55, 55, 55, 55, 50, 50, 50, 45, 45, 45, 45, 40, 40, 40, 40, 35, 35, 30, 30, 30, 25, 25, 15, 15, 10, 10, 10, 15, 45, 40, 40, 40, 35, 35, 30, 30, 25},
                                {55, 55, 55, 55, 50, 50, 50, 45, 45, 45, 45, 50, 45, 45, 45, 45, 50, 50, 50, 50, 55, 55, 55, 60, 55, 55, 50, 50, 50, 50, 45 ,45, 45, 45, 45, 40, 40, 40, 35, 35, 35, 30, 30, 25, 25, 15, 15, 10, 10, 10, 45 ,40 ,40 ,40 ,40 ,35 ,35, 30, 30},
                                {60, 60, 55, 55, 55, 50, 50, 50, 45, 45, 50, 50, 50, 45, 45, 50, 50, 50 ,50 ,55, 55, 55, 60, 60, 60, 55, 55, 50, 50, 50, 50, 50, 45, 45, 45, 45, 40, 40, 40, 40, 35, 35, 35, 30, 30, 25, 15, 15, 10, 10, 45, 45, 45, 40, 40, 40, 40, 35, 35},
                                {40, 35, 35, 30, 30, 25, 15, 10, 10, 10, 15, 15, 25, 30, 30, 35, 35, 40, 40, 40, 45 ,45, 45, 45, 45, 45, 40, 40, 40, 35, 35, 30, 30, 30, 30, 35, 35, 40, 40, 40, 40, 35, 35, 35, 40, 40, 40, 45, 45, 45, 10, 10, 10, 15, 15, 25, 30 ,30, 35},
                                {40, 40, 35, 35, 30, 30, 25, 15, 10, 15, 15, 25 ,30 ,30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 50, 45, 45, 45, 40, 40, 40, 40, 35, 35, 30, 35, 35, 40, 40, 40, 35, 35, 30, 30, 35, 35, 40, 40, 40, 40, 45, 10, 10, 10, 10, 15 ,25, 30, 30, 30},
                                {40, 40, 40, 35, 35, 30, 25, 25, 15, 15, 25, 25, 30, 35, 35, 40, 40, 40, 40, 45, 45, 45, 50, 50, 50, 45, 45, 45, 40, 40, 40, 40, 35, 35, 40, 40, 40, 35, 35, 35, 30, 30, 25, 30, 30, 35, 40, 40, 40, 45, 10, 15, 10, 10, 10, 15, 25, 25, 25},
                                {40, 40, 40, 40, 35, 35, 30, 25, 15, 25, 25, 30, 35, 35, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 50, 45, 45, 45, 45, 40, 40, 40, 40, 35, 40, 40, 40, 35, 35, 30, 30, 25, 25, 25, 30, 35, 35, 40, 40, 40, 15, 15, 15, 10, 10, 15, 25, 25, 25},
                                {45, 45, 40, 40 ,40 ,35 ,35 ,30 ,25 ,25 ,30 ,35 ,35 ,40 ,40, 40, 40, 45, 45, 45, 50, 50, 50, 55, 50, 50, 45, 45, 45, 45, 40, 40, 40, 40, 40, 40, 35, 30, 30, 30, 25, 25, 15 ,25, 25, 30, 35, 35, 40, 40, 15, 25, 15, 15, 10, 10, 15, 15, 15},
                                {45, 45, 40, 40, 40, 40, 35, 30, 25, 30, 30, 35, 40, 40, 40, 40, 45, 45, 45, 45, 50, 50, 50, 55, 50, 50, 50, 45 ,45, 45, 45, 40, 40, 40, 40, 35, 35, 30, 30, 25, 25 ,15, 15, 15, 25, 25, 30, 35, 35 ,40, 25, 25, 15, 15, 10, 10, 10, 15, 15},
                                {45, 45, 45, 40, 40, 40, 35, 30, 30, 30, 35, 35, 40, 40, 40, 40, 40, 45, 45, 45, 50, 50, 50, 50, 50, 50, 45, 45, 45, 40, 40, 40, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 15, 15, 25, 25, 30, 30, 35, 40, 30, 30, 25, 25, 15, 10, 10, 10, 15},
                                {50, 45, 45, 45, 40, 40, 40, 35, 30, 35, 35, 40, 40, 35, 35, 40, 40, 40, 40, 45, 45 ,45, 50, 50 ,50, 45, 45, 45, 40, 40, 40, 40, 35, 35, 30, 30, 25, 25, 15, 15, 15, 10, 10, 10, 15, 15, 25, 30, 30, 35, 30, 30, 25, 25, 15, 15, 10, 10, 10},
                                {50, 45, 45, 45, 45, 40, 40, 40, 35, 35, 40, 40, 40, 35, 35, 40, 40, 40, 40, 45, 45, 45, 50, 50, 45, 45 ,45, 40, 40, 40, 40, 35, 35, 35, 30, 30, 25, 25, 15, 15 ,10 ,10 ,10, 10, 15, 15, 25, 25, 30, 35, 35, 30, 25, 25 ,15, 15, 15, 10, 10}};

        int sIndex=-1;
        int eIndex=-1;
        if ( start.length() == 0 && stop.length()==0 ) {
            v1.setText("");
            Toast.makeText(FareCalculator.this, "Please Enter both start & end stations", Toast.LENGTH_SHORT).show();
        }
        else if(start.length()==0)
        {
            v1.setText("");
            Toast.makeText(FareCalculator.this, "Please Enter  Start station", Toast.LENGTH_SHORT).show();
        }
        else if(stop.length()==0) {
            v1.setText("");
            Toast.makeText(FareCalculator.this, "Please Enter End station", Toast.LENGTH_SHORT).show();
        }
        else if(stop.equals(start)) {
            v1.setText("");
            Toast.makeText(FareCalculator.this, "Start & End stations should not be same", Toast.LENGTH_SHORT).show();
        }
        else {

            String[] arr;
            arr = new String[]{"Nagole", "Uppal", "Stadium", "NGRI", "Habsiguda", "Tarnaka", "Mettuguda",
                    "Secunderabad East", "Parade Ground", "Paradise", "Rasoolpura", "Prakash Nagar",
                    "Begumpet", "Ameerpet", "Madhura Nagar", "Yusufguda", "Road No.5 Jubilee Hills",
                    "Jubilee Hills Check Post", "Peddamma Gudi", "Madhapur", "Durgam Cheruvu",
                    "Hitec City", "Raidurg","Miyapur", "JNTU College", "KPHB Colony", "Kukatpally", "Dr.B.R.Ambedkar Balanagar",
                    "Moosapet", "Bharat Nagar", "Erragadda", "ESI Hospital", "SR Nagar",
                    "Ameerpet", "Punjagutta", "Irrum Manzil", "Khairatabad", "Lakdi-Ka-Pul",
                    "Assembly", "Nampally", "Gandhi Bhavan", "Osmania Medical College",
                    "MG Bus Station", "Malakpet", "New Market", "Musarambagh", "Dilsukhnagar",
                    "Chaitanyapuri", "Victoria Memorial", "LB Nagar",
                    "Parade Ground", "Secunderabad West", "Gandhi Hospital", "Musheerabad", "RTC X Roads",
                    "Chikkadpally", "Narayanguda", "Sultan Bazar", "MG Bus Station"};
            for(int i=0;i<arr.length;i++)
            {
                if(arr[i].equals(start))
                    sIndex=i;
                if(arr[i].equals(stop))
                    eIndex=i;
                if(sIndex!=-1 && eIndex!=-1)
                    return fare_chart[sIndex][eIndex];
            }
        }
        return 0;

    }


}