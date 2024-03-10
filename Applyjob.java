package com.example.freelancingapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Applyjob extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://freelancing-app-f483e-default-rtdb.firebaseio.com/");

    StorageReference imagesRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://freelancing-app-f483e.appspot.com/images/resumes");
    String fid,freelancerId,jid;
    String applicantsPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyjob);
         SharedPreferences preferences = getSharedPreferences("Apply", MODE_PRIVATE);
        freelancerId = preferences.getString("freelancerId", "");
        String jobId = preferences.getString("jobId", "");
        jid=jobId;
        String jobName = preferences.getString("jobName", "");
        String location = preferences.getString("location", "");
        String oname = preferences.getString("oname", "");
        String mode=preferences.getString("mode","");
        String shift = preferences.getString("shift", "");
        String field = preferences.getString("field", "");
        String recruiterId = preferences.getString("recruiterId", "");
        String id=freelancerId;//replace this id with the info passed by intent during login

        // Display job details in TextViews or any other UI elements
        TextView jobIdTextView = findViewById(R.id.jobIdTextView);
        jobIdTextView.setText("Job ID: " + jobId);

        TextView jobNameTextView = findViewById(R.id.jobNameTextView);
        jobNameTextView.setText("Job Name: " + jobName);

        TextView locationTextView = findViewById(R.id.locationTextView);
        locationTextView.setText("Location: " + location);

        TextView onameTextView = findViewById(R.id.onameTextView);
        onameTextView.setText("Organization Name: " + oname);
        TextView modeTextView = findViewById(R.id.modeTextView);
        modeTextView.setText("Mode " + mode);
        TextView fieldTextView = findViewById(R.id.fieldTextView);
        fieldTextView.setText("Field: " + field);

        TextView shiftTextView = findViewById(R.id.shiftTextView);
        shiftTextView.setText("Shift: " + shift);
        TextView recruiterIdTextView = findViewById(R.id.recruiterIdTextView);
        recruiterIdTextView.setText("Recruiter ID: " + recruiterId);
        TextView freelancer=findViewById(R.id.freelancerIdTextView);
        freelancer.setText("Freelancer ID:"+id);
        fid=recruiterId+jobId+id;
        Toast.makeText(this, fid, Toast.LENGTH_SHORT).show();
        pickImage();
    }

    public void pickImage() {
        Toast.makeText(this, "applicantsPath", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }
    ImageView selectedImageView;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                uploadImage(imageUri);
                checkAndSetApplicantValue();
            }
        }
    }
    private void checkAndSetApplicantValue() {
        dbref.child("Jobs").child(freelancerId).child("applicants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Applicants node exists, add the freelancer's ID
                    dbref.child("Jobs").child(jid).child("applicants").child(freelancerId).setValue(true)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(Applyjob.this, "Application successful", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(Applyjob.this, "Application failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    // Applicants node doesn't exist, create it and add the freelancer's ID
                    dbref.child("Jobs").child(jid).child("applicants").setValue(true)
                            .addOnSuccessListener(aVoid -> {
                                dbref.child("Jobs").child(jid).child("applicants").child(freelancerId).setValue(true)
                                        .addOnSuccessListener(aVoid1 -> {
                                            Toast.makeText(Applyjob.this, "Application successful", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(Applyjob.this, "Application failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(Applyjob.this, "Application failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void uploadImage(Uri imageUri) {
        if (imageUri != null) {
            // Get the file extension
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String fileExtension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
            String imageName = fid +"_"+  fileExtension;
            Toast.makeText(Applyjob.this, imageName, Toast.LENGTH_SHORT).show();
            StorageReference imageRef = imagesRef.child(imageName);

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image upload success
                        Toast.makeText(Applyjob.this, "Upload successful", Toast.LENGTH_SHORT).show();

                    })
                    .addOnFailureListener(e -> {
                        // Image upload failure
                        Toast.makeText(Applyjob.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
