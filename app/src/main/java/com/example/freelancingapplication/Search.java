package com.example.freelancingapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://freelancing-app-f483e-default-rtdb.firebaseio.com/");
    private Spinner locationSpinner, fieldSpinner;
    private Spinner spinnerLocation, spinnerField, spinnerMode;
    private ImageButton searchButton;
    String recruiter_id,oname,job_name,field,shift,location,job_id,mode,freelancerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       // Toast.makeText(this, "hiiiiiiiiiii", Toast.LENGTH_SHORT).show();
        locationSpinner = findViewById(R.id.Item1);
        fieldSpinner = findViewById(R.id.Item2);
        spinnerMode=findViewById(R.id.Item3);
        SharedPreferences preferences = getSharedPreferences("Search", MODE_PRIVATE);
        freelancerId = preferences.getString("freelancerId", null);
        DatabaseReference jobsReference = FirebaseDatabase.getInstance().getReference().child("Jobs");

        jobsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> uniqueLocations = new ArrayList<>();
                List<String> uniqueFields = new ArrayList<>();

                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Jobb jobb = jobSnapshot.getValue(Jobb.class);
                    Toast.makeText(Search.this, jobb.getJob_id(), Toast.LENGTH_SHORT).show();
                    if (jobb != null) {
                        String location = jobb.getLocation();
                        String field = jobb.getField();

                        if (!uniqueLocations.contains(location)) {
                            uniqueLocations.add(location);
                            Toast.makeText(Search.this, location, Toast.LENGTH_SHORT).show();
                        }
                        if (!uniqueFields.contains(field)) {
                            uniqueFields.add(field);
                        }
                    }
                }

                uniqueLocations.add(0, "Select_Location");
                uniqueFields.add(0, "Select_Field");



                populateSpinner(uniqueLocations, locationSpinner);
                populateSpinner(uniqueFields, fieldSpinner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


             void populateSpinner(List<String> dataList, Spinner spinner) {
                 Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void onSearchButtonClick(View view) {
        // Get selected options from spinners
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        Spinner locationSpinner = findViewById(R.id.Item1);
        String selectedLocation = locationSpinner.getSelectedItem().toString();
        Spinner Mode1Spinner = findViewById(R.id.Item2);
        String selectedField = Mode1Spinner.getSelectedItem().toString();
        Spinner Mode2Spinner = findViewById(R.id.Item3);
        String selectedMode = Mode2Spinner.getSelectedItem().toString();

        String defaultLocation = "Select_Location";
        String defaultField = "Select_Field";
        String defaultMode = "Select_Mode";
        if (defaultLocation.equals(selectedLocation) && defaultField.equals(selectedField) && defaultMode.equals(selectedMode)) {
            Toast.makeText(this, "Button is pressed with default options", Toast.LENGTH_SHORT).show();
            fetchData();
        } else {
            selectedData(selectedLocation, selectedField, selectedMode);
        }

    }
    public void fetchData() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs");
        final LinearLayout resultContainer = findViewById(R.id.resultContainer);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resultContainer.removeAllViews(); // Clear previous results

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    // Retrieve individual fields
                    Jobb jobb = childSnapshot.getValue(Jobb.class);
                    if (jobb != null) {
                        String cjob_id = childSnapshot.child("job_id").getValue(String.class);
                        String cmode = childSnapshot.child("Mode").getValue(String.class);
                        String clocation = childSnapshot.child("Location").getValue(String.class);
                        String cshift = childSnapshot.child("Shift").getValue(String.class);
                        String cfield = childSnapshot.child("Field").getValue(String.class);
                        String cjob_name = childSnapshot.child("job_name").getValue(String.class);
                        String coname = childSnapshot.child("oname").getValue(String.class);
                        String crecruiter_id = childSnapshot.child("recruiter_id").getValue(String.class);

                        // Create a new CardView for each job
                        CardView cardView = new CardView(Search.this);
                        cardView.setLayoutParams(new CardView.LayoutParams(
                                CardView.LayoutParams.MATCH_PARENT,
                                CardView.LayoutParams.WRAP_CONTENT
                        ));

                        // Inflate the user entry layout
                        View userEntryView = getLayoutInflater().inflate(R.layout.userentry, null);

                        // Set data to the TextViews in the user entry layout
                        if (cjob_id != null && cmode != null && clocation != null &&
                                cshift != null && cfield != null && cjob_name != null &&
                                coname != null && crecruiter_id != null) {
                            TextView jobIdTextView = userEntryView.findViewById(R.id.job_id);
                            jobIdTextView.setText(" " + cjob_id);

                            TextView jobNameTextView = userEntryView.findViewById(R.id.job_name);
                            jobNameTextView.setText(" " + cjob_name);

                            TextView locationTextView = userEntryView.findViewById(R.id.Location);
                            locationTextView.setText("" + clocation);

                            TextView onameTextView = userEntryView.findViewById(R.id.oname);
                            onameTextView.setText(" " + coname);
                            TextView Mode = userEntryView.findViewById(R.id.mode);
                            Mode.setText("" + cmode);

                            TextView shiftTextView = userEntryView.findViewById(R.id.Shift);
                            shiftTextView.setText("" + cshift);

                            TextView fieldTextView = userEntryView.findViewById(R.id.Field);
                            fieldTextView.setText("" + cfield);

                            TextView recruiterIdTextView = userEntryView.findViewById(R.id.recruiter_id);
                            recruiterIdTextView.setText("" + crecruiter_id);

                            // Add the user entry layout to the CardView
                            Button applyButton = new Button(Search.this);
                            applyButton.setText("Apply");
                            LinearLayout buttonContainer = userEntryView.findViewById(R.id.buttonContainer);
                            buttonContainer.addView(applyButton);
                            cardView.addView(userEntryView);

                            // Add the CardView to the result container
                            resultContainer.addView(cardView);
                            applyButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences preferences = getSharedPreferences("Apply", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("freelancerId", freelancerId);
                                    editor.putString("jobId", cjob_id);
                                    editor.putString("jobName", cjob_name);
                                    editor.putString("location", clocation);
                                    editor.putString("oname", coname);
                                    editor.putString("mode", cmode);
                                    editor.putString("shift", cshift);
                                    editor.putString("field", cfield);
                                    editor.putString("recruiterId", crecruiter_id);
                                    editor.apply();
                                    Intent applyIntent = new Intent(Search.this, Applyjob.class);
                                    startActivity(applyIntent);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    public void selectedData(String loc, String field, String mode) {
        Toast.makeText(this, "Button is pressed with specific options", Toast.LENGTH_SHORT).show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs");
        final LinearLayout resultContainer = findViewById(R.id.resultContainer);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resultContainer.removeAllViews(); // Clear previous results

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Jobb jobb = childSnapshot.getValue(Jobb.class);
                    if ((jobb !=null) &&  (isJobMatch(jobb, loc, field, mode))) {
                        CardView cardView = new CardView(Search.this);
                        cardView.setLayoutParams(new CardView.LayoutParams(
                                CardView.LayoutParams.MATCH_PARENT,
                                CardView.LayoutParams.WRAP_CONTENT
                        ));

                        // Inflate the user entry layout
                        View userEntryView = getLayoutInflater().inflate(R.layout.userentry, null);

                        // Set data to the TextViews in the user entry layout
                        TextView jobIdTextView = userEntryView.findViewById(R.id.job_id);
                        jobIdTextView.setText(" " + jobb.getJob_id());

                        TextView jobNameTextView = userEntryView.findViewById(R.id.job_name);
                        jobNameTextView.setText(" " + jobb.getJob_name());

                        TextView locationTextView = userEntryView.findViewById(R.id.Location);
                        locationTextView.setText("" + jobb.getLocation());

                        TextView onameTextView = userEntryView.findViewById(R.id.oname);
                        onameTextView.setText(" " + jobb.getOname());

                        TextView shiftTextView = userEntryView.findViewById(R.id.Shift);
                        shiftTextView.setText("" + jobb.getShift());

                        TextView fieldTextView = userEntryView.findViewById(R.id.Field);
                        fieldTextView.setText("" + jobb.getField());

                        TextView recruiterIdTextView = userEntryView.findViewById(R.id.recruiter_id);
                        recruiterIdTextView.setText("" + jobb.getRecruiter_id());

                        // Add the user entry layout to the CardView
                        Button applyButton = new Button(Search.this);
                        applyButton.setText("Apply");

                        // Set an OnClickListener for the Apply button
                        applyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                String user = preferences.getString("freelancerId", "");
                                Intent applyIntent = new Intent(Search.this, Applyjob.class);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("freelancerId", user);
                                editor.putString("jobId", jobb.getJob_id());
                                editor.putString("jobName", jobb.getJob_name());
                                editor.putString("location", jobb.getLocation());
                                editor.putString("oname", jobb.getOname());
                                editor.putString("mode", jobb.getMode());
                                editor.putString("shift", jobb.getShift());
                                editor.putString("field", jobb.getField());
                                editor.putString("recruiterId", jobb.getRecruiter_id());
                                editor.apply();
                                startActivity(applyIntent);
                            }
                        });

                        // Add the Apply button to the user entry layout
                        LinearLayout buttonContainer = userEntryView.findViewById(R.id.buttonContainer);
                        buttonContainer.addView(applyButton);

                        // Set an OnClickListener for the CardView
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Handle CardView click (optional)
                                // For example, open a new activity or fragment
                                // You can use the same logic as the Apply button click
                            }
                        });
                        cardView.addView(userEntryView);

                        // Add the CardView to the result container
                        resultContainer.addView(cardView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private boolean isJobMatch(Jobb jobb, String location, String field, String mode) {
        return jobb != null &&
                ((location == null || "Select_Location".equals(location) || location.equals(jobb.getLocation())) ||
                        (field == null || "Select_Field".equals(field) || field.equals(jobb.getField())) ||
                        (mode == null || "Select_Mode".equals(mode) || mode.equals(jobb.getMode())));
    }





}


