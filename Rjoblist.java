package com.example.freelancingapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rjoblist extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String recruiterId,jid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rjoblist);

        SharedPreferences preferences = getSharedPreferences("Rjoblist", MODE_PRIVATE);
        recruiterId = preferences.getString("recruiterId", "");
        Toast.makeText(this, recruiterId, Toast.LENGTH_SHORT).show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs");
        fetchJobsByRecruiter();
    }

    private void fetchJobsByRecruiter() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LinearLayout resultContainer = findViewById(R.id.resultContainer);
                resultContainer.removeAllViews(); // Clear previous results

                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Jobb jobb = jobSnapshot.getValue(Jobb.class);

                    // Check if the job is posted by the current recruiter
                    if (jobb != null && jobb.getRecruiter_id().equals(recruiterId)) {
                        createJobCard(resultContainer, jobb);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(Rjoblist.this, "Error fetching jobs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createJobCard(LinearLayout resultContainer, Jobb jobb) {
        // Create a new CardView for each job
        CardView cardView = new CardView(Rjoblist.this);
        cardView.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        ));
        View jobEntryView = getLayoutInflater().inflate(R.layout.userentry, null);

        // Set data to the TextViews in the job entry layout
        TextView jobIdTextView = jobEntryView.findViewById(R.id.job_id);
        jobIdTextView.setText(": " + jobb.getJob_id());

        TextView jobNameTextView = jobEntryView.findViewById(R.id.job_name);
        jobNameTextView.setText(": " + jobb.getJob_name());

        TextView locationTextView = jobEntryView.findViewById(R.id.Location);
        locationTextView.setText(": " + jobb.getLocation());

        TextView shiftTextView = jobEntryView.findViewById(R.id.Shift);
        shiftTextView.setText(": " + jobb.getShift());

        TextView fieldTextView = jobEntryView.findViewById(R.id.Field);
        fieldTextView.setText(": " + jobb.getField());

        TextView modeTextView = jobEntryView.findViewById(R.id.mode);
        modeTextView.setText(": " + jobb.getMode());

        TextView onameTextView = jobEntryView.findViewById(R.id.oname);
        onameTextView.setText(": " + jobb.getOname());

        TextView recruiterIdTextView = jobEntryView.findViewById(R.id.recruiter_id);
        recruiterIdTextView.setText(": " + jobb.getRecruiter_id());
        Button applyButton = new Button(Rjoblist.this);
        applyButton.setText("Get Details");
        LinearLayout buttonContainer = jobEntryView.findViewById(R.id.buttonContainer);
        buttonContainer.addView(applyButton);
        cardView.addView(jobEntryView);
        resultContainer.addView(cardView);
        // Set an OnClickListener for the CardView
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Rjoblist.this, "hello", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("joblist", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("recruiterId", recruiterId);
                editor.putString("JobId", jobb.getJob_id());
                editor.apply();
                Intent jobDetailsIntent = new Intent(Rjoblist.this, Getapplicantsdetails.class);
                startActivity(jobDetailsIntent);
            }
        });


    }
}

