package com.example.freelancingapplication;

import static android.app.ProgressDialog.show;
import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createjobapplication extends AppCompatActivity {
    DatabaseReference dbref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://freelancing-app-f483e-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createjobapplication);
        Button yourButton = findViewById(R.id.button3);
        yourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinnerJobTypes = findViewById(R.id.Mode);
                int selectedPosition = spinnerJobTypes.getSelectedItemPosition();
                String jobtype=handleSelectedJobType(selectedPosition);
                EditText editTextJobName = findViewById(R.id.job_name);
                EditText editTextLocation = findViewById(R.id.location);
                EditText editTextOrganizationName = findViewById(R.id.oname);
                EditText editTextShift = findViewById(R.id.Shift);
                EditText editTextField = findViewById(R.id.field);
                EditText editTextjobid=findViewById(R.id.job_id);
                EditText editTextrecruiterid=findViewById(R.id.recruiter_id);
                String jobName = editTextJobName.getText().toString();
                String location = editTextLocation.getText().toString();
                String organizationName = editTextOrganizationName.getText().toString();
                String shift = editTextShift.getText().toString();
                String field = editTextField.getText().toString();
                String jobid=editTextjobid.getText().toString();
                String recruiterid=editTextrecruiterid.getText().toString();
                if(jobName.isEmpty()||location.isEmpty()||organizationName.isEmpty()||shift.isEmpty()||field.isEmpty()||jobid.isEmpty()||recruiterid.isEmpty()){
                    Toast.makeText(createjobapplication.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }else {
                    String message = "Job Name: " + jobName +
                            "\nLocation: " + location +
                            "\nOrganization Name: " + organizationName +
                            "\nShift: " + shift +
                            "\nField: " + field +
                            "\nSelected Job Type: " + jobtype;
                    dbref.child("Jobs").child(jobid).child("recruiter_id").setValue(recruiterid);
                    dbref.child("Jobs").child(jobid).child("job_name").setValue(jobName);
                    dbref.child("Jobs").child(jobid).child("Location").setValue(location);
                    dbref.child("Jobs").child(jobid).child("Shift").setValue(shift);
                    dbref.child("Jobs").child(jobid).child("Field").setValue(field);
                    dbref.child("Jobs").child(jobid).child("Mode").setValue(jobtype);
                    dbref.child("Jobs").child(jobid).child("oname").setValue(organizationName);
                    dbref.child("Jobs").child(jobid).child("job_id").setValue(jobid);
                    Toast.makeText(createjobapplication.this, "Job Application Registered Successfully", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public String handleSelectedJobType(int position) {
        // Get the selected item from the spinner

        String[] jobTypes = getResources().getStringArray(R.array.spinner_job_types);
        String selectedJobType = jobTypes[position];
        return selectedJobType;

    }

}