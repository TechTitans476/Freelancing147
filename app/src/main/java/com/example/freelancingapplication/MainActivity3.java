package com.example.freelancingapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity3 extends AppCompatActivity {

    private Toolbar toolbar;
    String user;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        user = preferences.getString("user", "");
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
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
        }else if(itemId==R.id.action_joblist){
            SharedPreferences preferences = getSharedPreferences("Fjoblist", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("freelancerId", user);
            editor.apply();
            Intent intent1 = new Intent(MainActivity3.this, Fjoblist.class);
            startActivity(intent1);
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.search) {
            SharedPreferences preferences = getSharedPreferences("Search", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("freelancerId", user);
            editor.apply();
            Intent createIntent = new Intent(MainActivity3.this, Search.class);
            startActivity(createIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}