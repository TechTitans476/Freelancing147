package com.example.freelancingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    private Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleBottomNavigationItemSelected(item);
            }
    });
    }
        @SuppressLint("NonConstantResourceId")
        private boolean handleBottomNavigationItemSelected(MenuItem item) {
            int itemId=item.getItemId();
            Intent intent;
            if(itemId==R.id.action_home){
                recreate();
                return true;
            }
            return false;
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.createid) {
            Intent createIntent = new Intent(MainActivity2.this, createjobapplication.class);
            startActivity(createIntent);
            return true;
        }
        if (itemId == R.id.createdid) {
            Intent otherIntent = new Intent(MainActivity2.this, Userlist.class);
            startActivity(otherIntent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}