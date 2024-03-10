package com.example.freelancingapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class jobdetailsactivity extends AppCompatActivity {

    private LinearLayout resultContainer;
    private String jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdetailsactivity);

        // Get job ID from the intent
        jobId = getIntent().getStringExtra("jobId");

        // Initialize UI components
        resultContainer = findViewById(R.id.resultContainer);

        // Fetch and display freelancers who applied for the specific job
        fetchApplicants();
    }

    private void fetchApplicants() {
        // Construct the StorageReference for images
        StorageReference imagesRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://freelancing-app-f483e.appspot.com/images/resumes");

        // Iterate through images and check for matching recruiter ID
        imagesRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        // Get the name of the image (e.g., "recruiter_id_job_id_freelancer_id")
                        String imageName = item.getName();

                        // Check if the image corresponds to the current job and recruiter
                        if (imageName.contains("recruiter_id" + jobId) ) {
                            // Load and display the image
                            loadAndDisplayImage(item);
                        }
                    }
                })
                .addOnFailureListener(exception -> {
                    // Handle any errors that may occur during image fetching
                    Toast.makeText(this, "Error fetching images", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadAndDisplayImage(StorageReference imageRef) {
        // Create ImageView dynamically
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageView.setLayoutParams(layoutParams);

        // Load image data using getBytes
        imageRef.getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(bytes -> {
                    // Create a Bitmap from the byte data

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    // Set the Bitmap to the ImageView
                    imageView.setImageBitmap(bitmap);

                    // Add ImageView to the result container
                    resultContainer.addView(imageView);
                })
                .addOnFailureListener(exception -> {
                    // Handle any errors that may occur during image loading
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                });
    }

}
