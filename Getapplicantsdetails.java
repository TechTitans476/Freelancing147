package com.example.freelancingapplication;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Getapplicantsdetails extends AppCompatActivity {

    private LinearLayout imageContainer;
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://freelancing-app-f483e-default-rtdb.firebaseio.com/");
    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://freelancing-app-f483e.appspot.com/images/resumes");
    String recruiterId,jobId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getapplicantsdetails);
        SharedPreferences preferences = getSharedPreferences("joblist", MODE_PRIVATE);
        recruiterId = preferences.getString("recruiterId", "");
         jobId = preferences.getString("JobId", "");

        imageContainer = findViewById(R.id.imageContainer);

        // Fetch and display images
        fetchImages();
    }

    private void fetchImages() {
        // Use listAll to get a list of items (images) from the StorageReference
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    // Get the name and download URL for each image
                    final String imageName = item.getName();
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Check if the image name contains the current freelancerId and jobId
                            String id=recruiterId + jobId;
                            if (imageName.contains(id)){
                                // Create ImageView dynamically and load the image using Picasso
                                addImageToLayout(imageName, uri.toString());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle failure
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle failure
            }
        });
    }


    private void addImageToLayout(String imageName, String imageUrl) {
        // You can use the imageName to display or store information about the image
        // For now, we'll just display the image name in a TextView along with the image
        TextView textView = new TextView(this);
        textView.setText("Image Name: " + imageName);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Load image into ImageView using Picasso
        Picasso.get().load(imageUrl).into(imageView);

        // Add TextView and ImageView to the layout
        imageContainer.addView(textView);
        imageContainer.addView(imageView);
    }

}

