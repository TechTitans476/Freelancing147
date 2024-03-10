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

public class MainActivity2 extends AppCompatActivity {

    private Toolbar toolbar;
    String rid;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
         rid = preferences.getString("user", "");
        Toast.makeText(this, rid, Toast.LENGTH_SHORT).show();
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
            }else if(itemId==R.id.action_list){
                SharedPreferences preferences = getSharedPreferences("Rjoblist", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("recruiterId", rid);
                editor.apply();
                Intent intent1 = new Intent(MainActivity2.this, Rjoblist.class);
                startActivity(intent1);
                return true;
            }//else if(itemId==R.id.action_msgs)
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