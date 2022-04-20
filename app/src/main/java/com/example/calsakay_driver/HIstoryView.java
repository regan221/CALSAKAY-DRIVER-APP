package com.example.calsakay_driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HIstoryView extends AppCompatActivity {

    Histories historiesList;
    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);

        Intent intent = getIntent();
        historiesList = (Histories) intent.getSerializableExtra("info");
        tv_test = findViewById(R.id.tv_test);
        tv_test.setText(String.valueOf(historiesList.getFrontliner_id()));


    }
}