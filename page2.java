package com.example.freelancingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class page2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        Button freelancerButton = findViewById(R.id.freelancer);
        Button recruiterButton = findViewById(R.id.recruiter);

        recruiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(page2.this, Login.class);
                intent.putExtra("userType", "Recruiters");
                startActivity(intent);
            }
        });
        freelancerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(page2.this, Login.class);
                intent1.putExtra("userType", "Freelancers");
                startActivity(intent1);
            }
        });
    }
}