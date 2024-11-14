package com.example.mannatsandroidlab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ImageView imgView;
    Switch sw;
    EditText emailInput;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);
        sw = findViewById(R.id.spin_switch);
        emailInput = findViewById(R.id.email_input);
        loginButton = findViewById(R.id.login_button);

        // Load saved email from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedEmail = prefs.getString("LoginName", "");
        emailInput.setText(savedEmail);

        sw.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Switch is ON", Toast.LENGTH_SHORT).show();
                RotateAnimation rotate = new RotateAnimation(0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(5000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());
                imgView.startAnimation(rotate);
            } else {
                Toast.makeText(this, "Switch is OFF", Toast.LENGTH_SHORT).show();
                imgView.clearAnimation();
            }
        });

        loginButton.setOnClickListener(v -> {
            // Save email in SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", emailInput.getText().toString());
            editor.apply();

            // Start SecondActivity with the email
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", emailInput.getText().toString());
            startActivity(nextPage);
        });
    }
}
