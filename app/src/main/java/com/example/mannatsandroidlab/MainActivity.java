package com.example.mannatsandroidlab;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgView;
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);
        sw = findViewById(R.id.spin_switch);


        sw.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Switch is ON", Toast.LENGTH_SHORT).show();


                RotateAnimation rotate = new RotateAnimation(
                        0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                );


                rotate.setDuration(5000); // 5 seconds
                rotate.setRepeatCount(Animation.INFINITE); // Infinite repeat
                rotate.setInterpolator(new LinearInterpolator()); // Constant speed


                imgView.startAnimation(rotate);

            } else {
                Toast.makeText(this, "Switch is OFF", Toast.LENGTH_SHORT).show();


                imgView.clearAnimation();
            }
        });
    }
}
