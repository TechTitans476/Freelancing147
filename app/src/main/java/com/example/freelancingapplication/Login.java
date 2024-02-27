package com.example.freelancingapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference dbref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://freelancing-app-f483e-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView username= findViewById(R.id.username);
        TextView password= findViewById(R.id.password);
        Button loginbtn= findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                 String pass=password.getText().toString();
                Intent intent=getIntent();
                String userType = getIntent().getStringExtra("userType");

                if(pass.isEmpty()){
                    //correct
                    Toast.makeText(Login.this,"INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                    //Intent i1=new Intent(MainActivity.this,MainActivity2.class);
                }else{
                    dbref.child("Users").child(userType).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(user)) {
                                final String getpass = snapshot.child(user).child("Password").getValue(String.class);
                                if (getpass.equals(pass)) {
                                    //intent.putExtra("userType", user);
                                    // Save user type in shared preferences
                                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("user", user);
                                    editor.apply();

// Redirect to the appropriate activity
                                    if (userType.equals("Freelancers")) {
                                        startActivity(new Intent(Login.this, MainActivity3.class));
                                    } else if (userType.equals("Recruiters")) {
                                        startActivity(new Intent(Login.this, MainActivity2.class));
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                }
                            }

                            else{
                                Toast.makeText(Login.this,"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                //incorrect
                //Toast.makeText(MainActivity.this,"LOGIN FAILED !!!",Toast.LENGTH_SHORT).show();
            }
        });


    }
}