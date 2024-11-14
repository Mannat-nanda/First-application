package com.example.mannatsandroidlab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mannatsandroidlab.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TextView mytext;
    private Button mybutton;
    private EditText myedit;
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize variableBinding and set content view
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        // Initialize ViewModel
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Set up views using variableBinding
        ImageView algonquinLogo = variableBinding.algonquinLogo;
        algonquinLogo.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Algonquin Logo Clicked!", Toast.LENGTH_SHORT).show();
        });

        mybutton = variableBinding.mybutton;
        myedit = variableBinding.myedittext;
        mytext = variableBinding.textview;

        mybutton.setOnClickListener(v -> {
            String editString = myedit.getText().toString();
            mytext.setText("Your edit text has: " + editString);
            Toast.makeText(MainActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
        });

        // Set up other components with listeners
        variableBinding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.isChecked.postValue(isChecked);
        });

        variableBinding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.isChecked.postValue(isChecked);
        });

        variableBinding.radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.isChecked.postValue(isChecked);
        });

        viewModel.isChecked.observe(this, selected -> {
            // Update CompoundButtons here
            variableBinding.checkBox.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);

            // Add Toast message here
            Toast.makeText(MainActivity.this, "The value is now: " + selected, Toast.LENGTH_SHORT).show();
        });

        // Adjust window insets if necessary
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}