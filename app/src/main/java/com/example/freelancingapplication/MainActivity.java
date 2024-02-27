package com.example.freelancingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DatabaseReference dbref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://freelancing-app-f483e-default-rtdb.firebaseio.com/");
    RadioGroup rg;
    RadioButton rb;
    String key;
    RadioButton radioButton1,radioButton2;
    public void checkradio(){
        radioButton1 = findViewById(R.id.radioButtonFreelancer);
        radioButton2 = findViewById(R.id.radioButtonRecruiter);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton2.setChecked(false);
                key=radioButton1.getText().toString();
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setChecked(false);
                key=radioButton2.getText().toString();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkradio();
        final EditText uname=findViewById(R.id.uname);
        final EditText email=findViewById(R.id.email);
        final EditText phno=findViewById(R.id.phone);
        final EditText pass=findViewById(R.id.password);
        final EditText cpass=findViewById(R.id.confirmpassword);
        final Button regBtn=findViewById(R.id.btnregister);
        final TextView loginBtn=findViewById(R.id.textView2);
        rg=findViewById(R.id.radioGroup);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=uname.getText().toString();
                final String emailid=email.getText().toString();
                final String  pwd=pass.getText().toString();
                final String cpwd=cpass.getText().toString();
                final String phone=phno.getText().toString();
                if((username.isEmpty() && emailid.isEmpty()&&pwd.isEmpty()&&cpwd.isEmpty()&&phone.isEmpty())){
                    Toast.makeText(MainActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                }
                if(username.isEmpty()||emailid.isEmpty()||pwd.isEmpty()||cpwd.isEmpty()||phone.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                }else if(!cpwd.equals(pwd)){
                    Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }else{
                    final String root=key+"_"+phone;
                    dbref.child(root).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final String root=username+phone;
                            if(snapshot.exists() && snapshot.hasChild("root")){
                                Toast.makeText(MainActivity.this, "User is already registered", Toast.LENGTH_SHORT).show();
                            }else{
                                if(key.equals(radioButton2.getText().toString())){
                                    dbref.child("Users").child("Recruiters").child(root).child("Unique ID").setValue(root);
                                    dbref.child("Users").child("Recruiters").child(root).child("User Name").setValue(username);
                                    dbref.child("Users").child("Recruiters").child(root).child("Phone No").setValue(phone);
                                    dbref.child("Users").child("Recruiters").child(root).child("Email ID").setValue(emailid);
                                    dbref.child("Users").child("Recruiters").child(root).child("Password").setValue(pwd);
                                }else {

                                    dbref.child("Users").child("Freelancers").child(root).child("User Name").setValue(username);
                                    dbref.child("Users").child("Freelancers").child(root).child("Phone No").setValue(phone);
                                    dbref.child("Users").child("Freelancers").child(root).child("Email ID").setValue(emailid);
                                    dbref.child("Users").child("Freelancers").child(root).child("Password").setValue(pwd);
                                    dbref.child("Users").child("Freelancers").child(root).child("Unique ID").setValue(root);
                                }

                                Toast.makeText(MainActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void checkButton(View v){
        int rid=rg.getCheckedRadioButtonId();
        rb=findViewById(rid);
    }

}