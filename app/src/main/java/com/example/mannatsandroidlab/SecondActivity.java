package com.example.mannatsandroidlab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    EditText phoneInput;
    ImageView profileImage;
    TextView welcomeText;
    Button callButton, changePictureButton;

    // Set up ActivityResultLauncher for the camera
    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Bitmap thumbnail = (Bitmap) result.getData().getExtras().get("data");
                    profileImage.setImageBitmap(thumbnail);
                    saveImage(thumbnail);  // Save the bitmap
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        welcomeText = findViewById(R.id.welcome_text);
        phoneInput = findViewById(R.id.phone_input);
        callButton = findViewById(R.id.call_button);
        profileImage = findViewById(R.id.profile_image);
        changePictureButton = findViewById(R.id.change_picture_button);

        // Retrieve and display email address from Intent
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        welcomeText.setText("Welcome back " + emailAddress);

        // Load saved phone number from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedPhoneNumber = prefs.getString("PhoneNumber", "");
        phoneInput.setText(savedPhoneNumber); // Display saved phone number in EditText if available

        // Call number button handler
        callButton.setOnClickListener(v -> {
            String phoneNumber = phoneInput.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        });

        // Change picture button handler
        changePictureButton.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save phone number in SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber", phoneInput.getText().toString()); // Save current phone number
        editor.apply();
    }

    private void saveImage(Bitmap bitmap) {
        // Code to save the bitmap to disk (optional)
    }
}
