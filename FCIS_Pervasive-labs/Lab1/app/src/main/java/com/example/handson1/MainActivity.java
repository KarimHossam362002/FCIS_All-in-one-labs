package com.example.handson1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText et_year = (EditText) findViewById(R.id.et_year);
        Button btn_age = (Button) findViewById(R.id.btn_age);
        TextView tv_result = (TextView) findViewById(R.id.tv_result);


        btn_age.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int year = Integer.valueOf(et_year.getText().toString());
                calculateAge age = new calculateAge();
                age.setBirthYear(year);
                int calculatedAge = age.ageCalculate();

                if (calculatedAge != -1) {
                    tv_result.setText("Age in 2025 will be: " + calculatedAge);
                } else {
                    tv_result.setText("Please enter a valid year before 2025.");
                }
            }
        });
    }
}