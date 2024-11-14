package com.example.mannatsandroidlab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.mannatsandroidlab.databinding.ActivityMainBinding;
import com.example.mannatsandroidlab.MainViewModel;

public class MainActivity extends AppCompatActivity {

    TextView mytext;
    Button mybutton;
    EditText myedit;
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainViewModel viewModel = new MainViewModel();
        ImageView algonquinLogo = variableBinding.algonquinLogo; // Initialize using ViewBinding

        algonquinLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event here
                // For example, you can display a Toast message
                Toast.makeText(MainActivity.this, "Algonquin Logo Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        Button myButton = variableBinding.myButton; // Initialize using ViewBinding
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "A Button Clicked!", Toast.LENGTH_SHORT).show();

            }
        });
        // ... other code ...

        ImageButton myImageButton = variableBinding.myimagebutton; // Initialize using ViewBinding

        myImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event here
                // For example, you can display a Toast message
                Toast.makeText(MainActivity.this, "ImageButton Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

// ... other code ...
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
            CharSequence text = "The value is now: " + selected;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(MainActivity.this, text, duration);
            toast.show();
        });

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mytext = variableBinding.textview;
        mybutton = variableBinding.mybutton;
        myedit = variableBinding.myedittext;

        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editString = myedit.getText().toString();
                mytext.setText("Your edit text has: " + editString);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}