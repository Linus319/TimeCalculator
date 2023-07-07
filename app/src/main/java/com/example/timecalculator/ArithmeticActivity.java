package com.example.timecalculator;

import static android.graphics.Color.rgb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;


public class ArithmeticActivity extends AppCompatActivity {

    private int timeToSeconds(String time) { // time must be digits only, hhmmss
        int s = 0;

        s += (time.charAt(5) - '0') + ((time.charAt(4) - '0') * 10); // add seconds to s
        s += ((time.charAt(3) - '0') + ((time.charAt(2) - '0') * 10)) * 60; // convert minutes to seconds and add to s
        s += ((time.charAt(1) - '0') + ((time.charAt(0) - '0') * 10)) * 3600; // convert hours to seconds and add to s

        // System.out.println("seconds: " + s);
        return s;
    }

    private String secondsToTime(int s) {
        String result = "";

        Boolean isNegative = s < 0;

        if (isNegative) {
            s *= -1;
        }

        int days = 0, hours = 0, minutes = 0, seconds;

        if (s >= 86400) { // get number of days and subtract from s
            days = s / 86400;
            s = s % 86400;
        }

        if (s >= 3600) { // get number of hours and subtract from s
            hours = s / 3600;
            s = s % 3600;
        }

        if (s >= 60) { // get number of minutes and subtract from s
            minutes = s / 60;
            s = s % 60;
        }

        seconds = s; // put remaining seconds from s into seconds

        if (days > 0) {
            result =  result.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds);
        }
        else {
            result = result.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        if (isNegative) {
            result = "-" + result;
        }

        return result;
    }

    @NonNull
    private Boolean isValidTime(String time) { // string must be "hhmmss" format (digits only)
        if (time.charAt(2) < '6' && time.charAt(4) < '6') { // make sure minutes and seconds are 59 or less
            if (time.charAt(0) == '2' && time.charAt(1) < '4') { // make sure hours don't go past 23
                return true;
            }
            else if (time.charAt(0) < '2') { // make sure hours don't go past 23
                return true;
            }
        }
        return false;
    }

    private String addTwoTimes(String t1, String t2) { // strings should be in "hhmmss" format
        int s1 = timeToSeconds(t1);
        int s2 = timeToSeconds(t2);
        return secondsToTime(s1 + s2);
    }

    private String subTwoTimes(String t1, String t2) {
        int s1 = timeToSeconds(t1);
        int s2 = timeToSeconds(t2);
        return secondsToTime(s1 - s2);
    }

    private float divTwoTimes(String t1, String t2) {
        float s1 = timeToSeconds(t1);
        float s2 = timeToSeconds(t2);
        return s1 / s2;
    }

    final TextWatcher timeFormat = new TextWatcher() {
        private String current = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            String cleanS = s.toString().replaceAll(":", "");

            if (s.length() != current.length()) {

                if (cleanS.length() == 0) {
                    current = "";
                }
                if (cleanS.length() == 1) {
                    current = cleanS;
                }
                else if (cleanS.length() == 2) {
                    current = cleanS;
                }
                else if (cleanS.length() == 3) {
                    current = cleanS.charAt(0) + ":" + cleanS.substring(1,3);
                }
                else if (cleanS.length() == 4) {
                    current = cleanS.substring(0,2) + ":" + cleanS.substring(2,4);
                }
                else if (cleanS.length() == 5) {
                    current = cleanS.charAt(0) + ":" + cleanS.substring(1,3) + ":" + cleanS.substring(3,5);
                }
                else if (cleanS.length() == 6) {
                    current = cleanS.substring(0,2) + ":" + cleanS.substring(2,4) + ":" + cleanS.substring(4,6);
                }

                s.replace(0, s.length(), current);
            }
        }
    };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);

        TextView resultText = findViewById(R.id.showResult);

        Button calcButton = findViewById(R.id.calcButton);
        Button addButton = findViewById(R.id.addButton);
        Button subButton = findViewById(R.id.subButton);
        Button divButton = findViewById(R.id.divButton);



        EditText enterTime1 = findViewById(R.id.editTextTime1), enterTime2 = findViewById(R.id.editTextTime2);
        enterTime1.addTextChangedListener(timeFormat);
        enterTime2.addTextChangedListener(timeFormat);


        addButton.setOnClickListener(view -> {
            calcButton.setText("Add");
        });

        subButton.setOnClickListener(view -> {
            calcButton.setText("Subtract");
        });

        divButton.setOnClickListener(view -> {
            calcButton.setText("Divide");
        });

        calcButton.setOnClickListener(view -> {
            // convert strings to hhmmss format
            String time1 = enterTime1.getText().toString().replaceAll(":", "");
            String time2 = enterTime2.getText().toString().replaceAll(":", "");
            while (time1.length() < 6) {
                time1 = '0' + time1;
            }
            while (time2.length() < 6) {
                time2 = '0' + time2;
            }


            if (calcButton.getText().toString().equals("Calculate")) {
                resultText.setText("Please selecte an arithmetic operator");
            }
            else if (isValidTime(time1) && isValidTime(time2)) { // check if times are valid or not
                if (calcButton.getText().toString().equals("Add")) {
                    resultText.setText(addTwoTimes(time1, time2));
                }
                else if (calcButton.getText().toString().equals("Subtract")) {
                    resultText.setText(subTwoTimes(time1, time2));
                }
                else if (calcButton.getText().toString().equals("Divide")) {
                    resultText.setText("".format("%02f", divTwoTimes(time1, time2)));
                }
            }
            else {
                resultText.setText("Values must be between 00:00:01 and 23:59:59");
            }
        });
    }
}