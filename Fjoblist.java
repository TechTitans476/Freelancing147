package com.example.freelancingapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fjoblist extends AppCompatActivity {
//get freelancerid from intent by passing it from login activity
DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://freelancing-app-f483e-default-rtdb.firebaseio.com/");
    private String freelancerId; // Assume you pass freelancer ID through intent

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fjoblist);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // Get freelancer ID from the intent

        SharedPreferences preferences = getSharedPreferences("Fjoblist", MODE_PRIVATE);
        freelancerId = preferences.getString("freelancerId", "");
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs");
        // Fetch and display jobs for the freelancer
        fetchJobsForFreelancer();
    }

    private void fetchJobsForFreelancer() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(Fjoblist.this, "hello freelancer", Toast.LENGTH_SHORT).show();
                LinearLayout resultContainer = findViewById(R.id.resultContainer);
                resultContainer.removeAllViews(); // Clear previous results

                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Jobb jobb = jobSnapshot.getValue(Jobb.class);
                    Toast.makeText(Fjoblist.this, "jobb", Toast.LENGTH_SHORT).show();

                    // Check if the freelancer has applied for this job
                    if (jobb != null && hasFreelancerApplied(jobb, freelancerId)) {
                        createJobCard(resultContainer, jobb);
                        Toast.makeText(Fjoblist.this, "jobb", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    private void createJobCard(LinearLayout resultContainer, Jobb jobb) {
        // Create a new CardView for each job
        Toast.makeText(Fjoblist.this, "jobb", Toast.LENGTH_SHORT).show();
        CardView cardView = new CardView(Fjoblist.this);
        cardView.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        ));

        // Inflate the job entry layout
        View jobEntryView = getLayoutInflater().inflate(R.layout.userentry, null);

        // Set data to the TextViews in the job entry layout
        TextView jobIdTextView = jobEntryView.findViewById(R.id.job_id);
        jobIdTextView.setText("Job ID: " + jobb.getJob_id());

        TextView jobNameTextView = jobEntryView.findViewById(R.id.job_name);
        jobNameTextView.setText("Job Name: " + jobb.getJob_name());

        TextView locationTextView = jobEntryView.findViewById(R.id.Location);
        locationTextView.setText("Location: " + jobb.getLocation());

        TextView shiftTextView = jobEntryView.findViewById(R.id.Shift);
        shiftTextView.setText("Shift: " + jobb.getShift());

        TextView modeTextView = jobEntryView.findViewById(R.id.mode);
        modeTextView.setText("Mode: " + jobb.getMode());

        TextView fieldTextView = jobEntryView.findViewById(R.id.Field);
        fieldTextView.setText("Field: " + jobb.getField());

        TextView onameTextView = jobEntryView.findViewById(R.id.oname);
        onameTextView.setText("Organization Name: " + jobb.getOname());

        TextView recruiterIdTextView = jobEntryView.findViewById(R.id.recruiter_id);
        recruiterIdTextView.setText("Recruiter ID: " + jobb.getRecruiter_id());

        // Add the job entry layout to the CardView
        cardView.addView(jobEntryView);

        // Add the CardView to the result container
        resultContainer.addView(cardView);
    }


    private boolean hasFreelancerApplied(Jobb jobb, String freelancerId) {
        // Check if the "applicants" field contains the freelancer ID
        return jobb != null && jobb.getApplicants() != null && jobb.getApplicants().containsKey(freelancerId);
    }
}