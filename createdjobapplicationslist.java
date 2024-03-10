package com.example.freelancingapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class createdjobapplicationslist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> list;
    MainAdapter mainAdapter;
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Jobs");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbref.keepSynced(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<User>();
        mainAdapter = new MainAdapter(this, list);
        recyclerView.setAdapter(mainAdapter);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot sp : snapshot.getChildren()) {
                    User user = sp.getValue(User.class);
                    list.add(user);
                }
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to retrieve data: " + error.getMessage());
            }
        });
    }

}

