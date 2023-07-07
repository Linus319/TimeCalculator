package com.example.timecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button arithmeticButton = findViewById(R.id.arithButton);
        Intent arithmeticIntent = new Intent(this, ArithmeticActivity.class);

        arithmeticButton.setOnClickListener(v -> startActivity(arithmeticIntent));
    }
}